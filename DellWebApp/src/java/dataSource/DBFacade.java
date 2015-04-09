package dataSource;

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
    
    public boolean createProject(Project p){
        return pm.createProject(p, con);
    }
    
    public ArrayList<Project> getProjects(){
        return pm.getProjects(con);
    }
    
    public ArrayList<String> getStateNames(){
        return pm.getStateNames(con);
    }
}
