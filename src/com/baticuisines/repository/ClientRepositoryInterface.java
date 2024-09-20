package com.baticuisines.repository;

import com.baticuisines.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepositoryInterface {
        Client save(Client client);
//        Optional<Client> findById(Long id);
          List<Client> findAll();
//        void delete(Long id);
          Optional<Client> findByName(String name);
//        List<Client> findByProfessionalStatus(boolean isProfessional);
}
