package Data;

public class PersonType {
    
    private int personTypeID;
    private String personTypeName;

    public String getPersonTypeName() {
        return personTypeName;
    }

    public void setPersonTypeName(String personTypeName) {
        this.personTypeName = personTypeName;
    }

    public int getPersonTypeID() {
        return personTypeID;
    }

    public void setPersonTypeID(int personTypeID) {
        this.personTypeID = personTypeID;
    }

    public PersonType(int personTypeID, String personTypeName) {
        this.personTypeName = personTypeName;
        this.personTypeID = personTypeID;
    }
    
    
}
