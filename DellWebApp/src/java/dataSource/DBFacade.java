/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSource;

import Data.Project;
import java.sql.Connection;

/**
 *
 * @author marcj_000
 */
public class DBFacade {

    private ProjectMapper pm;
    private Connection con;

    //== Singleton start
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
}
