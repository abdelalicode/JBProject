package com.baticuisines.repository.classes;

import com.baticuisines.entity.Project;
import com.baticuisines.repository.ProjectRepositoryInterface;
import ressources.DBconnection;

import java.sql.*;

public class ProjectRepository implements ProjectRepositoryInterface {

    private static Connection conn = DBconnection.getInstance().getConnection();

    public Project save(Project project) {
        return null;
    }





}
