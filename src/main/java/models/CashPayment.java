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
public class CashPayment {
    private int chid;
    private double chamount;
    private String chdate;
    private String chpayee;
    
    public CashPayment(){
        this.chid=0;
        this.chamount=0;
        this.chdate="";
        this.chpayee="";
        
    }
    public CashPayment(int id,double amount,String date,String payee){
        this.chid=id;
        this.chamount=amount;
        this.chdate=date;
        this.chpayee=payee;
    }

    public int getChid() {
        return chid;
    }

    public void setChid(int chid) {
        this.chid = chid;
    }

    public double getChamount() {
        return chamount;
    }

    public void setChamount(double chamount) {
        this.chamount = chamount;
    }

    public String getChdate() {
        return chdate;
    }

    public void setChdate(String chdate) {
        this.chdate = chdate;
    }

    public String getChpayee() {
        return chpayee;
    }

    public void setChpayee(String chpayee) {
        this.chpayee = chpayee;
    }
}
