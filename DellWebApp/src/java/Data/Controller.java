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
    
    public boolean createStateChange(Project p, int personID)
    {
        return dbf.createStateChange(p, personID);
    }
    
    public Project getProject(int projectID){
        return dbf.getProject(projectID);
    }
    public Project getLatestProject(int projectID){
        return dbf.getLatestProject(projectID);
    }
    
    public ArrayList<Project> getProjects(){
        return dbf.getProjects();
    }
    
    public ArrayList<String> getStateNames(){
        return dbf.getStateNames();
    }
    
    public ArrayList<Partner> getParnerInfo(){
        return dbf.getPartnerInfo();
    }
    
    public Person logIn(String userName, String password) {
        Person person = dbf.logIn(userName, password);
        
        return person;   
    }
    
    public int getNumberOfUsers()
    {
        return dbf.getNumberOfUsers();
    }
    public int getNumberOfPartners()
    {
        return dbf.getNumberOfPartners();
    }
    public Boolean nextProjectState(Project p)
    {
        return dbf.nextProjectState(p);
    }
}
