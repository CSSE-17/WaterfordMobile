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
public class Profit {
    private String date;
    private double income;
    private double profit;
    private double expense;
    
    
    public Profit(){
        this.expense=0;
        this.profit=0;
        this.income=0;
        this.date="";
    }
    public Profit(String date,double income,double expense,double profit){
        this.profit=profit;
        this.income=income;
        this.expense=expense;
        this.date=date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

    
}
