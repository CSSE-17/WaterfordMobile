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
public class searchChequePayee {
    
    private String chqpayee;
    
    
    public searchChequePayee(){
        
        chqpayee="";
        
        
    }
    public searchChequePayee(String payee){
        
        this.chqpayee=payee;
        
    }

   

    public String getChqpayee() {
        return chqpayee;
    }

    public void setChqpayee(String chqpayee) {
        this.chqpayee = chqpayee;
    }

   
}
