package com.baticuisines.repository.classes;

import com.baticuisines.entity.componentType.Labor;
import com.baticuisines.entity.componentType.Material;
import com.baticuisines.repository.ComponentRepositoryInterface;
import ressources.DBconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComponentRepository implements ComponentRepositoryInterface {

    private static Connection conn = DBconnection.getInstance().getConnection();


    public Material saveMaterial(Material material) {
        String sql = "INSERT INTO Material (name, componenttype, projectid, quantity, unitcost, transportcost, qualitycoefficient) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, material.getName());
            pstmt.setString(2, "Material");

            pstmt.setInt(3, material.getProject().getId());
            pstmt.setDouble(4, material.getQuantity());
            pstmt.setDouble(5, material.getUnitCost());
            pstmt.setDouble(6, material.getTransportCost());
            pstmt.setDouble(7, material.getQualityCoefficient());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
       return material;
    }

    public Labor saveLabor(Labor labor) {
        String sql = "INSERT INTO Labor (name, componenttype, projectid, hourlyrate, workhours, workerproductivity) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, labor.getName());
            pstmt.setString(2, "Labor");
            pstmt.setInt(3, labor.getProject().getId());
            pstmt.setDouble(4, labor.getHourlyRate());
            pstmt.setDouble(5, labor.getWorkHours());
            pstmt.setDouble(6, labor.getWorkerProductivity());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return labor;
    }

    public List<Material> getMaterialsByProjectId(int projectId) {
        List<Material> materials = new ArrayList<>();
        String sql = "SELECT * FROM material WHERE projectid = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                Material material = new Material(0, "Str", "Str", 0.00, 0.00 , 0.00 , 0.00 , 0.00);
                material.setId(resultSet.getInt("id"));
                material.setName(resultSet.getString("name"));
                material.setTVARate(resultSet.getDouble("tvarate"));
                material.setQuantity(resultSet.getDouble("quantity"));
                material.setUnitCost(resultSet.getDouble("unitcost"));
                material.setTransportCost(resultSet.getDouble("transportcost"));
                material.setQualityCoefficient(resultSet.getDouble("qualitycoefficient"));
                materials.add(material);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return materials;
    }

    public List<Labor> getLaborsByProjectId(int projectId) {
        List<Labor> labors = new ArrayList<>();
        String sql = "SELECT * FROM labor WHERE projectid = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                Labor labor = new Labor(0,  "name",  "componentType",  0.00 , 0.00,  0.00, 0.00);
                labor.setId(resultSet.getInt("id"));
                labor.setName(resultSet.getString("name"));
                labor.setTVARate(resultSet.getDouble("tvarate"));
                labor.setHourlyRate(resultSet.getDouble("hourlyrate"));
                labor.setWorkHours(resultSet.getDouble("workhours"));
                labor.setWorkerProductivity(resultSet.getDouble("workerproductivity"));
                labors.add(labor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return labors;
    }

    public void updateMaterial(Material material) {
        String sql = "UPDATE Material SET tvaRate = ? WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, material.getTVARate());
            pstmt.setInt(2, material.getId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Material TVA updated successfully.");
            } else {
                System.out.println("No Material found with ID: " + material.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateLabor(Labor labor) {
        String sql = "UPDATE Labor SET tvaRate = ? WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, labor.getTVARate());
            pstmt.setInt(2, labor.getId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Labor TVA updated successfully.");
            } else {
                System.out.println("No Labor found with ID: " + labor.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

