package dataSource;

import Data.Partner;
import Data.Person;
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
    public boolean createStateChange(Project p, int personID)
    {
        return pm.createStateChange(p, personID, con);
    }
    
    public boolean createProject(Project p){
        return pm.createProject(p, con);
    }
    
    public ArrayList<Project> getProjects(){
        return pm.getProjects(con);
    }
    
    public ArrayList<String> getStateNames(){
        return pm.getStateNames(con);
    }
    
    public ArrayList<Partner> getPartnerInfo(){
        return pm.getPartnerInfo(con);
    }
    
    public Person logIn(String userName, String password) {
        return pm.logIn(userName, password, con);
    }
    
    public Project getProject(int projectID)
    {
        return pm.getProject(con, projectID);
    }
    
    public Project getLatestProject(int projectID)
    {
        return pm.getLatestProject(con, projectID);
    }
    
    public int getNumberOfUsers()
    {
        return pm.getNumberOfUsers(con);
    }
    public int getNumberOfPartners()
    {
        return pm.getNumberOfPartners(con);
    }
    public boolean saveProject(Project p){
        return pm.saveProject(p, con);
    }
    public boolean nextProjectState(Project p)
    {
        return pm.nextProjectState(p, con);
    }
}
