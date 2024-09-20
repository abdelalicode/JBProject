package com.baticuisines.entity;

import com.baticuisines.entity.componentType.Labor;
import com.baticuisines.entity.componentType.Material;
import com.baticuisines.enums.ProjectStatus;

import java.util.List;

public class Project {
    private int id;
    private String projectName;
    private double surfaceArea;
    private double profitMargin;
    private double totalCost;
    private ProjectStatus projectStatus;
    private Client client;
    private List<Devis> devis;
    private List<Material> materials;
    private List<Labor> labors;


    public Project(int id , String projectName, double surfaceArea, double profitMargin, double totalCost, ProjectStatus projectStatus , Client client, List<Material> materials , List<Labor> labors) {
        this.id = id;
        this.projectName = projectName;
        this.surfaceArea = surfaceArea;
        this.profitMargin = profitMargin;
        this.totalCost = totalCost;
        this.projectStatus = projectStatus;
        this.client = client;
        this.materials = materials;
        this.labors = labors;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public double getSurfaceArea() {
        return surfaceArea;
    }

    public void setSurfaceArea(double surfaceArea) {
        this.surfaceArea = surfaceArea;
    }

    public double getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(double profitMargin) {
        this.profitMargin = profitMargin;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Client getClient() {
        return client;
    }

    public void setClients(Client client) {
        this.client = client;
    }

    public List<Devis> getDevis() {
        return devis;
    }

    public void setDevis(List<Devis> devis) {
        this.devis = devis;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public List<Labor> getLabors() {
        return labors;
    }

    public void setLabors(List<Labor> labors) {
        this.labors = labors;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", surfaceArea=" + surfaceArea +
                ", profitMargin=" + profitMargin +
                ", totalCost=" + totalCost +
                ", projectStatus=" + projectStatus +
                ", client=" + client +
                ", devis=" + devis +
                ", materials=" + materials +
                ", labors=" + labors +
                '}';
    }
}


