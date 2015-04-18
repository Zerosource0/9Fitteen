/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import dataSource.DBFacade;
import java.util.ArrayList;


/**
 *
 * @author marcj_000
 */
public class Controller {
    
    //Singleton
    private static Controller instance;
    private DBFacade dbf;
    private Person person = null;

    private Controller()
    {
        dbf = DBFacade.getInstance();
    }

    public static Controller getInstance()
    {
        if (instance == null)
        {
            instance = new Controller();
        }
        return instance;
    }
   
    public Project createProject(String projectName, String description, int fkPartnerId, long fundsAllocated){
        Project p = new Project(projectName, description, fkPartnerId, fundsAllocated);
        boolean status = dbf.createProject(p);
        if(!status) {
            p = null;
        }
        return p;
    }
    
    public Project saveProject(int projectID, String projectName, String desc, int partnerID,  Long funds){
        Project p = new Project(projectID, projectName, desc, partnerID, funds);
        boolean status = dbf.saveProject(p);
        System.out.println("SOUT STATUS: " + status);
        if(!status) {
            p = null;
        }
        return p;
    }
    
    public Project createProject(String projectName, String description, int fkPartnerId) {
        Project p = new Project(projectName, description, fkPartnerId);
        boolean status = dbf.createProject(p);
        if(!status) {
            p = null;
        }
        return p;
    }
    
    public boolean createStateChange(Project p, int personID){
        return dbf.createStateChange(p, personID);
    }
    
    public Project getProject(int projectID){
        return dbf.getProject(projectID);
    }
    public Project getLatestProject(){
        return dbf.getLatestProject();
    }
    
    public ArrayList<Project> getProjects(){
        return dbf.getProjects();
    }
     public ArrayList<Project> getProjectsMyAction(int partnerID){
        return dbf.getProjectsMyAction(partnerID);
    }
     public ArrayList<Project> getProjectsMyAction(){
        return dbf.getProjectsMyAction();
    }
    public ArrayList<Project> getProjects(int partnerID){
        return dbf.getProjects(partnerID);
    }
    
    public ArrayList<String> getStateNames(){
        return dbf.getStateNames();
    }
    
    public ArrayList<Partner> getPartnerInfo(){
        return dbf.getPartnerInfo();
    }
    public long getFundsAllocated()
    {
        return dbf.getFundsAllocated();
    }
    
    public boolean logIn(String userName, String password) {
        person = dbf.logIn(userName, password);
        
        return person != null;
    }
    
    
    public void logOut() {
        person = null;
    }
    
    public int getNumberOfUsers()
    {
        return dbf.getNumberOfUsers();
    }
    public int getNumberOfPartners()
    {
        return dbf.getNumberOfPartners();
    }
    public Boolean updateProjectState(Project p)
    {
        return dbf.updateProjectState(p);
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) 
    {
        this.person = person;
    }
    
    public ArrayList<Person> getPersons()
    {
        return dbf.getPersons();
    }

}
