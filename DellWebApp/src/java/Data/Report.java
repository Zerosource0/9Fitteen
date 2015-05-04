package Data;

import java.util.ArrayList;

/**
 *
 * @author Jonas
 */
public class Report {

    private ArrayList<String> countries;
    private ArrayList<Integer> noPartners;
    private ArrayList<Integer> noProjectDone;
    private ArrayList<Integer> moneySpent;
    private ArrayList<Integer> avgSpentPartner;

    public Report() {
        countries = new ArrayList<>();
        noPartners = new ArrayList<>();
        noProjectDone = new ArrayList<>();
        moneySpent = new ArrayList<>();
        avgSpentPartner = new ArrayList<>();
    }

    public ArrayList<String> getCountries() {
        return countries;
    }

    public ArrayList<Integer> getNoPartners() {
        return noPartners;
    }

    public ArrayList<Integer> getNoProjectDone() {
        return noProjectDone;
    }

    public ArrayList<Integer> getMoneySpent() {
        return moneySpent;
    }

    public ArrayList<Integer> getAvgSpentPartner() {
        return avgSpentPartner;
    }

}
