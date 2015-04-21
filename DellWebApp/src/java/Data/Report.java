/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.util.ArrayList;

/**
 *
 * @author Jonas
 */
public class Report {
   ArrayList<String> Countries = new ArrayList(); 
   ArrayList<Integer> NoPartners = new ArrayList(); 
   ArrayList<Integer> NoProjectDone = new ArrayList(); 
   ArrayList<Integer> MoneySpent = new ArrayList(); 
   ArrayList<Integer> avgSpentPartner = new ArrayList();

    public Report() {
        
    }
   
   

    public ArrayList<String> getCountries() {
        return Countries;
    }

    public ArrayList<Integer> getNoPartners() {
        return NoPartners;
    }

    public ArrayList<Integer> getNoProjectDone() {
        return NoProjectDone;
    }

    public ArrayList<Integer> getMoneySpent() {
        return MoneySpent;
    }

    public ArrayList<Integer> getAvgSpentPartner() {
        return avgSpentPartner;
    }

   
    
}
