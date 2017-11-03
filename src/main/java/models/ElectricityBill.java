/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author DinuX
 */
public class ElectricityBill {
    private String ebill_no;
    private double eamount;
    private int eunits;
    private String payment_date;
    
    public ElectricityBill(){
        this.ebill_no="";
        this.eamount=0;
        this.eunits=0;
        this.payment_date="";
    }
    public ElectricityBill(String billno,double amount,int units,String payment_date ){
        this.ebill_no=billno;
        this.eamount=amount;
        this.eunits=units;
        this.payment_date=payment_date;
    }

    public String getEbill_no() {
        return ebill_no;
    }

    public void setEbill_no(String ebill_no) {
        this.ebill_no = ebill_no;
    }

    public double getEamount() {
        return eamount;
    }

    public void setEamount(double eamount) {
        this.eamount = eamount;
    }

    public int getEunits() {
        return eunits;
    }

    public void setEunits(int eunits) {
        this.eunits = eunits;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }
}


