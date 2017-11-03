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
public class ChequePayments {
    private String chqno;
    private double chqamout;
    private String chqpayee;
    private String chqissuedate;
    private String chqvaliddate;
    
    public ChequePayments(){
        chqno="";
        chqamout=0;
        chqpayee="";
        chqissuedate="";
        chqvaliddate="";
        
    }
    public ChequePayments(String id,double amount,String payee,String issuedate,String validdate){
        this.chqno=id;
        this.chqamout=amount;
        this.chqpayee=payee;
        this.chqissuedate=issuedate;
        this.chqvaliddate=validdate;
    }

    public String getChqno() {
        return chqno;
    }

    public void setChqno(String chqno) {
        this.chqno = chqno;
    }

    public double getChqamout() {
        return chqamout;
    }

    public void setChqamout(double chqamout) {
        this.chqamout = chqamout;
    }

    public String getChqpayee() {
        return chqpayee;
    }

    public void setChqpayee(String chqpayee) {
        this.chqpayee = chqpayee;
    }

    public String getChqissuedate() {
        return chqissuedate;
    }

    public void setChqissuedate(String chqissuedate) {
        this.chqissuedate = chqissuedate;
    }

    public String getChqvaliddate() {
        return chqvaliddate;
    }

    public void setChqvaliddate(String chqvaliddate) {
        this.chqvaliddate = chqvaliddate;
    }
}
