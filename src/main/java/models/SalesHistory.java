/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author User
 */
public class SalesHistory {
    private String date_from;
    private String date_to;
    private double tot_sales;
    
    public SalesHistory(){
        this.date_from="";
        this.date_to="";
        this.tot_sales=0;
        
    }

    public String getDate_from() {
        return date_from;
    }

    public void setDate_from(String date_from) {
        this.date_from = date_from;
    }

    public String getDate_to() {
        return date_to;
    }

    public void setDate_to(String date_to) {
        this.date_to = date_to;
    }

    public double getTot_sales() {
        return tot_sales;
    }

    public void setTot_sales(double tot_sales) {
        this.tot_sales = tot_sales;
    }
    
    
}
