package Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adam
 */
public class Person {
    private int id, phoneNumber;
    private int rights;
    private Integer fkpersonid;
    private ResultSet projects;
    //private ArrayList<String> partnerName, projectName, projectDescr, dateCreated, dateDone, dateLastEdit;
    //private ArrayList<String> poeText, poePicture1, poePicture2, poePicture3;
    //private ArrayList<Integer> projectID, projectStateID, partnerID, fundsAllocaded;
    String name;
    
    
    public Person (int id, int rights, Integer fkpersonid, String name, int phoneNumber, ResultSet projects)
    {
        
        
        
        this.id=id;
        this.rights=rights;
        this.phoneNumber=phoneNumber;
        this.name=name;
        this.fkpersonid=fkpersonid;
        this.projects=projects;
        //this.pass=pass;
        
    }
    private void splitData (ResultSet projects)
    {
        
    }
    public int getID ()
    {
      return this.id;
    }
    public int getRights ()
    {
        return this.rights;
    }
    public int getPhoneNumber ()
    {
        return this.phoneNumber;
    }
    public Integer getFkpersonid ()
    {
        return this.phoneNumber;
    }
    public String getName ()
    {
        return this.name;
    }
    public void splitData ()
    {
        try {
            if (projects.next())
            {
                do
                {
                    System.out.println(projects.getString(1));
                    System.out.println(projects.getString(2));
                    System.out.println(projects.getString(3));
                    System.out.println(projects.getString(4));
                    System.out.println(projects.getString(5));
                    System.out.println(projects.getString(6));
                    System.out.println(projects.getString(7));
                    System.out.println(projects.getString(8));
                    System.out.println(projects.getString(9));
                    System.out.println(projects.getString(10));
                    System.out.println(projects.getString(11));
                    System.out.println(projects.getString(12));
                    System.out.println(projects.getString(13));
                    System.out.println(projects.getString(14));
                    
                    
                    
                } while (projects.next());
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
