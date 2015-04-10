/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Adam
 */
public class CoreLogic {
    //private DBconnector instance= new DBconnector();
    
    public Person logIn (String login, String password)
    {
        Connection con= DBconnector.getInstance().getConnection();
        Person pe1=null;
        try {
            PreparedStatement pre=con.prepareStatement("Select FKPERSONID "
                    + "from PERSONLOGIN where PERSONEMAIL='"+login+"' and PERSONPASSWORD='"+password+"'");
            ResultSet rs=pre.executeQuery();
            if (rs.next()==false) return null;
            else
            {
                int rights, id;
                id=rs.getInt(1);
                pre=con.prepareStatement("SELECT fkpersontypeid " +
                                            "FROM person, persontype " +
                  "Where person.personid="+id+ " and fkpersontypeid=persontypeid");
                
                rs=pre.executeQuery();
                rs.next();
                rights=rs.getInt(1);
                Person pe = new Person(id, rights);
                pe1=pe;
                
            }
            
        } catch (SQLException ex) {
            System.out.println("Wrong statement");
        }
        
        
        return pe1;
    }
    
}
