package dataSource;

import Data.Comment;
import Data.Partner;
import Data.Person;
import Data.PersonType;
import Data.Project;
import Data.Report;
import java.io.InputStream;
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
                        rs.getLong(9)
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
                        rs.getLong(9)
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
                        rs.getLong(9)
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return projects;
    }

    public ArrayList<PersonType> getPersonTypes(Connection con) {

        ArrayList<PersonType> personTypes = new ArrayList<>();

        String sqlString = "select * from persontype";

        try (PreparedStatement statement = con.prepareStatement(sqlString);
                ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                personTypes.add(
                        new PersonType(rs.getInt(1), rs.getString(2))
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return personTypes;
    }

    public ArrayList<Person> getPersons(Connection con) {

        ArrayList<Person> persons = new ArrayList<>();

        String sqlString = "select person.*, PersonType.PERSONTYPENAME "
                + "from person, PersonType "
                + "where person.FKPERSONTYPEID=Persontype.PERSONTYPEID ORDER BY personid";

        try (PreparedStatement statement = con.prepareStatement(sqlString);
                ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                persons.add(new Person(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getString(6),
                        ""
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return persons;
    }

    public ArrayList<Project> getProjects(Connection con, int partnerID) {
        System.out.println("PM partnerID: " + partnerID);
        ArrayList<Project> projects = new ArrayList<>();

        String sqlString1 = "SELECT * FROM PROJECT PR JOIN PARTNER PA ON PR.FKPARTNERID = PA.PARTNERID WHERE PA.PARTNERID = " + partnerID;

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
                        rs.getLong(9)
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return projects;
    }

    public ArrayList<Project> getProjectsMyAction(Connection con, int partnerID) {
        System.out.println("PM partnerID: " + partnerID);
        ArrayList<Project> projects = new ArrayList<>();

        String sqlString1 = "select * from Project PR join partner PA on PR.fkpartnerid = pa.partnerid where partnerid = " + partnerID + " and PR.FKPROJECTSTATEID in (3,5,6)";

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
                        rs.getLong(9)
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
                        rs.getLong(9)
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

    public boolean savePartner(Partner partner, Connection con) {

        int rowsUpdated = 0;

        String sqlString = "update partner set partnername = ?, partneraddress = ?, partnerzipcode = ?, partnercountry = ? where partnerid = " + partner.getPartnerID();
        try (PreparedStatement statement = con.prepareStatement(sqlString)) {
            statement.setString(1, partner.getPartnerName());
            statement.setString(2, partner.getPartnerAddress());
            statement.setInt(3, partner.getPartnerZip());
            statement.setString(4, partner.getPartnerCountry());

            rowsUpdated = statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsUpdated == 1;
    }

    public boolean savePerson(Person per, Connection con) {
        int rowsUpdated = 0;

        String sqlString = "update person set personname = ?, personphonenumber = ?, fkpersontypeid = ?, fkpartnerid = ? where personid = " + per.getID();

        try (PreparedStatement statement = con.prepareStatement(sqlString)) {
            statement.setString(1, per.getName());
            statement.setInt(2, per.getPhoneNumber());
            statement.setInt(3, per.getFkPersonTypeID());
            statement.setInt(4, per.getFkPartnerID());

            rowsUpdated = statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (rowsUpdated == 1) {
            rowsUpdated = 0;
            String sqlString3 = "UPDATE PERSONLOGIN SET PERSONEMAIL = '" + per.getEmail() + "' where fkpersonid = " + per.getID();

            try (PreparedStatement statement3 = con.prepareStatement(sqlString3)) {

                rowsUpdated = statement3.executeUpdate();
                System.out.println("ROWSUPDATED: " + rowsUpdated);
            } catch (SQLException e) {
                e.printStackTrace();
            }
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

    public ArrayList<String> getCountries(Connection con) {
        ArrayList<String> countries = new ArrayList<>();

        String sqlString1 = "select DISTINCT PARTNERCOUNTRY from partner";

        try (PreparedStatement statement = con.prepareStatement(sqlString1);
                ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                countries.add(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return countries;
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
            if (fkpartnerid == null) {
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
        String sqlString1 = "update QUARTER SET totalfund = totalfund - " + amount;

        try (PreparedStatement pre2 = con.prepareStatement(sqlString1);
                ResultSet rs2 = pre2.executeQuery();) {
            rs2.next();
            FundsLeft = rs2.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return FundsLeft;
    }

    public String getPassword(int personID, Connection con) {
        String password = null;

        String sqlString = "SELECT PERSONPASSWORD FROM PERSONLOGIN WHERE FKPERSONID = " + personID;

        try (PreparedStatement pre = con.prepareStatement(sqlString);
                ResultSet rs = pre.executeQuery()) {

            if (rs.next()) {
                password = rs.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return password;
    }

    public boolean changePassword(String password, int personID, Connection con) {
        int rowsUpdated = 0;

        String sqlString = "UPDATE PERSONLOGIN SET PERSONPASSWORD = '" + password + "' WHERE FKPERSONID = " + personID;

        try (PreparedStatement statement = con.prepareStatement(sqlString)) {

            rowsUpdated = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowsUpdated == 1;
    }

    public Person addPerson(Connection con) {
        Person person = null;
        int rowsInserted = 0;

        String sqlString = "INSERT INTO PERSON (PERSONNAME,PERSONPHONENUMBER,FKPersonTypeID) "
                + "VALUES ('Name',12345678,1)";

        try (PreparedStatement statement1 = con.prepareStatement(sqlString)) {

            rowsInserted = statement1.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sqlString2 = "Select * from person where personname = 'Name'";

        if (rowsInserted == 1) {

            try (PreparedStatement statement2 = con.prepareStatement(sqlString2);
                    ResultSet rs = statement2.executeQuery()) {
                if (rs.next()) {
                    person = new Person(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getInt(3),
                            rs.getInt(4),
                            rs.getInt(5)
                    );
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            String sqlString3 = "INSERT INTO PERSONLOGIN (FKPERSONID,PERSONEMAIL,PERSONPASSWORD)"
                    + " VALUES (" + person.getID() + ",'email','password')";

            try (PreparedStatement statement3 = con.prepareStatement(sqlString3)) {

                rowsInserted = statement3.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        person.setEmail("email");
        return person;
    }

    public boolean deletePerson(int personID, Connection con) {

        int rowsUpdated = 0;

        String sqlString1 = "DELETE FROM PERSONLOGIN WHERE FKPERSONID = " + personID;

        try (PreparedStatement statement1 = con.prepareStatement(sqlString1)) {

            rowsUpdated = statement1.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sqlString2 = "delete from person where personid = " + personID;

        try (PreparedStatement statement1 = con.prepareStatement(sqlString2)) {

            rowsUpdated = statement1.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsUpdated == 1;
    }

    public Report getReport(Connection con) {
        Report report = new Report();

        String sqlString = "select DISTINCT PARTNERCOUNTRY, (select count(partnerid) from partner PAR where PAR.PARTNERCOUNTRY = PA.PARTNERCOUNTRY),(select count(projectID) from project PR join partner PART on PR.FKpartnerID = PART.partnerid where PR.FKPROJECTSTATEID = 9 and PART.PARTNERCOUNTRY = PA.PARTNERCOUNTRY),(select sum(FUNDSALLOCATED) from project PRO join partner PARTN on PRO.FKPARTNERID = PARTN.PARTNERID where PRO.FKPROJECTSTATEID = 9 and PARTN.PARTNERCOUNTRY = PA.PARTNERCOUNTRY) ,((select sum(FUNDSALLOCATED) from project PRO join partner PARTN on PRO.FKPARTNERID = PARTN.PARTNERID where PRO.FKPROJECTSTATEID = 9 and PARTN.PARTNERCOUNTRY = PA.PARTNERCOUNTRY)/(select count(partnerid) from partner PAR where PAR.PARTNERCOUNTRY = PA.PARTNERCOUNTRY)) from partner PA ORDER BY PA.PARTNERCOUNTRY";

        try (PreparedStatement statement = con.prepareStatement(sqlString);
                ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                report.getCountries().add(rs.getString(1));
                report.getNoPartners().add(rs.getInt(2));
                report.getNoProjectDone().add(rs.getInt(3));
                report.getMoneySpent().add(rs.getInt(4));
                report.getAvgSpentPartner().add(rs.getInt(5));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report;

    }

    public Person getPerson(int personID, Connection con) {
        Person person = null;
        String sqlString = "select person.*, PersonType.PERSONTYPENAME, personlogin.personemail "
                + "from person, PersonType, personlogin "
                + "where person.FKPERSONTYPEID=Persontype.PERSONTYPEID and PERSONLOGIN.fkpersonID=PERSON.PERSONID and PERSON.PERSONID = " + personID
                + " ORDER BY personid";

        try (PreparedStatement statement = con.prepareStatement(sqlString);
                ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                person = new Person(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getString(7)
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    public ArrayList<Comment> getComments(int projectID, Connection con) {
        ArrayList<Comment> comments = new ArrayList();

        String sqlString = "Select comments, fkpersonid, dateofstatechange from projectStatePerson where FKprojectID = " + projectID + " order by dateofstatechange";

        try (PreparedStatement statement = con.prepareStatement(sqlString);
                ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                String comment = rs.getString(1);
                System.out.println("Comment: " + comment);
                if (comment != null) {
                    comments.add(new Comment(
                            comment,
                            rs.getInt(2),
                            rs.getString(3)
                    )
                    );
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    public boolean saveComment(Project p, int personID, String comment, Connection con) {
        int rowsInserted = 0;

        String sqlString1 = "insert into PROJECTSTATEPERSON (FKPersonID, FKProjectStateID, FKProjectID, Comments)"
                + "VALUES (?,?,?,?)";
        try (PreparedStatement statement = con.prepareStatement(sqlString1)) {

            statement.setInt(1, personID);
            statement.setInt(2, p.getFkProjetStateID());
            statement.setInt(3, p.getId());
            statement.setString(4, comment);
            rowsInserted = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsInserted == 1;
    }

    public boolean upload(int projectID, int projectStateID, InputStream inputStream, Connection con) {

        try {
            String sql = "INSERT INTO poe_pictures (fkprojectid, fkprojectstateid, poe) values (?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, projectID);
            statement.setInt(2, projectStateID);
            if (inputStream != null) {
                statement.setBlob(3, inputStream);
            }
            int row = statement.executeUpdate();
            return row > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
