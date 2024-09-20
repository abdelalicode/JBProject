package com.baticuisines.service;

import com.baticuisines.entity.Client;
import com.baticuisines.entity.Project;
import com.baticuisines.entity.componentType.Labor;
import com.baticuisines.entity.componentType.Material;
import com.baticuisines.enums.ProjectStatus;
import com.baticuisines.repository.ClientRepositoryInterface;
import com.baticuisines.repository.ProjectRepositoryInterface;
import com.baticuisines.utils.InputValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static com.baticuisines.Main.getUserChoice;
import static com.baticuisines.enums.ProjectStatus.ONGOING;

public class ProjectService {

    private final ProjectRepositoryInterface projectRepository;
    private final ClientRepositoryInterface clientRepository;

    public ProjectService(ProjectRepositoryInterface projectRepository , ClientRepositoryInterface clientRepository) {
        this.projectRepository = projectRepository;
        this.clientRepository = clientRepository;
    }


    public void handleClientManagement(Scanner scanner) {
        System.out.println("Souhaitez-vous chercher un client existant ou en ajouter un nouveau ?");
        System.out.println("1. Chercher un client existant");
        System.out.println("2. Ajouter un nouveau client");

        int clientChoice = getUserChoice(scanner);

        switch (clientChoice) {
            case 1:
                Client clientFound = searchClient(scanner);
                InputValidator.handleYesNo(scanner, "Souhaitez-vous continuer avec ce client ?", this::addNewProject, clientFound);
                break;
            case 2:
                Client clientSaved = addNewClient(scanner);
                if(clientSaved != null) {
                    InputValidator.handleYesNo(scanner, "Souhaitez-vous continuer avec ce client ?", this::addNewProject, clientSaved );
                }
                break;
            default:
                System.out.println("Choix invalide.");
        }
    }

    private Client searchClient(Scanner scanner) {
        System.out.println("Entrez le nom du client à rechercher : ");
        String clientName = scanner.nextLine();
        Client client = null;

        Optional<Client> clientOptional = clientRepository.findByName(clientName);

        if (clientOptional.isPresent()) {
            client = clientOptional.get();
            System.out.println(" Client trouvé ! \n" + client);

        } else {
            System.out.println("Aucun Client Trouvé " + clientName);
            System.out.println("Ajouter Un nouveau client ?  (y/n) ");
            String response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("y")) {
                addNewClient(scanner);
            } else {
                System.out.println("Retour au menu principal.");
            }
        }

        return client;
    }

    private Client addNewClient(Scanner scanner) {
        System.out.println("Entrez les informations du nouveau client :");

        int id = 0;
        String name = InputValidator.getValidatedString(scanner, "Nom: ");
        String address = InputValidator.getValidatedString(scanner, "Adresse: ");
        String phone = InputValidator.getValidatedString(scanner, "Téléphone: ");
        boolean isProfessional = false;
        //        System.out.print("Est-ce un professionnel ? (true/false): ");
        //        boolean isProfessional = scanner.nextBoolean();
        Client client = new Client(id ,name, address, phone , isProfessional);
        return clientRepository.save(client);
    }


    public void addNewProject(Scanner scanner, Client client) {
        System.out.println("--- Création d'un Nouveau Projet ---");
        int id = 0;
        String projectName = InputValidator.getValidatedString(scanner, " Entrez le nom du projet : ");
        double surfaceArea = InputValidator.getValidatedDouble(scanner, " Entrez la surface de la cuisine (en m²) : ");

        List<Material> materials = collectMaterials(scanner);
        List<Labor> labors = collectLabor(scanner);

        double profitMargin = 00.0;
        double totalCost = 00.0;
        ProjectStatus projectStatus = ONGOING;

        Project project = new Project(id , projectName , surfaceArea, profitMargin, totalCost, projectStatus, client , materials , labors);
        System.out.println(project);
//        return projectRepository.save(project);

    }


    private List<Material> collectMaterials(Scanner scanner) {
        List<Material> materials = new ArrayList<>();
        boolean addMoreMaterials = true;

        while (addMoreMaterials) {
            System.out.println("--- Ajout des matériaux ---");

            int id = 0;
            String materialName = InputValidator.getValidatedString(scanner, "Entrez le nom du matériau : ");
            double quantity = InputValidator.getValidatedDouble(scanner, "Entrez la quantité de ce matériau (en m² ou en litres) : ");
            double unitCost = InputValidator.getValidatedDouble(scanner, "Entrez le coût unitaire de ce matériau (€/m² ou €/litre) : ");
            double transportCost = InputValidator.getValidatedDouble(scanner, "Entrez le coût de transport de ce matériau (€) : ");
            double qualityCoefficient = InputValidator.getValidatedDouble(scanner, "Entrez le coefficient de qualité du matériau (1.0 = standard, > 1.0 = haute qualité) : ");

            Material material = new Material(id ,materialName, "Material", 0, quantity, unitCost, transportCost, qualityCoefficient);
            materials.add(material);

            System.out.println("Matériau ajouté avec succès !");
            addMoreMaterials = InputValidator.handleYesNo(scanner, "Voulez-vous ajouter un autre matériau ?");
        }
        return materials;
    }


    private List<Labor> collectLabor(Scanner scanner) {
        List<Labor> labors = new ArrayList<>();
        boolean addMoreLabor = true;

        while (addMoreLabor) {
            System.out.println("--- Ajout de la main-d'œuvre ---");

            int id = 0;
            String laborType = InputValidator.getValidatedString(scanner, "Entrez le type de main-d'œuvre (e.g., Ouvrier de base, Spécialiste) : ");
            double hourlyRate = InputValidator.getValidatedDouble(scanner, "Entrez le taux horaire de cette main-d'œuvre (€/h) : ");
            double workHours = InputValidator.getValidatedDouble(scanner, "Entrez le nombre d'heures travaillées : ");
            double productivityFactor = InputValidator.getValidatedDouble(scanner, "Entrez le facteur de productivité (1.0 = standard, > 1.0 = haute productivité) : ");

            Labor labor = new Labor(id, laborType, "Labor", 0, hourlyRate, workHours, productivityFactor);
            labors.add(labor);

            System.out.println("Main-d'œuvre ajoutée avec succès !");
            addMoreLabor = InputValidator.handleYesNo(scanner, "Voulez-vous ajouter un autre type de main-d'œuvre ?");
        }
        return labors;
    }





}
