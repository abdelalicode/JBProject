package com.baticuisines.repository.classes;

import com.baticuisines.entity.Project;
import com.baticuisines.entity.componentType.Material;
import com.baticuisines.repository.ProjectRepositoryInterface;
import ressources.DBconnection;

import java.sql.*;

public class ProjectRepository implements ProjectRepositoryInterface {

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

    public boolean updateProfiteMargin(Project project) {
        String sql = "UPDATE Project SET profitmargin = ? WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, project.getProfitMargin());
            pstmt.setInt(2, project.getId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                System.out.println("Aucun Projet avec ID: " + project.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }






}
