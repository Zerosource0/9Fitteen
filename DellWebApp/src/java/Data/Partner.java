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
public class Partner {
    
    int partnerID;
    String partnerName;
    String partnerAddress;
    int partnerZip;
    String partnerCountry;

    public int getPartnerID() {
        return partnerID;
    }

    public void setPartnerID(int partnerID) {
        this.partnerID = partnerID;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerAddress() {
        return partnerAddress;
    }

    public void setPartnerAddress(String partnerAddress) {
        this.partnerAddress = partnerAddress;
    }

    public int getPartnerZip() {
        return partnerZip;
    }

    public void setPartnerZip(int partnerZip) {
        this.partnerZip = partnerZip;
    }

    public String getPartnerCountry() {
        return partnerCountry;
    }

    public void setPartnerCountry(String partnerCountry) {
        this.partnerCountry = partnerCountry;
    }
    

    public Partner(int partnerID, String partnerName, String partnerAddress, int partnerZip, String partnerCountry) {
        this.partnerID = partnerID;
        this.partnerName = partnerName;
        this.partnerAddress = partnerAddress;
        this.partnerZip = partnerZip;
        this.partnerCountry = partnerCountry;
    }
    
}
