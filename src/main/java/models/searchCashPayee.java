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
public class searchCashPayee {
    
    private String chpayee;
    
    public searchCashPayee(){
        
        this.chpayee="";
        
    }
    public searchCashPayee(String payee){
        
        this.chpayee=payee;
    }

    

    public String getChpayee() {
        return chpayee;
    }

    public void setChpayee(String chpayee) {
        this.chpayee = chpayee;
    }
}
