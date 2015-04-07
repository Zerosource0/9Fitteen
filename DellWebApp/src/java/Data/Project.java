/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    private int fkpartnerId;
    private String dateCreated;
    private String dateDone;
    private String dateLastEdit;
    private int fundAllocated;
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

    public int getFkpartnerId() {
        return fkpartnerId;
    }

    public void setFkpartnerId(int fkpartnerId) {
        this.fkpartnerId = fkpartnerId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateDone() {
        return dateDone;
    }

    public void setDateDone(String dateDone) {
        this.dateDone = dateDone;
    }

    public String getDateLastEdit() {
        return dateLastEdit;
    }

    public void setDateLastEdit(String dateLastEdit) {
        this.dateLastEdit = dateLastEdit;
    }

    public int getFundAllocated() {
        return fundAllocated;
    }

    public void setFundAllocated(int fundAllocated) {
        this.fundAllocated = fundAllocated;
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
    
    
    
    public Project(int id, int fkProjetStateID, String projectName, String description, int fkpartnerId, String dateCreated, String dateDone, String dateLastEdit, int fundAllocated, String poeText, String poePicture1, String poePicture2, String poePicture3) {
        this.id = id;
        this.fkProjetStateID = fkProjetStateID;
        this.projectName = projectName;
        this.description = description;
        this.fkpartnerId = fkpartnerId;
        this.dateCreated = dateCreated;
        this.dateDone = dateDone;
        this.dateLastEdit = dateLastEdit;
        this.fundAllocated = fundAllocated;
        this.poeText = poeText;
        this.poePicture1 = poePicture1;
        this.poePicture2 = poePicture2;
        this.poePicture3 = poePicture3;
    }

    public Project(String projectName) {
        this.projectName = projectName;
        
        
    }
    
    
}
