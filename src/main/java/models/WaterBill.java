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
public class WaterBill {
    private String wbill_no;
    private double wamount;
    private int wunits;
    private String payment_date;
    
    public WaterBill(){
        this.wbill_no="";
        this.wamount=0;
        this.wunits=0;
        this.payment_date="";
    }
    public WaterBill(String billno,double amount,int units,String payment_date ){
        this.wbill_no=billno;
        this.wamount=amount;
        this.wunits=units;
        this.payment_date=payment_date;
    }

    public String getWbill_no() {
        return wbill_no;
    }

    public void setWbill_no(String wbill_no) {
        this.wbill_no = wbill_no;
    }

    public double getWamount() {
        return wamount;
    }

    public void setWamount(double wamount) {
        this.wamount = wamount;
    }

    public int getWunits() {
        return wunits;
    }

    public void setWunits(int wunits) {
        this.wunits = wunits;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }
}
