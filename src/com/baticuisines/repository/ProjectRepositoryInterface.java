package com.baticuisines.repository;

import com.baticuisines.entity.Devis;
import com.baticuisines.entity.Project;
import com.baticuisines.enums.ProjectStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProjectRepositoryInterface {
        Project save(Project project);
        boolean updateProfiteMargin(Project project);
        void updateProjectTotalCost(Project project);
        Devis createDevis(Devis devis);
        Optional<Project> findById(int id);
        List<Project> findAll();
//        void delete(Long id);
}
