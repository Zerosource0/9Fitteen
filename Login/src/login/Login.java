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
public class Login {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        CoreLogic cl = new CoreLogic();
        String password="12345678";
        String login="JOB@mail.dk";
        Person pe;
        pe=cl.logIn(login, password);
        if (pe!=null) System.out.println(pe.getID()+ " "+pe.getRights());
        //boolean state=cl.logIn(login, password);
       // if (state==true ) System.out.println("Login Succes");
        //else System.out.println("Login failed");
    }
    
}
