package com.baticuisines.repository.classes;

import com.baticuisines.entity.Client;
import com.baticuisines.entity.Devis;
import com.baticuisines.entity.Project;
import com.baticuisines.entity.componentType.Labor;
import com.baticuisines.entity.componentType.Material;
import com.baticuisines.enums.ProjectStatus;
import com.baticuisines.repository.ComponentRepositoryInterface;
import com.baticuisines.repository.ProjectRepositoryInterface;
import ressources.DBconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProjectRepository implements ProjectRepositoryInterface {



    private final ComponentRepositoryInterface componentRepository;

    public ProjectRepository(ComponentRepositoryInterface componentRepository) {
        this.componentRepository = componentRepository;
    }

    private static Connection conn = DBconnection.getInstance().getConnection();

    public Project save(Project project) {
        String query = "INSERT INTO project (projectname, surfacearea, clientid) " +
                "VALUES (?, ?, ?) RETURNING id";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, project.getProjectName());
            statement.setDouble(2, project.getSurfaceArea());
            statement.setInt(3, project.getClient().getId());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int projectId = resultSet.getInt("id");
                    project.setId(projectId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return project;
    }

    public List<Project> findAll() {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT project.*, client.id AS client_id, client.name AS client_name, client.address AS client_address, " +
                "client.phonenumber AS client_phonenumber, client.isprofessional AS client_isprofessional " +
                "FROM project JOIN client ON project.clientid = client.id";


        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int projectId = resultSet.getInt("id");
                String projectName = resultSet.getString("projectname");
                double surfaceArea = resultSet.getDouble("surfacearea");
                double profitMargin = resultSet.getDouble("profitmargin");
                double totalCost = resultSet.getDouble("totalcost");
                ProjectStatus projectStatus = ProjectStatus.valueOf(resultSet.getString("projectstatus").toUpperCase());

                Client client = new Client(
                        resultSet.getInt("client_id"),
                        resultSet.getString("client_name"),
                        resultSet.getString("client_address"),
                        resultSet.getString("client_phonenumber"),
                        resultSet.getBoolean("client_isprofessional")
                );

                List<Material> materials = componentRepository.getMaterialsByProjectId(projectId);
                List<Labor> labors = componentRepository.getLaborsByProjectId(projectId);

                Project project = new Project(projectId, projectName, surfaceArea, profitMargin, totalCost, projectStatus, client, materials, labors);
                projects.add(project);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projects;
    }

    public Optional<Project> findById(int id) {
        String query = "SELECT project.*, client.id AS client_id, client.name AS client_name, client.address AS client_address, " +
                "client.phonenumber AS client_phonenumber, client.isprofessional AS client_isprofessional " +
                "FROM project JOIN client ON project.clientid = client.id WHERE project.id = ?";
        Project project = null;

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int projectId = resultSet.getInt("id");
                    String projectName = resultSet.getString("projectname");
                    double surfaceArea = resultSet.getDouble("surfacearea");
                    double profitMargin = resultSet.getDouble("profitmargin");
                    double totalCost = resultSet.getDouble("totalcost");
                    ProjectStatus projectStatus = ProjectStatus.valueOf(resultSet.getString("projectstatus").toUpperCase());

                    Client client = new Client(
                            resultSet.getInt("client_id"),
                            resultSet.getString("client_name"),
                            resultSet.getString("client_address"),
                            resultSet.getString("client_phonenumber"),
                            resultSet.getBoolean("client_isprofessional")
                    );

                    List<Material> materials = componentRepository.getMaterialsByProjectId(projectId);
                    List<Labor> labors = componentRepository.getLaborsByProjectId(projectId);

                    project = new Project(projectId, projectName, surfaceArea, profitMargin, totalCost, projectStatus, client, materials, labors);
                    return Optional.of(project);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }


    public boolean updateProfiteMargin(Project project) {
        String sql = "UPDATE Project SET profitmargin = ? WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, project.getProfitMargin());
            pstmt.setInt(2, project.getId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("");
            } else {
                System.out.println("Aucun Projet avec ID: " + project.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


    public void updateProjectTotalCost(Project project) {
        String query = "UPDATE project SET totalcost = ? WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setDouble(1, project.getTotalCost());
            statement.setInt(2, project.getId());

            int rowsUpdated = statement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Devis createDevis(Devis devis) {
        String query = "INSERT INTO devis (estimatedamount, issuedate, validitydate, accepted, projectid) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setDouble(1, devis.getProject().getTotalCost());
            statement.setDate(2, java.sql.Date.valueOf(devis.getIssueDate()));

            statement.setDate(3, java.sql.Date.valueOf(devis.getValidityDate()));
            statement.setBoolean(4, devis.isAccepted());
            statement.setInt(5, devis.getProject().getId());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    UUID devisId = (UUID) resultSet.getObject("id");
                    devis.setId(devisId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return devis;
    }








}
