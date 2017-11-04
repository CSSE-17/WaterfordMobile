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
public class RentalPayment {
    private int rid;
    private double ramount;
    private String rpayment_date;
    
    public RentalPayment(){
        this.rid=0;
        this.ramount=0;
        this.rpayment_date="";
    }
    public RentalPayment(int rental_id,double amount,String payment_date){
        this.rid=rental_id;
        this.ramount=amount;
        this.rpayment_date=payment_date;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public double getRamount() {
        return ramount;
    }

    public void setRamount(double ramount) {
        this.ramount = ramount;
    }

    public String getRpayment_date() {
        return rpayment_date;
    }

    public void setRpayment_date(String rpayment_date) {
        this.rpayment_date = rpayment_date;
    }
}
