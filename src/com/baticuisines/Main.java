package com.baticuisines;

import com.baticuisines.entity.Project;
import com.baticuisines.repository.ClientRepositoryInterface;
import com.baticuisines.repository.ComponentRepositoryInterface;
import com.baticuisines.repository.ProjectRepositoryInterface;
import com.baticuisines.repository.classes.ClientRepository;
import com.baticuisines.repository.classes.ComponentRepository;
import com.baticuisines.repository.classes.ProjectRepository;
import com.baticuisines.service.ProjectService;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {



        ClientRepositoryInterface clientRepository = new ClientRepository();
        ComponentRepositoryInterface componentRepository = new ComponentRepository();
        ProjectRepositoryInterface projectRepository = new ProjectRepository(componentRepository);

        ProjectService service = new ProjectService(projectRepository, clientRepository, componentRepository);

        Scanner scanner = new Scanner(System.in);


        do {
            System.out.println("\n\n === Bienvenue dans l'application de gestion des projets de rénovation de cuisines ===");
            displayMenu();
            int choice = getUserChoice(scanner);

            switch (choice) {
                case 1:
                    System.out.println("--- Recherche de client existant ---");
                    service.handleClientManagement(scanner);
                    pause(scanner);

                    break;
                case 2:
                    service.viewAllProjects();
                    pause(scanner);
                    break;
                case 3:
                    System.out.println("calculateProjectCost");
                    Project project = service.findProjectById(scanner);
                    if(project != null) {
                        service.calculateTotalCost(scanner , project);
                    }
                    pause(scanner);
                    break;
                case 4:
                    System.out.println("Exit");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (true);
    }

    private static void displayMenu() {
        System.out.println("===========================================");
        System.out.println("||          *** MENU PRINCIPAL ***       ||");
        System.out.println("===========================================");
        System.out.println("||                                       ||");
        System.out.println("||  1. Créer un nouveau projet           ||");
        System.out.println("||  2. Afficher les projets existants    ||");
        System.out.println("||  3. Calculer le coût d'un projet      ||");
        System.out.println("||  4. Quitter                           ||");
        System.out.println("||                                       ||");
        System.out.println("===========================================");
        System.out.print("\nVeuillez choisir une option : ");
    }

    public static int getUserChoice(Scanner scanner) {
        int choice = -1;
        try {
            choice = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        } finally {
            scanner.nextLine();
        }
        return choice;
    }

    private static void pause(Scanner scanner) {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }



}
