package dataSource;

import Data.Partner;
import Data.Person;
import Data.Project;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProjectMapper {

    public ArrayList<Partner> getPartnerInfo(Connection con) {
        ArrayList<Partner> partnerInfo = new ArrayList<>();

        String sqlString1 = "select * from partner";

        try (PreparedStatement statement = con.prepareStatement(sqlString1);
                ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                partnerInfo.add(new Partner(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getString(5)
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return partnerInfo;
    }

    public ArrayList<String> getStateNames(Connection con) {
        ArrayList<String> stateNames = new ArrayList<>();

        String sqlString1 = "select projectstatename from projectstate";

        try (PreparedStatement statement = con.prepareStatement(sqlString1);
                ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                stateNames.add(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stateNames;
    }

    public Project getLatestProject(Connection con, int projectID) {
        String sqlString1 = "select * from(select * from project order by PROJECTID DESC) where rownum=1";
        Project project = null;
        System.out.println("HERE");

        try (PreparedStatement statement = con.prepareStatement(sqlString1);
                ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                project = (new Project(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getLong(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return project;
    }

    public Project getProject(Connection con, int projectID) {
        String sqlString1 = "select * from project where projectid =" + projectID;
        Project project = null;

        try (PreparedStatement statement = con.prepareStatement(sqlString1);
                ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                project = (new Project(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getLong(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return project;
    }

    public ArrayList<Project> getProjects(Connection con) {

        ArrayList<Project> projects = new ArrayList<>();

        String sqlString1 = "select * from project order by projectid";

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
                        rs.getLong(9),
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

    public boolean createStateChange(Project p, int personID, Connection con) {
        int rowsInserted = 0;

        String sqlString1 = "insert into PROJECTSTATEPERSON (FKPersonID, FKProjectStateID, FKProjectID)"
                + "VALUES (?,?,?)";
        try (PreparedStatement statement = con.prepareStatement(sqlString1)) {

            statement.setInt(1, personID);
            statement.setInt(2, p.getFkProjetStateID());
            statement.setInt(3, p.getId());
            rowsInserted = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsInserted == 1;
    }

     public boolean saveProject(Project p, Connection con) {
        int rowsInserted = 0;
        
        String sqlString1 = "update project set projectname = ?,  projectdescription = ?, fkpartnerid =  fundsallocated = ? where id = "+p.getId()
            + " values (?,?,?,?)";
            
        try (PreparedStatement statement = con.prepareStatement(sqlString1)){
            statement.setString(1, p.getProjectName());
            statement.setString(2, p.getDescription());
            statement.setInt(3, p.getFkPartnerId());
            statement.setLong(4, p.getFundsAllocated());
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsInserted == 1;
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

        try (PreparedStatement statement = con.prepareStatement(sqlString2)) {

            statement.setString(1, p.getProjectName());
            statement.setInt(2, p.getFkPartnerId());
            statement.setString(3, p.getDescription());
            statement.setLong(4, p.getFundsAllocated());
            rowsInserted = statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsInserted == 1;
    }

    public Person logIn(String login, String password, Connection con) {

        Person pe1 = null;

        String sqlString1 = "Select FKPERSONID "
                + "from PERSONLOGIN where PERSONEMAIL='" + login.toLowerCase() + "' and PERSONPASSWORD='" + password + "'";
        int rights, id, phoneNumber;
        Integer fkpartnerid;
        String name;
        try (PreparedStatement pre1 = con.prepareStatement(sqlString1);
                ResultSet rs1 = pre1.executeQuery();) {

            if (rs1.next() == false) {
                return null;
            } else {
                
                id = rs1.getInt(1);

                String sqlString2 = "SELECT * " +
                                            "FROM person, persontype " +
                  "Where person.personid="+id+ " and fkpersontypeid=persontypeid" ;

                try (PreparedStatement pre2 = con.prepareStatement(sqlString2);
                        ResultSet rs2 = pre2.executeQuery();) {
                    rs2.next();
                    id=rs2.getInt(1);
                    name=rs2.getString(2);
                    phoneNumber=rs2.getInt(3);
                    rights=rs2.getInt(4);
                    fkpartnerid=rs2.getInt(5);
                }

                Person pe = new Person(id, rights, fkpartnerid, name, phoneNumber);
                pe1 = pe;

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (pe1!=null) 
        {
            System.out.println("id "+pe1.getID()+ " Rights "+pe1.getRights()+" Phone Number "
            +pe1.getPhoneNumber()+" fkpersonid "+pe1.getFkpersonid() +" Name "+pe1.getName());
        }
        return pe1;
    }

    public int getNumberOfUsers(Connection con) {
        int numberOfUsers = 0;
        String sqlString1 = "select count(PERSONID) from PERSON";

        try (PreparedStatement pre2 = con.prepareStatement(sqlString1);
                ResultSet rs2 = pre2.executeQuery();) {
                rs2.next();
             numberOfUsers = rs2.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numberOfUsers;
    }
    
    public int getNumberOfPartners(Connection con) {
        int numberOfPartners = 0;
        String sqlString1 = "select count(PartnerID) from PARTNER";

        try (PreparedStatement pre2 = con.prepareStatement(sqlString1);
                ResultSet rs2 = pre2.executeQuery();) {
                rs2.next();
             numberOfPartners = rs2.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numberOfPartners;
    }

}
