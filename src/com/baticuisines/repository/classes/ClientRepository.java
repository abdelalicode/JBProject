package com.baticuisines.repository.classes;

import com.baticuisines.entity.Client;
import com.baticuisines.repository.ClientRepositoryInterface;
import ressources.DBconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepository implements ClientRepositoryInterface {

    private static Connection conn = DBconnection.getInstance().getConnection();
    List<Client> clients = new ArrayList<>();


    public Client save(Client client) {
        String query = "INSERT INTO client (name, address, phonenumber) VALUES (?, ?, ?) RETURNING id, isprofessional";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, client.getName());
            statement.setString(2, client.getAddress());
            statement.setString(3, client.getPhone());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                client.setId(resultSet.getInt("id"));
                client.setProfessional(resultSet.getBoolean("isprofessional"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    public List<Client> findAll() {

        String query = "SELECT * FROM client";

        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phonenumber");
                boolean isProfessional = resultSet.getBoolean("isprofessional");

                Client client = new Client(id , name, address, phone , isProfessional);
                clients.add(client);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }



    public Optional<Client> findByName(String name) {
        return findAll().stream()
                .filter(client -> client.getName().equalsIgnoreCase(name))
                .findFirst();
    }



}
