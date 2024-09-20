package com.baticuisines.repository;

import com.baticuisines.entity.Project;
import com.baticuisines.enums.ProjectStatus;

import java.util.List;
import java.util.Optional;

public interface ProjectRepositoryInterface {
        Project save(Project project);
//        Optional<Project> findById(Long id);
//        List<Project> findAll();
//        void delete(Long id);
}
