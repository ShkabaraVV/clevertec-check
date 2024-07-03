package ru.clevertec.check;

public class CheckRunner {

	public static void main(String[] args) {
		CheckService checkService = new CheckService();
		checkService.generateReceipt(args);
	}
}
/**
 * пример запуска
 * gradle build
 * java -cp build/classes/java/main ru.clevertec.check.CheckRunner 3-1 2-1 4-1
**/