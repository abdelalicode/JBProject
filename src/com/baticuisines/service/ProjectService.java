package com.baticuisines.service;

import com.baticuisines.entity.Client;
import com.baticuisines.entity.Devis;
import com.baticuisines.entity.Project;
import com.baticuisines.entity.componentType.Labor;
import com.baticuisines.entity.componentType.Material;
import com.baticuisines.enums.ProjectStatus;
import com.baticuisines.repository.ClientRepositoryInterface;
import com.baticuisines.repository.ComponentRepositoryInterface;
import com.baticuisines.repository.ProjectRepositoryInterface;
import com.baticuisines.utils.InputValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.baticuisines.Main.getUserChoice;
import static com.baticuisines.enums.ProjectStatus.ONGOING;
import static java.util.UUID.randomUUID;

public class ProjectService {

    private final ProjectRepositoryInterface projectRepository;
    private final ClientRepositoryInterface clientRepository;
    private final ComponentRepositoryInterface componentRepository;

    public ProjectService(ProjectRepositoryInterface projectRepository , ClientRepositoryInterface clientRepository, ComponentRepositoryInterface componentRepository) {
        this.projectRepository = projectRepository;
        this.clientRepository = clientRepository;
        this.componentRepository = componentRepository;
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

        Optional<Client> clientOptional = findByName(clientName);

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

    public Optional<Client> findByName(String name) {
        return clientRepository.findAll().stream()
                .filter(client -> client.getName().equalsIgnoreCase(name))
                .findFirst();
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
        Project projectInserted =  projectRepository.save(project);

        if(projectInserted != null) {

            for (Material material : materials) {
                material.setProject(project);
            }
            for (Labor labor : labors) {
                labor.setProject(project);
            }

            saveComponents(materials, labors);
        }

        Double totalProjectCost = calculateTotalCost(scanner,project);

        if(project.getClient().isProfessional())
        {
            System.out.println("\n"+project.getClient().getName() + " est  professionel");
            double remise = InputValidator.getValidatedDouble(scanner, " Entrez la remise souhaitée: ");

            if(remise !=0) {
               Double projectCost = totalProjectCost - (totalProjectCost * remise / 100.0);
                System.out.println("\n\nCout Totale après remise " + projectCost +"\n");
                project.setTotalCost(projectCost);
                projectRepository.updateProjectTotalCost(project);
            }
        }

        createProjectDevis(scanner, project);


    }

    public Project findProjectById(Scanner scanner) {

        int projectId = InputValidator.getValidatedInt(scanner, "Enter Project ID: ");
        Optional<Project> projectOptional = projectRepository.findById(projectId);

        if(projectOptional.isPresent()) {
            displayProjectComponents(projectOptional.get());
            return projectOptional.get();
        }
        else {
            System.out.println("No Project Found");
            return null;
        }

    }

    public Map<String, List<?>> saveComponents(List<Material> materials, List<Labor> labors) {

        Map<String, List<?>> componentsPair = new HashMap<>();

        for (Material material : materials) {
            Material materialInserted = componentRepository.saveMaterial(material);
        }

        for (Labor labor : labors) {
            Labor laborInserted = componentRepository.saveLabor(labor);
        }


        componentsPair.put("materials", materials);
        componentsPair.put("labors", labors);

        return componentsPair;
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

    public Double calculateTotalCost(Scanner scanner, Project project) {
        System.out.println("\n\n--- Calcul du coût total ---\n");

        boolean applyTVA = InputValidator.handleYesNo(scanner, "Souhaitez-vous appliquer une TVA au projet ?");
        if (applyTVA) {
            double tvaRate = InputValidator.getValidatedDouble(scanner, "Entrez le pourcentage de TVA (%) :");
            updateTVA(scanner, project, tvaRate);
        }
        double margin = 0;

        boolean applyMargin = InputValidator.handleYesNo(scanner, "Souhaitez-vous appliquer une une marge bénéficiaire au projet ?");
        if (applyMargin) {
            margin = InputValidator.getValidatedDouble(scanner, "Entrez le pourcentage du marge benificiare (%) :");
            updateProfiteMargin(scanner, project, margin);
        }

        double totalCostWithVAT = calculateTotalCostWithTVA(project);

        double marginProject = (totalCostWithVAT * margin) / 100 ;

        double totalCost = totalCostWithVAT + marginProject;

        project.setTotalCost(totalCost);

        projectRepository.updateProjectTotalCost(project);

//        displayProjectComponents(project);
        System.out.println("3. Coût total avant marge : "+totalCostWithVAT+" €\n\n" +
                "4. Marge bénéficiaire (" + margin +"%) : "+marginProject+" €\n\n");

        System.out.println("Le coût total du projet " + project.getProjectName() +", TVA et Marge incluse, est de : "+ totalCost+" €");

        return totalCost;
    }

    private void updateTVA(Scanner scanner, Project project, double tvaRate) {

        List<Material> materials = componentRepository.getMaterialsByProjectId(project.getId());
        for (Material material : materials) {
            material.setTVARate(tvaRate);
            componentRepository.updateMaterial(material);
        }

        List<Labor> labors = componentRepository.getLaborsByProjectId(project.getId());
        for (Labor labor : labors) {
            labor.setTVARate(tvaRate);
            componentRepository.updateLabor(labor);
        }

        System.out.println("TVA mise à jour pour tous les composants du projet.");
    }

    private void updateProfiteMargin(Scanner scanner, Project project, double margin) {

        project.setProfitMargin(margin);
        if(projectRepository.updateProfiteMargin(project)) {
            System.out.println("Marge benificiaire mise à jour pour tous les composants du projet.");
        }


    }

    public double calculateTotalCostWithTVA(Project project) {
        List<Material> materials = componentRepository.getMaterialsByProjectId(project.getId());
        List<Labor> labors = componentRepository.getLaborsByProjectId(project.getId());

        System.out.println("--- Résultat du Calcul---");
        System.out.println("Nom du projet : " + project.getProjectName());
        System.out.println("Client : " + project.getClient().getName());
        System.out.println("Adresse du chantier : " + project.getClient().getAddress());
        System.out.println("Surface : " + project.getSurfaceArea());

        double totalMaterialCost = 0.0;
        double totalMaterialCostTVA = 0.0;
        double totalLaborCost = 0.0;
        double totalLaborCostTVA = 0.0;

        System.out.println("--- Détail des Coûts---");
        System.out.println("1. Matériaux :");

        for (Material material : materials) {
            double materialCost = material.getQuantity() * material.getUnitCost() * material.getQualityCoefficient() + material.getTransportCost();
            double materialCostWithTVA = materialCost * (1 + material.getTVARate() / 100);
            totalMaterialCost += materialCost;
            totalMaterialCostTVA += materialCostWithTVA;
            System.out.println("- "+ material.getName() +" : " + materialCost+ " (quantité : " + material.getQuantity() + ", coût unitaire : " + material.getUnitCost() +", qualité : " + material.getQualityCoefficient() +", transport : "+ material.getTransportCost() +")");
        }

        System.out.println("**Coût total des matériaux avant TVA : " + totalMaterialCost +" €**");
        System.out.println("**Coût total des matériaux avec TVA  : "+ totalMaterialCostTVA +" €**");

        System.out.println("\n\n2. Main-d'œuvre :");

        for (Labor labor : labors) {
            double laborCost = labor.getHourlyRate() * labor.getWorkHours() * labor.getWorkerProductivity();
            double laborCostWithTVA = laborCost * (1 + labor.getTVARate() / 100);
            totalLaborCost += laborCost;
            totalLaborCostTVA += laborCostWithTVA;
            System.out.println("- " + labor.getName() +" : "+ laborCost +" € (taux horaire : " + labor.getHourlyRate()+ " €/h, heures travaillées : "+ labor.getWorkHours() +" h, productivité : "+ labor.getWorkerProductivity() +")");
        }

        System.out.println("**Coût total de la main-d'œuvre avant TVA : " + totalLaborCost +" €**");
        System.out.println("**Coût total de la main-d'œuvre avec TVA (20%) : " + totalLaborCostTVA + " €**");

        return (totalLaborCostTVA + totalMaterialCostTVA);

    }

    public void createProjectDevis(Scanner scanner , Project project) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        UUID id = randomUUID();

        System.out.println("--- Enregistrement du Devis---");

        LocalDate issueDate = InputValidator.getValidDate(scanner, "Entrez la date d'émission du devis", dtf);

        LocalDate validityDate = InputValidator.getValidDate(scanner, "Entrez la date de validité du devis", dtf);

        Devis devis = new Devis(id , project.getTotalCost(), issueDate, validityDate, true , project);

        boolean saveDevis = InputValidator.handleYesNo(scanner, "Souhaitez-vous enregistrer le devis ?");

        if (saveDevis) {

            Devis devisCreated = projectRepository.createDevis(devis);
            System.out.println("Le devis a été enregistré avec succès.");

            if (InputValidator.handleYesNo(scanner, "Imprimer le devis ?")) {
                System.out.println(devisCreated);
            }

        } else {

            devis.setAccepted(false);
            projectRepository.createDevis(devis);
            System.out.println("Le devis n'a pas été enregistré.");
        }


    }

    private void displayProjectComponents(Project project) {
        List<Material> materials = componentRepository.getMaterialsByProjectId(project.getId());
        List<Labor> labors = componentRepository.getLaborsByProjectId(project.getId());

        System.out.println("Matériaux pour le projet  " + project.getProjectName() + ":");
        for (Material material : materials) {
            System.out.println(material);
        }

        System.out.println("MainOeuvre pour le projet " + project.getProjectName() + ":");
        for (Labor labor : labors) {
            System.out.println(labor);
        }
    }

    public  void viewAllProjects() {
        System.out.println("Tous les Projects :");
        Map<Integer,Project> allProjects = projectRepository.findAll();

        allProjects.values().stream().sorted(Comparator.comparingDouble(Project::getTotalCost)).forEach(project -> {
            System.out.println("_________________________________________________________________________________________________________________");
            System.out.println(" ID: "+ project.getId() +",  Nom du Projet : " + project.getProjectName() + ", Surface : "
                    + project.getSurfaceArea() + " m², Coût Total : "
                    + String.format("%.2f", project.getTotalCost()) + " € , Location:" + project.getClient().getAddress());
            System.out.println("_________________________________________________________________________________________________________________");
        });

    }




}
