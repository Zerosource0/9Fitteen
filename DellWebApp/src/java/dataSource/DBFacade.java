package dataSource;

import Data.Partner;
import Data.Person;
import Data.PersonType;
import Data.Project;
import java.sql.Connection;
import java.util.ArrayList;

/**
 *
 * @author marcj_000
 */
public class DBFacade {

    private ProjectMapper pm;
    private Connection con;

    private static DBFacade instance;

    private DBFacade() {
        pm = new ProjectMapper();
        con = DBconnector.getInstance().getConnection();
    }

    public static DBFacade getInstance() {
        if (instance == null) {
            instance = new DBFacade();
        }
        return instance;
    }

    public boolean createStateChange(Project p, int personID) {
        return pm.createStateChange(p, personID, con);
    }

    public boolean createProject(Project p) {
        return pm.createProject(p, con);
    }

    public ArrayList<Project> getProjects() {
        return pm.getProjects(con);
    }

    public ArrayList<Project> getProjectsMyAction(int partnerID) {
        return pm.getProjectsMyAction(con, partnerID);
    }

    public ArrayList<Project> getProjectsMyAction() {
        return pm.getProjectsMyAction(con);
    }

    public ArrayList<Project> getProjects(int partnerID) {
        System.out.println("DBF Partner ID" + partnerID);
        return pm.getProjects(con, partnerID);
    }

    public ArrayList<String> getStateNames() {
        return pm.getStateNames(con);
    }

    public ArrayList<Partner> getPartnerInfo() {
        return pm.getPartnerInfo(con);
    }
     public ArrayList<String> getCountries() {
        return pm.getCountries(con);
    }


    public Person logIn(String userName, String password) {
        return pm.logIn(userName, password, con);
    }

    public Project getProject(int projectID) {
        return pm.getProject(con, projectID);
    }

    public Project getLatestProject() {
        return pm.getLatestProject(con);
    }

    public int getNumberOfUsers() {
        return pm.getNumberOfUsers(con);
    }

    public int getNumberOfPartners() {
        return pm.getNumberOfPartners(con);
    }

    public boolean saveProject(Project p) {
        return pm.saveProject(p, con);
    }

    public boolean updateProjectState(Project p) {
        return pm.updateProjectState(p, con);
    }

    public ArrayList<Person> getPersons() {
        return pm.getPersons(con);
    }

    public long getFundsAllocated() {
        return pm.getFundsAllocated(con);
    }

    public void useFunds(int amount) {
        pm.useFunds(amount, con);
    }
    public ArrayList<PersonType> getPersonTypes()
    {
        return pm.getPersonTypes(con);
    }
    public boolean savePerson(Person per) {
        return pm.savePerson(per, con);
    }
    public boolean savePartner(Partner partner) {
        return pm.savePartner(partner, con);
    }
    
    public String getPassword(int personID) {
        return pm.getPassword(personID, con);
    }
    
    public boolean changePassword(String password, int personID) {
        return pm.changePassword(password, personID, con);
    }
    
    public Person addPerson(){
        return pm.addPerson(con);
    }
    public boolean deletePerson(int personID){
        return pm.deletePerson(personID,con);
    }
    public Person getPerson(int personID){
        return pm.getPerson(personID, con);
    }
}
