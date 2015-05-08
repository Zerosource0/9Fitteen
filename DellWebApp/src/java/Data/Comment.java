package Data;
//Author: Marc
public class Comment {
    
    private String comment;
    private int personID;
    private String timeStamp;
    private String personName;

    public Comment(String comment, int personID, String timeStamp) {
        this.comment = comment;
        this.personID = personID;
        this.timeStamp = timeStamp;
    } 

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
 
    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    
}
