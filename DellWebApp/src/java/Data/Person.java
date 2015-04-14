package Data;

/**
 *
 * @author Adam
 */
public class Person {
    private int id, phoneNumber;
    private int rights;
    private Integer fkpersonid;
    String name;
    
    
    public Person (int id, int rights, Integer fkpersonid, String name, int phoneNumber)
    {
        
        
        
        this.id=id;
        this.rights=rights;
        this.phoneNumber=phoneNumber;
        this.name=name;
        this.fkpersonid=fkpersonid;
        //this.pass=pass;
        
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
}
