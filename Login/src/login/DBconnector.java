/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

//import com.sun.jndi.ldap.Connection;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Adam
 */
public class DBconnector {
    private static String driver = "oracle.jdbc.driver.OracleDriver";
    private static String URL = "jdbc:oracle:thin:@datdb.cphbusiness.dk:1521:dat";
    private static String id = "cphjb186";	//Insert Your ORACLE id and password
    private static String pw = "cphjb186";
    
    private Connection con;
    //private static DBconnector instance;
    private static DBconnector instance;
    public DBconnector ()
    {
        
        try
        {
            //Class.forName(driver);
            this.con = DriverManager.getConnection(URL, id, pw);
            
            
        }
        catch (Exception e)
        {
             System.out.println("Ooooops sth went wrong");
             System.out.println(e);
                    
        }
        
        
    }
    public static DBconnector getInstance ()
    {
        if (instance==null) 
        {
            instance = new DBconnector();
        }
        return instance;
        
    }
    public Connection getConnection()
    {
        
        return this.con;
    }
}
