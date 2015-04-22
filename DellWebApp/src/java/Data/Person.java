package Data;

public class Person {

    private int id;
    private String name;
    private int phoneNumber;
    private int fkPersonTypeID;
    private String personTypeName;
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPersonTypeName() {
        return personTypeName;
    }

    public void setFkPersonTypeName(String fkPersonTypeName) {
        this.personTypeName = fkPersonTypeName;
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

    public Person(String name, int phoneNumber, int fkPersonTypeID, Integer fkPartnerID) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.fkPersonTypeID = fkPersonTypeID;
        this.fkPartnerID = fkPartnerID;
    }

    public Person(int id, String name, int phoneNumber, int fkPersonTypeID, Integer fkPartnerID, String personTypeName, String email) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.fkPersonTypeID = fkPersonTypeID;
        this.fkPartnerID = fkPartnerID;
        this.personTypeName = personTypeName;
        this.email = email;
    }
    public Person(int id, String name, int phoneNumber, int fkPersonTypeID, Integer fkPartnerID) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.fkPersonTypeID = fkPersonTypeID;
        this.fkPartnerID = fkPartnerID;
    }
    public Person(int id, String name, int phoneNumber, int fkPersonTypeID, Integer fkPartnerID, String email) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.fkPersonTypeID = fkPersonTypeID;
        this.fkPartnerID = fkPartnerID;
        this.email = email;
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
