/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.ResultSet;
import util.JDBC;

/**
 *
 * @author Sampath Perera
 */
public class Exchange {
    private String date;
    private String invoice_no;
    private String item_no;
    private int tot_item_qty;
    private String exchange_id;
    private int ret_qty;
    private String description;
    private double returnCost;
    private String comments;
    private double subReturn;
    private String customer;
    private String employee;

  
    
    public Exchange(){
        this.date="";
        this.invoice_no="";
        this.item_no= "";
        this.tot_item_qty=0;
        this.description="";
        this.exchange_id="";
        this.ret_qty=0;
        this.comments="";
        this.subReturn=0;
        this.returnCost=0;
        this.customer="";
        this.customer="";
    } 

    public int getTot_item_qty() {
        return tot_item_qty;
    }

    public void setTot_item_qty(int tot_item_qty) {
        this.tot_item_qty = tot_item_qty;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public double getReturnCost() {
        return returnCost;
    }

    public void setReturnCost(double returnCost) {
        this.returnCost = returnCost;
    }
    
     public double getSubReturn() {
        return subReturn;
    }

    public void setSubReturn(double subReturn) {
        this.subReturn = subReturn;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
  

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInvoice_no() {
        return invoice_no;
    }

    public void setInvoice_no(String invoice_no) {
        this.invoice_no = invoice_no;
    }

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }

    public String getExchange_id() {
        return exchange_id;
    }

    public void setExchange_id(String exchange_id) {
        this.exchange_id = exchange_id;
    }

    public int getRet_qty() {
        return ret_qty;
    }

    public void setRet_qty(int ret_qty) {
        this.ret_qty = ret_qty;
    }
    
      public Exchange(String exchange_id, String ret_date, String inv_no, String item_no,String description, int qty,String comm,double ret_cost) {
     this.exchange_id=exchange_id;
     this.date=ret_date;
     this.invoice_no=inv_no;
     this.item_no=item_no;
     this.description=description;
     this.ret_qty=qty;
     this.comments=comm;
     this.returnCost=ret_cost;
    }
      public String generateExchangeId() {
        int r_count = 1;

        JDBC j = new JDBC();
        try {
            ResultSet rset = j.getData("SELECT COUNT(exchange_id) FROM waterford_mobile.exchange");
            if (rset.next()) {
                r_count = rset.getInt(1)+1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        int exchange_id = r_count;
        String ex_id = Integer.toString(exchange_id);
        return ex_id;
    }
    
}
