package com.baticuisines.repository;

import com.baticuisines.entity.Component;
import com.baticuisines.entity.Project;
import com.baticuisines.entity.componentType.Labor;
import com.baticuisines.entity.componentType.Material;

import java.util.List;

public interface ComponentRepositoryInterface {
        Material saveMaterial(Material material);
        Labor saveLabor(Labor labor);
        List<Material> getMaterialsByProjectId(int projectId);
        List<Labor> getLaborsByProjectId(int projectId);

        void updateMaterial(Material material);
        void updateLabor(Labor labor);
//        Optional<Project> findById(Long id);
//        List<Project> findAll();
//        void delete(Long id);
}
