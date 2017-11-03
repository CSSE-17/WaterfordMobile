package models;

import javax.persistence.*;

@Entity
@Table(name = "supplier", schema = "waterford_mobile", catalog = "")
public class SupplierEntity {
    private String supplierId;
    private String supplierName;
    private String description;
    private String address;
    private String tpnumber;
    private String email;
    private String dateJoined;
    private String bank;
    private String branch;
    private String accntNo;

    public SupplierEntity() {
    }

    public SupplierEntity(String supplierId, String supplierName, String description, String address, String tpnumber, String email, String dateJoined, String bank, String branch, String accntNo) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.description = description;
        this.address = address;
        this.tpnumber = tpnumber;
        this.email = email;
        this.dateJoined = dateJoined;
        this.bank = bank;
        this.branch = branch;
        this.accntNo = accntNo;
    }

    @Id
    @Column(name = "Supplier_ID")
    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    @Basic
    @Column(name = "Supplier_name")
    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    @Basic
    @Column(name = "Description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "Address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "Tpnumber")
    public String getTpnumber() {
        return tpnumber;
    }

    public void setTpnumber(String tpnumber) {
        this.tpnumber = tpnumber;
    }

    @Basic
    @Column(name = "Email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "Date_joined")
    public String getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;
    }

    @Basic
    @Column(name = "Bank")
    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    @Basic
    @Column(name = "Branch")
    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    @Basic
    @Column(name = "Accnt_no")
    public String getAccntNo() {
        return accntNo;
    }

    public void setAccntNo(String accntNo) {
        this.accntNo = accntNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SupplierEntity that = (SupplierEntity) o;

        if (supplierId != null ? !supplierId.equals(that.supplierId) : that.supplierId != null) return false;
        if (supplierName != null ? !supplierName.equals(that.supplierName) : that.supplierName != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (tpnumber != null ? !tpnumber.equals(that.tpnumber) : that.tpnumber != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (dateJoined != null ? !dateJoined.equals(that.dateJoined) : that.dateJoined != null) return false;
        if (bank != null ? !bank.equals(that.bank) : that.bank != null) return false;
        if (branch != null ? !branch.equals(that.branch) : that.branch != null) return false;
        if (accntNo != null ? !accntNo.equals(that.accntNo) : that.accntNo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = supplierId != null ? supplierId.hashCode() : 0;
        result = 31 * result + (supplierName != null ? supplierName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (tpnumber != null ? tpnumber.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (dateJoined != null ? dateJoined.hashCode() : 0);
        result = 31 * result + (bank != null ? bank.hashCode() : 0);
        result = 31 * result + (branch != null ? branch.hashCode() : 0);
        result = 31 * result + (accntNo != null ? accntNo.hashCode() : 0);
        return result;
    }
}
