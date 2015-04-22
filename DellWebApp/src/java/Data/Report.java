package Data;

import java.util.ArrayList;

/**
 *
 * @author Jonas
 */
public class Report {

    private final ArrayList<String> countries = new ArrayList();
    private final ArrayList<Integer> noPartners = new ArrayList();
    private final ArrayList<Integer> noProjectDone = new ArrayList();
    private final ArrayList<Integer> moneySpent = new ArrayList();
    private final ArrayList<Integer> avgSpentPartner = new ArrayList();

    public Report() {

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
