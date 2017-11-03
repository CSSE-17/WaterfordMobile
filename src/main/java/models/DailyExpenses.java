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
public class DailyExpenses {
    private int id;
    private String date;
    private double amount;
    private String description;
    
    public DailyExpenses(){
        this.id=0;
        this.date="";
        this.amount=0;
        this.description="";
    }
    public DailyExpenses(int did,String ddate,double damount,String ddescription){
        this.id=did;
        this.date=ddate;
        this.amount=damount;
        this.description=ddescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
