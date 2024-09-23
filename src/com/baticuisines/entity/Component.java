package com.baticuisines.entity;

import java.util.List;

public class Component {

    protected int id;
    protected String name;
    protected String componentType;
    protected double TVARate;
    protected Project project;

    public Component(int id, String name, String componentType, double TVARate) {
        this.id = id;
        this.name = name;
        this.componentType = componentType;
        this.TVARate = TVARate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public double getTVARate() {
        return TVARate;
    }

    public void setTVARate(double TVARate) {
        this.TVARate = TVARate;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "Component{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", componentType='" + componentType + '\'' +
                ", TVARate=" + TVARate +
                '}';
    }
}
