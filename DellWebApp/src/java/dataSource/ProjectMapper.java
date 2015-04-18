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

    public Project getLatestProject(Connection con) {
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

    public ArrayList<Person> getPersons(Connection con) {
        
        ArrayList<Person> persons = new ArrayList<>();
        
        String sqlString = "select person.*, PersonType.PERSONTYPENAME " +
                            "from person, PersonType " +
                            "where person.FKPERSONTYPEID=Persontype.PERSONTYPEID";
        
        try (PreparedStatement statement = con.prepareStatement(sqlString);
                ResultSet rs = statement.executeQuery()){
                while(rs.next()){
                    persons.add(new Person(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getInt(3),
                            rs.getInt(4),
                            rs.getInt(5),
                            rs.getString(6)
                    ));
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return persons;
    }
    
    
    public ArrayList<Project> getProjects(Connection con, int partnerID) {
        System.out.println("PM partnerID: "+partnerID);
        ArrayList<Project> projects = new ArrayList<>();

        String sqlString1 = "SELECT * FROM PROJECT PR JOIN PARTNER PA ON PR.FKPARTNERID = PA.PARTNERID WHERE PA.PARTNERID = "+partnerID;

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
    
    public ArrayList<Project> getProjectsMyAction(Connection con, int partnerID) {
        System.out.println("PM partnerID: "+partnerID);
        ArrayList<Project> projects = new ArrayList<>();

        String sqlString1 = "select * from Project PR join partner PA on PR.fkpartnerid = pa.partnerid where partnerid = "+ partnerID +" and PR.FKPROJECTSTATEID in (3,5,6)";

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
    public ArrayList<Project> getProjectsMyAction(Connection con) {
        ArrayList<Project> projects = new ArrayList<>();

        String sqlString1 = "select * from Project where FKPROJECTSTATEID in (1,2,4,7,8) order by datelastedit DESC";

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

    public boolean updateProjectState(Project p, Connection con) {
        int rowsUpdated = 0;

        String sqlString1 = "update project set fkprojectstateid = ? where projectid = " + p.getId();

        try (PreparedStatement statement = con.prepareStatement(sqlString1)) {
            statement.setInt(1, p.getFkProjetStateID());

            rowsUpdated = statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsUpdated == 1;
    }

    public boolean saveProject(Project p, Connection con) {
        int rowsUpdated = 0;

        String sqlString1 = "update project set projectname = ?,  projectdescription = ?, fkpartnerid = ?,  fundsallocated = ? where projectid = " + p.getId();

        try (PreparedStatement statement = con.prepareStatement(sqlString1)) {
            statement.setString(1, p.getProjectName());
            statement.setString(2, p.getDescription());
            statement.setInt(3, p.getFkPartnerId());
            statement.setLong(4, p.getFundsAllocated());

            rowsUpdated = statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsUpdated == 1;
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

        try (PreparedStatement pre1 = con.prepareStatement(sqlString1);
                ResultSet rs1 = pre1.executeQuery();) {

            if (rs1.next() == false) {
                return null;
            } else {

                return createPersonClass(con, rs1.getInt(1));

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public Person createPersonClass(Connection con, int id) {

        int rights = 0, phoneNumber = 0;
        Integer fkpartnerid = 0;
        String name = null;
        String sqlString2 = "SELECT * "
                + "FROM person, persontype "
                + "Where person.personid=" + id + " and fkpersontypeid=persontypeid";

        try (PreparedStatement pre2 = con.prepareStatement(sqlString2);
                ResultSet rs2 = pre2.executeQuery();) {

            rs2.next();
            id = rs2.getInt(1);
            name = rs2.getString(2);
            phoneNumber = rs2.getInt(3);
            rights = rs2.getInt(4);
            fkpartnerid = rs2.getInt(5);
            if(fkpartnerid==null)
            {
                fkpartnerid = 1;
            }
            sqlString2
                    = "Select Partner.PARTNERNAME, PROJECT.*"
                    + " from Project, person, projectstateperson, partner"
                    + " where Person.PERSONID=" + id + " and Person.PersonID=PROJECTSTATEPERSON.FKPERSONID"
                    + " and PROJECTSTATEPERSON.FKPROJECTID=Project.PROJECTID"
                    + " and Partner.PARTNERID=Project.FKPARTNERID";
            PreparedStatement pre = con.prepareStatement(sqlString2);
            ResultSet rs = pre.executeQuery();

            return new Person(id, rights, fkpartnerid, name, phoneNumber);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;

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
    
    public int getFundsAllocated(Connection con) {
        int FundsLeft = 0;
        String sqlString1 = "select totalFund from Quarter";

        try (PreparedStatement pre2 = con.prepareStatement(sqlString1);
                ResultSet rs2 = pre2.executeQuery();) {
            rs2.next();
            FundsLeft = rs2.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return FundsLeft;
    }

    
        public int useFunds(int amount, Connection con) {
        int FundsLeft = 0;
        String sqlString1 = "update QUARTER SET totalfund = totalfund - "+amount;

        try (PreparedStatement pre2 = con.prepareStatement(sqlString1);
                ResultSet rs2 = pre2.executeQuery();) {
            rs2.next();
            FundsLeft = rs2.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return FundsLeft;
    }
}
