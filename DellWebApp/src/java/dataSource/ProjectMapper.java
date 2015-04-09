package dataSource;

import Data.Project;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProjectMapper {

  //public ArrayList<String> getPartnerNames(Connection con){}
    public ArrayList<String> getStateNames(Connection con) {
        ArrayList<String> stateNames = new ArrayList<>();

        String sqlString1 = "select projectstatename from projectstate";
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(sqlString1);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                stateNames.add(rs.getString(1));
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
        return stateNames;
    }

    public ArrayList<Project> getProjects(Connection con) {

        ArrayList<Project> projects = new ArrayList<>();

        String sqlString1 = "select * from project order by projectid";

        //PreparedStatement statement = null;
        try (PreparedStatement statement = con.prepareStatement(sqlString1);
                ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }

        return projects;
    }

    public boolean createProject(Project p, Connection con) {
        int rowsInserted = 0;

        /* String sqlString1 = "select seq_projectid.nextval"
         + "from dual";*/
        String sqlString1 = "insert into project (PROJECTNAME,FKPARTNERID)"
                + " values (?,?)";

        //the future one
        String sqlString2 = "insert into project (PROJECTNAME,FKPARTNERID,PROJECTDESCRIPTION,FUNDSALLOCATED)"
                + " values (?,?,?,?)";

        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(sqlString2);
            statement.setString(1, p.getProjectName());
            statement.setInt(2, p.getFkPartnerId());
            statement.setString(3, p.getDescription());
            statement.setInt(4, p.getFundsAllocated());
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
