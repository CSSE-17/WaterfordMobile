/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import util.JDBC;

/**
 *
 * @author Pasan
 */
public class Property {

    private int pId;
    private int eBillId;
    private int wBillId;
    private float rental;

    public Property() {
        this.pId = 0;
        this.eBillId = 0 ;
        this.wBillId = 0 ;
        this.rental = 0.0f ;
    }

    public Property(int eBillId, int wBillId, float rental) {

        this.eBillId = eBillId;
        this.wBillId = wBillId;
        this.rental = rental;
    }

    public Property(int pid, int eBillId, int wBillId, float rental) {

        this.pId = pid;
        this.eBillId = eBillId;
        this.wBillId = wBillId;
        this.rental = rental;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public int geteBillId() {
        return eBillId;
    }

    public void seteBillId(int eBillId) {
        this.eBillId = eBillId;
    }

    public int getwBillId() {
        return wBillId;
    }

    public void setwBillId(int wBillId) {
        this.wBillId = wBillId;
    }

    public float getRental() {
        return rental;
    }

    public void setRental(float rental) {
        this.rental = rental;
    }

//    add new properties to db

    public void addNewProperty(Property prop){
        int eBillId = prop.eBillId;
        int wBillId = prop.wBillId;
        float rental = prop.rental;

        JDBC j = new JDBC();
        j.setData("INSERT INTO property (eBillId,wBillId,rental) VALUES('"+eBillId+"', '"+wBillId+"', '"+rental+"')");
    }

    public void updateProperty(Property prop){
        int pid = prop.pId;
        int eBillId = prop.eBillId;
        int wBillId = prop.wBillId;
        float rental = prop.rental;

        JDBC j = new JDBC();
        j.setData("UPDATE property (eBillId,wBillId,rental) VALUES('"+eBillId+"', '"+wBillId+"', '"+rental+"') where pid='"+pid+"' ");
    }

}
