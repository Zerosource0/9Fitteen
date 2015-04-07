/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSource;

import Data.Project;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 * @author marcj_000
 */
public class ProjectMapper {
    
    public boolean createProject(Project p, Connection con){
        int rowsInserted = 0;
        
        String sqlString1 = "select seq_projectid.nextval"
                + "from dual";
        
        String sqlString2 = "insert into project "
                + "values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = null;
        try{
            
        statement = con.prepareStatement(sqlString1);
        ResultSet rs = statement.executeQuery();
        if(rs.next()){
            p.setId(rs.getInt(1));
        }
        
        statement = con.prepareStatement(sqlString2);
        statement.setInt(1, p.getId());
        statement.setInt(2, p.getFkProjetStateID());
        statement.setString(3, p.getProjectName());
        statement.setString(4, p.getDescription());
        statement.setInt(5, p.getFkpartnerId());
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        statement.setString(6, dateFormat.format(date));
        
        statement.setString(7, null);
        statement.setString(8, dateFormat.format(date));
        statement.setInt(9, 0);
        statement.setString(10, null);
        statement.setString(11, null);
        statement.setString(12, null);
        statement.setString(13, null);
        }
        catch(Exception e){
        
        }
            finally
            {
                try{
                    statement.close();
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
            }
        return rowsInserted == 1;
   }
}
