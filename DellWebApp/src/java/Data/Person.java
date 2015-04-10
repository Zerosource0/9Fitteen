package Data;

/**
 *
 * @author Adam
 */
public class Person {
    private int id;
    private int rights;
    
    public Person (int id, int rights)
    {
        this.id=id;
        this.rights=rights;
    }
    public int getID ()
    {
      return this.id;
    }
    public int getRights ()
    {
        return this.rights;
    }
}
