package org.example;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CurrencyConverter converter = new CurrencyConverter();

        run(scanner, converter);
    }

    public static void run(Scanner scanner, CurrencyConverter converter) {
        while (true) {
            System.out.println("Set from which currency you want to convert.");
            String from = scanner.nextLine().trim().toUpperCase();

            System.out.println("Set to which currency you want to convert.");
            String to = scanner.nextLine().trim().toUpperCase();

            System.out.println("Amount:");
            String amount = scanner.nextLine().trim();

            if (from.isEmpty() && to.isEmpty() && amount.isEmpty()) {
                System.out.println("No currency to convert. Exiting... ");
                break;
            }
            try {
                double amountDouble = Double.parseDouble(amount);
                double result = converter.convertCurrency(from, to, amountDouble);
                System.out.printf("Converted: %.2f %s = %.2f %s%n", amountDouble, from, result, to);
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Please enter a numeric value.");
            } catch (Exception e) {
                System.out.println("Error during conversion: " + e.getMessage());
            }
        }
        scanner.close();
    }
}