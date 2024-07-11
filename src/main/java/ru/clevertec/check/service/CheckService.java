package ru.clevertec.check.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.model.Product;
import ru.clevertec.check.exception.BadRequestException;
import ru.clevertec.check.exception.InternalServerErrorException;
import ru.clevertec.check.exception.NotEnoughMoneyException;
import ru.clevertec.check.strategy.DiscountCardStrategy;
import ru.clevertec.check.strategy.DiscountStrategy;
import ru.clevertec.check.strategy.WholesaleDiscountStrategy;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CheckService {
    private ProductService productService;
    private DiscountService discountService;
    private List<DiscountStrategy> discountStrategies;

    public CheckService(ProductService productService, DiscountService discountService) {
        this.productService = productService;
        this.discountService = discountService;
        this.discountStrategies = new ArrayList<>();
        this.discountStrategies.add(new WholesaleDiscountStrategy());
    }

    public void generateReceipt(String[] args, String saveToFile) {
        try {
            Map<Product, Integer> products = parseArguments(args);
            BigDecimal balanceDebitCard = getBalanceDebitCard(args);
            DiscountCard discountCard = getDiscountCard(args);

            generateAndPrintReceipt(products, balanceDebitCard, discountCard, saveToFile);
        } catch (BadRequestException | NotEnoughMoneyException e) {
            handleException(e.getMessage(), saveToFile);
        } catch (InternalServerErrorException e) {
            handleException("INTERNAL SERVER ERROR: " + e.getMessage(), saveToFile);
        }
    }

    private Map<Product, Integer> parseArguments(String[] args) {
        Map<Integer, Integer> productQuantities = new LinkedHashMap<>();
        for (String arg : args) {
            if (arg.contains("-")) {
                String[] parts = arg.split("-");
                int productId = Integer.parseInt(parts[0]);
                int quantity = Integer.parseInt(parts[1]);
                productQuantities.put(productId, productQuantities.getOrDefault(productId, 0) + quantity);
            }
        }

        Map<Product, Integer> products = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer> entry : productQuantities.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();
            Product product = productService.getProductById(productId);
            if (product == null) {
                throw new BadRequestException("BAD REQUEST");
            }
            if (quantity > product.getQuantityInStock()) {
                throw new BadRequestException("BAD REQUEST");
            }
            products.put(product, quantity);
        }
        return products;
    }

    private BigDecimal getBalanceDebitCard(String[] args) {
        for (String arg : args) {
            if (arg.startsWith("balanceDebitCard=")) {
                double balance = Double.parseDouble(arg.split("=")[1]);
                return BigDecimal.valueOf(balance);
            }
        }
        return BigDecimal.ZERO;
    }

    private DiscountCard getDiscountCard(String[] args) {
        for (String arg : args) {
            if (arg.startsWith("discountCard=")) {
                int cardNumber = Integer.parseInt(arg.split("=")[1]);
                DiscountCard discountCard = discountService.getDiscountCard(cardNumber);
                if (discountCard == null) {
                    // Применяем скидку 2%, если карта не найдена
                    return new DiscountCard(cardNumber, 2.0);
                }
                return discountCard;
            }
        }
        return null;
    }

    private void generateAndPrintReceipt(Map<Product, Integer> products, BigDecimal balanceDebitCard, DiscountCard discountCard, String saveToFile) {
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal totalDiscount = BigDecimal.ZERO;
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy;HH:mm");
        String formattedDateTime = currentDateTime.format(formatter);

        StringBuilder receipt = new StringBuilder();
        receipt.append("Date;Time\n").append(formattedDateTime).append("\n\n");
        receipt.append("QTY;DESCRIPTION;PRICE;DISCOUNT;TOTAL\n");

        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            BigDecimal productTotal = product.getPrice().multiply(BigDecimal.valueOf(quantity));
            BigDecimal productDiscount;

            if (product.isWholesale() && quantity >= 5) {
                // Применяем оптовую скидку
                productDiscount = new WholesaleDiscountStrategy().calculateDiscount(product, quantity, discountCard);
            } else {
                // Применяем скидку по дисконтной карте
                productDiscount = new DiscountCardStrategy().calculateDiscount(product, quantity, discountCard);
            }

            total = total.add(productTotal);
            totalDiscount = totalDiscount.add(productDiscount);

            receipt.append(String.format("%d;%s;%.2f%s;%.2f%s;%.2f%s\n",
                    quantity, product.getDescription(), product.getPrice(),
                    "$", productDiscount, "$", productTotal, "$"));
        }

        if (balanceDebitCard.compareTo(total.subtract(totalDiscount)) < 0) {
            throw new NotEnoughMoneyException("NOT ENOUGH MONEY");
        }

        if (discountCard != null) {
            receipt.append("\nDISCOUNT CARD;DISCOUNT PERCENTAGE\n");
            receipt.append(discountCard.getCardNumber()).append(";").append(discountCard.getDiscountRate()).append("%\n");
        }
        receipt.append("\nTOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT\n");
        receipt.append(String.format("%.2f%s;%.2f%s;%.2f%s\n",
                total, "$",
                totalDiscount, "$",
                total.subtract(totalDiscount), "$"));

        System.out.println(receipt.toString());

        // Запись в CSV
        try (FileWriter writer = new FileWriter(saveToFile)) {
            writer.append(receipt);
        } catch (IOException e) {
            throw new InternalServerErrorException("INTERNAL SERVER ERROR ", e);
        }
        try {
            if (saveToFile.endsWith(".xlsx")) {
                saveToExcel(receipt.toString(), saveToFile);
            } else if (saveToFile.endsWith(".pdf")) {
                saveToPDF(receipt.toString(), saveToFile);
            } else if (saveToFile.endsWith(".docx")) {
                saveToWord(receipt.toString(), saveToFile);
            } else {
                throw new IllegalArgumentException("Unsupported file format: " + saveToFile);
            }
        } catch (IOException e) {
            throw new InternalServerErrorException("Error saving file: " + e.getMessage(), e);
        }
    }

    private void saveToExcel(String data, String saveToFile) throws IOException {
        // Implement saving to Excel using Apache POI
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Receipt");

        String[] lines = data.split("\n");
        int rowNum = 0;
        for (String line : lines) {
            Row row = sheet.createRow(rowNum++);
            String[] fields = line.split(";");
            int colNum = 0;
            for (String field : fields) {
                row.createCell(colNum++).setCellValue(field);
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream(saveToFile)) {
            workbook.write(outputStream);
        }
    }

    private void saveToPDF(String data, String saveToFile) throws IOException {
        // Implement saving to PDF using iText
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(saveToFile));
        Document document = new Document(pdfDocument);

        // Parse the data and add to PDF document
        BufferedReader reader = new BufferedReader(new StringReader(data));
        String line;
        while ((line = reader.readLine()) != null) {
            document.add(new Paragraph(line));
        }

        document.close();
    }

    private void saveToWord(String data, String saveToFile) throws IOException {
        // Implement saving to Word using Apache POI
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph paragraph = document.createParagraph();

        String[] lines = data.split("\n");
        for (String line : lines) {
            XWPFRun run = paragraph.createRun();
            run.setText(line);
            run.addBreak();
        }

        try (FileOutputStream outputStream = new FileOutputStream(saveToFile)) {
            document.write(outputStream);
        }
    }

    private void handleException(String errorMessage, String saveToFile) {
        try (FileWriter writer = new FileWriter(saveToFile)) {
            writer.write(errorMessage);
        } catch (IOException e) {
            System.err.println("INTERNAL SERVER ERROR: " + e.getMessage());
        }
    }
}
