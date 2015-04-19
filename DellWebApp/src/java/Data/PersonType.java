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
public class PersonType {
    
    int personTypeID;
    String personTypeName;

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
