package com.baticuisines.utils;
import com.baticuisines.entity.Client;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Consumer;


public class InputValidator {


        public static int getValidatedInt(Scanner scanner, String prompt) {
            int input = -1;
            boolean isValid = false;

            while (!isValid) {
                try {
                    System.out.print(prompt);
                    input = scanner.nextInt();
                    isValid = true;
                } catch (InputMismatchException e) {
                    System.out.println("Invalide! Veuillez Entrer une valeure numérique");
                    scanner.next();
                }
            }
            scanner.nextLine();
            return input;
        }

    public static double getValidatedDouble(Scanner scanner, String prompt) {
        double input = -1;
        boolean isValid = false;


        while (!isValid) {
            try {
                System.out.print(prompt);
                input = scanner.nextDouble();
                isValid = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalide! Veuillez entrer une valeur numérique décimale.");
                scanner.next();
            }
        }
        scanner.nextLine();
        return input;
    }


    public static String getValidatedString(Scanner scanner, String prompt) {
            String input = "";
            boolean isValid = false;

            while (!isValid) {
                System.out.print(prompt);
                input = scanner.nextLine().trim();
                if (!input.isEmpty()) {
                    isValid = true;
                } else {
                    System.out.println("Invalide! Veuillez Entrer une valeure juste");
                }
            }
            return input;
        }

        public static boolean getValidatedBoolean(Scanner scanner, String prompt) {
            boolean isValid = false;
            boolean result = false;

            while (!isValid) {
                System.out.print(prompt);
                String input = scanner.nextLine().trim().toLowerCase();

                if (input.equals("true") || input.equals("false")) {
                    result = Boolean.parseBoolean(input);
                    isValid = true;
                } else {
                    System.out.println("Invalid input. Please enter 'true' or 'false'.");
                }
            }
            return result;
        }


    public static void handleYesNo(Scanner scanner, String message, BiConsumer<Scanner, Client> action, Client client) {
        System.out.println(message + " (y/n)");
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.equals("y")) {
            action.accept(scanner, client);
        } else {
            System.out.println("Retour au menu principal.");
        }
    }

    public static boolean handleYesNo(Scanner scanner, String message) {
        System.out.println(message + " (y/n)");
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.equals("y")) {
            return true;
        } else {
            System.out.println("Retour au menu principal.");
            return false;
        }
    }


}
