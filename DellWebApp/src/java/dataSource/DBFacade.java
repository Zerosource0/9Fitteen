package dataSource;

import Data.Comment;
import Data.Partner;
import Data.Person;
import Data.PersonType;
import Data.PoeFile;
import Data.Project;
import Data.Report;
import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;

/**
 *
 * @author marcj_000
 */
public class DBFacade {

    private ProjectMapper pm;
    

    private static DBFacade instance;

    private DBFacade() {
        pm = new ProjectMapper();
    }

    public static DBFacade getInstance() {
        if (instance == null) {
            instance = new DBFacade();
        }
        return instance;
    }

    public boolean createStateChange(Project p, int personID) {
        return pm.createStateChange(p, personID);
    }
    public boolean upload (int projectID, int projectStateID, InputStream inputStream, String fileName)
    {
        return pm.upload(projectID, projectStateID, inputStream, fileName);
    }
    public boolean createProject(Project p) {
        return pm.createProject(p);
    }

    public ArrayList<Project> getProjects() {
        return pm.getProjects();
    }

    public ArrayList<Project> getProjectsMyAction(int partnerID) {
        return pm.getProjectsMyAction(partnerID);
    }

    public ArrayList<Project> getProjectsMyAction() {
        return pm.getProjectsMyAction();
    }

    public ArrayList<Project> getProjects(int partnerID) {
        System.out.println("DBF Partner ID" + partnerID);
        return pm.getProjects(partnerID);
    }

    public ArrayList<String> getStateNames() {
        return pm.getStateNames();
    }

    public ArrayList<Partner> getPartnerInfo() {
        return pm.getPartnerInfo();
    }
     public ArrayList<String> getCountries() {
        return pm.getCountries();
    }


    public Person logIn(String userName, String password) {
        return pm.logIn(userName, password);
    }

    public Project getProject(int projectID) {
        return pm.getProject(projectID);
    }
    
    public Report getReport(){
        return pm.getReport();
    }

    public Project getLatestProject() {
        return pm.getLatestProject();
    }

    public int getNumberOfUsers() {
        return pm.getNumberOfUsers();
    }

    public int getNumberOfPartners() {
        return pm.getNumberOfPartners();
    }

    public boolean saveProject(Project p) {
        return pm.saveProject(p);
    }

    public boolean updateProjectState(Project p) {
        return pm.updateProjectState(p);
    }

    public ArrayList<Person> getPersons() {
        return pm.getPersons();
    }
    
    public long getFundsAllocated() {
        return pm.getFundsAllocated();
    }

    public void useFunds(int amount) {
        pm.useFunds(amount);
    }
    public ArrayList<PersonType> getPersonTypes()
    {
        return pm.getPersonTypes();
    }
    public boolean savePerson(Person per) {
        return pm.savePerson(per);
    }
    public boolean savePartner(Partner partner) {
        return pm.savePartner(partner);
    }
    
    public String getPassword(int personID) {
        return pm.getPassword(personID);
    }
    
    public boolean changePassword(String password, int personID) {
        return pm.changePassword(password, personID);
    }
    
    public Person addPerson(){
        return pm.addPerson();
    }
    public boolean deletePerson(int personID){
        return pm.deletePerson(personID);
    }
    public Person getPerson(int personID){
        return pm.getPerson(personID);
    }
        
    public boolean saveComment(Project p, int personID, String comment){
        return pm.saveComment(p, personID, comment);
    }
    
    public ArrayList<Comment> getComments(int projectID){
        return pm.getComments(projectID);
    }
    
    public ArrayList<byte[]> getImage(int projectID) {
        return pm.getImage(projectID);
    }
    
    public int getNumberOfPoe(int projectID) {
        return pm.getNumberOfPoe(projectID);
    }
    
    public ArrayList<PoeFile> getPoeFiles(int projetID){
        return pm.getPoeFiles(projetID);
    }
}
