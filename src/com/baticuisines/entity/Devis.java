package com.baticuisines.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class Devis {
    private UUID id;
    private double estimatedAmount;
    private LocalDate issueDate;
    private LocalDate validityDate;
    private boolean accepted;
    private Project project;

    public Devis(UUID id, double estimatedAmount, LocalDate issueDate, LocalDate validityDate, boolean accepted , Project project) {
        this.id = id;
        this.estimatedAmount = estimatedAmount;
        this.issueDate = issueDate;
        this.validityDate = validityDate;
        this.accepted = accepted;
        this.project = project;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getEstimatedAmount() {
        return estimatedAmount;
    }

    public void setEstimatedAmount(double estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(LocalDate validityDate) {
        this.validityDate = validityDate;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }


    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return
                "\n╭──────────────────────────────────────────╮" +
                "\n│ ID:                " + id +
                "\n│ PROJECT :          " + project.getProjectName() +
                "\n│ Estimated Amount:  $" + String.format("%.2f", estimatedAmount) +
                "\n│ Issue Date:        " + issueDate +
                "\n│ Validity Date:     " + validityDate +
                "\n│ Accepted:          " + (accepted ? "✔ Yes" : "✘ No") +
                "\n╰──────────────────────────────────────────╯\n";
    }

}
