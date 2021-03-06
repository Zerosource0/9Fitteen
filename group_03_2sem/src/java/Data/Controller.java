package Data;
//Author: Andreas, Jonas, Adam

import dataSource.DBFacade;
import java.io.InputStream;
import java.util.ArrayList;

public class Controller {
    private static Controller instance;
    private DBFacade dbf;
    private Person person;

    private Controller() {
        person = null;
        dbf = DBFacade.getInstance();
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public Project createProject(String projectName, String description, int fkPartnerId, long fundsAllocated) {
        Project p = new Project(projectName, description, fkPartnerId, fundsAllocated);
        boolean status = dbf.createProject(p);
        if (!status) {
            p = null;
        }
        return p;
    }

    public Project saveProject(int projectID, String projectName, String desc, int partnerID, Long funds) {
        Project p = new Project(projectID, projectName, desc, partnerID, funds);
        boolean status = dbf.saveProject(p);

        if (!status) {
            p = null;
        }
        return p;
    }

    public Person savePerson(int personID, String name, int phoneNumber, int personTypeID, int partnerID, String email) {
        Person per = new Person(personID, name, phoneNumber, personTypeID, partnerID, email);
        boolean status = dbf.savePerson(per);

        if (!status) {
            per = null;
        }
        return per;
    }

    public Partner savePartner(int partnerID, String name, String address, int zip, String country) {
        Partner partner = new Partner(partnerID, name, address, zip, country);
        boolean status = dbf.savePartner(partner);

        if (!status) {
            partner = null;
        }
        return partner;
    }

    public Project createProject(String projectName, String description, int fkPartnerId) {
        Project p = new Project(projectName, description, fkPartnerId);
        boolean status = dbf.createProject(p);
        if (!status) {
            p = null;
        }
        return p;
    }

    public boolean createStateChange(Project p, int personID) {
        return dbf.createStateChange(p, personID);
    }

    public Project getProject(int projectID) {
        return dbf.getProject(projectID);
    }

    public Project getLatestProject() {
        return dbf.getLatestProject();
    }
    
    public Report getReport()
    {
        return dbf.getReport();
    }
    public boolean upload (int projectID, int projectStateID, InputStream inputStream, String fileName)
    {
        return dbf.upload(projectID, projectStateID, inputStream, fileName);
    }
    
    public ArrayList<Project> getProjects(int partnerID) {
        if (partnerID != 1) {
            return dbf.getProjects(partnerID);
        } else {
            return dbf.getProjects();
        }
    }
    
    public ArrayList<Project> getProjects() {
        return dbf.getProjects();
    }

    public ArrayList<Project> getProjectsMyAction(int partnerID) {
        return dbf.getProjectsMyAction(partnerID);
    }

    public ArrayList<Project> getProjectsMyAction() {
        return dbf.getProjectsMyAction();
    }

    public ArrayList<String> getStateNames() {
        return dbf.getStateNames();
    }
        public ArrayList<String> getCountries() {
        return dbf.getCountries();
    }

    public ArrayList<Partner> getPartnerInfo() {
        return dbf.getPartnerInfo();
    }

    public long getFundsAllocated() {
        return dbf.getFundsAllocated();
    }

    public void useFunds(int amount) {
        dbf.useFunds(amount);
    }

    public boolean logIn(String userName, String password) {
        person = dbf.logIn(userName, password);

        return person != null;
    }

    public void logOut() {
        person = null;
        //closeConnection();
    }

    public int getNumberOfUsers() {
        return dbf.getNumberOfUsers();
    }

    public int getNumberOfPartners() {
        return dbf.getNumberOfPartners();
    }

    public Boolean updateProjectState(int projectID, int direction) {
        Project project = getProject(projectID);
        
        int state = project.getFkProjetStateID();
        //next
        if (direction == 1) {
            if (state < 9) {
                state = state + direction;
                project.setFkProjetStateID(state);
                if (state == 9) {
                    useFunds((int) project.getFundsAllocated());
                }
            }
        } //back
        else if (direction == -1) {
            if (state > 0) {
                state = state + direction;
                project.setFkProjetStateID(state);
                if (state == 8) {
                    useFunds(-(int) project.getFundsAllocated());
                }
            }
        }
        
        createStateChange(project, person.getID());
        return dbf.updateProjectState(project);
    }

    public Person getCurrentUser() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public ArrayList<Person> getPersons() {
        return dbf.getPersons();
    }

    public ArrayList<PersonType> getPersonTypes() {
        return dbf.getPersonTypes();
    }

    public boolean checkPassword(String current) {
        return current.equals(dbf.getPassword(person.getID()));
    }

    public boolean changePassword(String password, String retype) {
        if (password.equals(retype)) {
            return dbf.changePassword(password, person.getID());
        }

        return false;
    }
    
    public Person addPerson(){
        return dbf.addPerson();
    }
    
    public boolean deletePerson(int personID){
        return dbf.deletePerson(personID);
    }
    
    public Person getPerson(int personID){
        return dbf.getPerson(personID);
    }
    
   
    
    public boolean saveComment(Project p, int personID, String comment){
        return dbf.saveComment(p, personID, comment);
    }
    
    /**
     * 
     * @param projectID
     * @return 
     */
    public ArrayList<Comment> getComments(int projectID){
        
        ArrayList<Comment> comments = dbf.getComments(projectID);
        
        // for each comment in the list get the name of the personID
        comments.stream().forEach((com) -> {  
            String name = getPerson(com.getPersonID()).getName();
            com.setPersonName(name);
        });
        
        return comments;
    }
    
    public byte[] getImage(int projectID, int index) {
        return dbf.getImage(projectID).get(index);
    }
    
    public int getNumberOfPoe(int projectID) {
        return dbf.getNumberOfPoe(projectID);
    }
    
    public ArrayList<PoeFile> getPoeFiles(int projectID){
        return dbf.getPoeFiles(projectID);
    }
    
    public PoeFile getPoeFile(int projectID, int index) {
        return dbf.getPoeFile(projectID, index);
    }
    
    public ArrayList<String> getPoeFileList(int projectID) {
        return dbf.getPoeFileList(projectID);
    }
}
