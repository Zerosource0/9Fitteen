/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSource;

import Data.Project;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author marcj_000
 */
public class ProjectMapper {

    public ArrayList<Project> getProjects(Connection con) {

        ArrayList<Project> projects = new ArrayList<Project>();

        String sqlString1 = "select * from project";

        PreparedStatement statement = null;
        try {

            statement = con.prepareStatement(sqlString1);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
               try{ 
                projects.add(new Project(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getInt(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13)
                ));
               }
               catch(Exception e)
               {
                   e.printStackTrace();
               }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return projects;
    }

    public boolean createProject(Project p, Connection con) {
        int rowsInserted = 0;

        /* String sqlString1 = "select seq_projectid.nextval"
         + "from dual";*/
        String sqlString1 = "insert into project (FKPROJECTSTATEID,PROJECTNAME,FKPARTNERID)"
                + " values (?,?,?)";

        //the future one
        String sqlString2 = "insert into project (PROEJCTNAME,FKPARTNERID)"
                + " values (?,?)";

        PreparedStatement statement = null;
        try {

            statement = con.prepareStatement(sqlString1);
            statement.setInt(1, p.getFkProjetStateID());
            statement.setString(2, p.getProjectName());
            statement.setInt(3, p.getFkProjetStateID());
            rowsInserted = statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rowsInserted == 1;
    }
}
