package ru.clevertec.check;

import ru.clevertec.check.service.CheckService;
import ru.clevertec.check.service.DiscountService;
import ru.clevertec.check.service.ProductService;

import java.io.FileWriter;
import java.io.IOException;

public class CheckRunner {

    public static void main(String[] args) {
        String productFilePath = null;
        String saveToFile = "result.csv";
        String discountCardFilePath = "./src/main/resources/discountCards.csv";

        for (String arg : args) {
            if (arg.startsWith("pathToFile=")) {
                productFilePath = arg.split("=")[1];
            } else if (arg.startsWith("saveToFile=")) {
                saveToFile = arg.split("=")[1];
            }
        }

        if (productFilePath == null) {
            handleError("BAD REQUEST", saveToFile);
            return;
        }

        ProductService productService = new ProductService(productFilePath);
        DiscountService discountService = new DiscountService(discountCardFilePath);
        CheckService checkService = new CheckService(productService, discountService);

        checkService.generateReceipt(args, saveToFile);
    }

    private static void handleError(String errorMessage, String saveToFile) {
        try (FileWriter writer = new FileWriter(saveToFile)) {
            writer.write(errorMessage);
        } catch (IOException e) {
            System.err.println("INTERNAL SERVER ERROR: " + e.getMessage());
        }
    }
}