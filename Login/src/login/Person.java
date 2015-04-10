/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

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
}
