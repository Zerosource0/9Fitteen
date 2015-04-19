package Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adam
 */
public class Person {

    private int id;
    private String name;
    private int phoneNumber;
    private int fkPersonTypeID;
    private String PersonTypeName;

    public String getPersonTypeName() {
        return PersonTypeName;
    }

    public void setFkPersonTypeName(String fkPersonTypeName) {
        this.PersonTypeName = fkPersonTypeName;
    }
    private Integer fkPartnerID;

    public Person(int id, int rights, Integer fkpartnerid, String name, int phoneNumber) {

        this.id = id;
        this.fkPersonTypeID = rights;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.fkPartnerID = fkpartnerid;
        //this.pass=pass;

    }

    public Person(int id, String name, int phoneNumber, int fkPersonTypeID, Integer fkPartnerID, String PersonTypeName) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.fkPersonTypeID = fkPersonTypeID;
        this.fkPartnerID = fkPartnerID;
        this.PersonTypeName = PersonTypeName;
    }
    public Person(int id, String name, int phoneNumber, int fkPersonTypeID, Integer fkPartnerID) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.fkPersonTypeID = fkPersonTypeID;
        this.fkPartnerID = fkPartnerID;
    }

    public int getID() {
        return this.id;
    }

    public int getFkPersonTypeID() {
        return this.fkPersonTypeID;
    }

    public int getPhoneNumber() {
        return this.phoneNumber;
    }

    public Integer getFkPartnerID() {
        return this.fkPartnerID;
    }

    public String getName() {
        return this.name;
    }
}
