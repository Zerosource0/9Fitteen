package Data;

/**
 *
 * @author marcj_000
 */
public class Project {
 
    private int id;
    private int fkProjetStateID;
    private String projectName;
    private String description;
    private int fkPartnerId;
    private String dateCreated;
    private String dateDone;
    private String dateLastEdit;
    private long fundsAllocated;
    private String poeText;
    private String poePicture1;
    private String poePicture2;
    private String poePicture3;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFkProjetStateID() {
        return fkProjetStateID;
    }

    public void setFkProjetStateID(int fkProjetStateID) {
        this.fkProjetStateID = fkProjetStateID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFkPartnerId() {
        return fkPartnerId;
    }

    public void setFkPartnerId(int fkPartnerId) {
        this.fkPartnerId = fkPartnerId;
    }

    public String getDateCreated() {
        return formatDate(dateCreated.substring(0,10));
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateDone() {
        return formatDate(dateDone.substring(0,10));
    }

    public void setDateDone(String dateDone) {
        this.dateDone = dateDone;
    }

    public String getDateLastEdit() {
        return formatDate(dateLastEdit.substring(0,10));
    }

    public void setDateLastEdit(String dateLastEdit) {
        this.dateLastEdit = dateLastEdit;
    }

    public long getFundsAllocated() {
        return fundsAllocated;
    }

    public void setFundsAllocated(long fundsAllocated) {
        this.fundsAllocated = fundsAllocated;
    }

    public String getPoeText() {
        return poeText;
    }

    public void setPoeText(String poeText) {
        this.poeText = poeText;
    }

    public String getPoePicture1() {
        return poePicture1;
    }

    public void setPoePicture1(String poePicture1) {
        this.poePicture1 = poePicture1;
    }

    public String getPoePicture2() {
        return poePicture2;
    }

    public void setPoePicture2(String poePicture2) {
        this.poePicture2 = poePicture2;
    }

    public String getPoePicture3() {
        return poePicture3;
    }

    public void setPoePicture3(String poePicture3) {
        this.poePicture3 = poePicture3;
    }

    // with funds
    public Project(String projectName, String description, int fkpartnerId, long fundAllocated) {
        this.projectName = projectName;
        this.description = description;
        this.fkPartnerId = fkpartnerId;
        this.fundsAllocated = fundAllocated;
    }

    // without funds
    public Project(String projectName, String description, int fkPartnerId) {
        this.projectName = projectName;
        this.description = description;
        this.fkPartnerId = fkPartnerId;
    }
    
    //For saveProject
    public Project(int id, String projectName, String description, int fkPartnerId, long fundsAllocated) {
        this.id = id;
        this.projectName = projectName;
        this.description = description;
        this.fkPartnerId = fkPartnerId;
        this.fundsAllocated = fundsAllocated;
    }

    
    public Project(int id, int fkProjetStateID, String projectName, String description, int fkpartnerId, String dateCreated, String dateDone, String dateLastEdit, long fundAllocated, String poeText, String poePicture1, String poePicture2, String poePicture3) {
        this.id = id;
        this.fkProjetStateID = fkProjetStateID;
        this.projectName = projectName;
        this.description = description;
        this.fkPartnerId = fkpartnerId;
        this.dateCreated = dateCreated;
        this.dateDone = dateDone;
        this.dateLastEdit = dateLastEdit;
        this.fundsAllocated = fundAllocated;
        this.poeText = poeText;
        this.poePicture1 = poePicture1;
        this.poePicture2 = poePicture2;
        this.poePicture3 = poePicture3;
    }

    public Project(String projectName) {
        this.projectName = projectName;
        this.fkPartnerId = 1;
        
    }
    
    public String formatDate (String date)
    {
        String result="";
        result=result+date.charAt(date.length()-2);
        result=result+date.charAt(date.length()-1);
        result=result+date.charAt(date.length()-3);
        result=result+date.charAt(date.length()-5);
        result=result+date.charAt(date.length()-4);
        result=result+date.charAt(date.length()-3);
        result=result+date.charAt(date.length()-10);
        result=result+date.charAt(date.length()-9);
        result=result+date.charAt(date.length()-8);
        result=result+date.charAt(date.length()-7);
        

        return result;
    }
    
    
    
}
