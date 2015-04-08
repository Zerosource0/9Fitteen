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
   
    public Project createProject(String name){
        Project p = new Project(name);
        boolean status = dbf.createProject(p);
        if(!status) {
            p = null;
        }
        return p;
    }
    
    public ArrayList<Project> getProjects(){
        return dbf.getProjects();
    }
}
