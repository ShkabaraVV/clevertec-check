package ru.clevertec.check;

import ru.clevertec.check.service.CheckService;
import ru.clevertec.check.service.DiscountService;
import ru.clevertec.check.service.ProductService;

public class CheckRunner {

	public static void main(String[] args) {
		String productFilePath = "./src/main/resources/products.csv";
		String discountCardFilePath = "./src/main/resources/discountCards.csv";

		ProductService productService = new ProductService(productFilePath);
		DiscountService discountService = new DiscountService(discountCardFilePath);
		CheckService checkService = new CheckService(productService, discountService);

		checkService.generateReceipt(args);
	}
}
/**
 * пример запуска
 * gradle build
 * java -cp build/classes/java/main ru.clevertec.check.CheckRunner 3-1 2-1 4-1
**/