/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.DatePicker;
import util.JDBC;

/**
 *
 * @author hp
 */
public class customer {

    private String customer_id;
    private String customer_name;
    private String contact_no;
    private String address;
    private String email_address;
    private String remark;
    public int getCustomerCount;
    private String date;

    public customer() {
        this.customer_id = "";
        this.customer_name = "";
        this.contact_no = "";
        this.address = "";
        this.email_address = "";
        this.remark = "";
        this.date="";

    }

    public customer(String customer_id, String customer_name, String contact_no, String address, String email_address, String remark, String date) {

        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.contact_no = contact_no;
        this.address = address;
        this.email_address = email_address;
        this.remark = remark;
        this.date =date;

    }

    public customer(String customer_id, String customer_name, String contact_no, String address, String email_address,String date) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getCoustomerId() {
        return customer_id;
    }
    public void setCoustomerId(String customer_id) {
        this.customer_id = customer_id;
    }
    
    public String getCoustomerName() {
        return customer_name;
    }
    public void setCoustomerName() {
        this.customer_name=customer_name;
    }

    public String getContactNo() {
        return contact_no;
    }
    public void setContactNo(){
        this.contact_no=contact_no;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress() {
        this.address=address;
    }
    public String getEmailAddress() {
        return email_address;
    }
    public void  setEmailAddress(){
        this.email_address=email_address;
    }
    
    public String getRemark() {
        return remark;
    }
    public void setRemark(){
        this.remark=remark;
    }
    
    public String getDate (){
    return date;
    }
    public void setDate (String date){
        this.date=date;
    }

    
    public int getCustomerCount() {
        int count = 0;
        JDBC j = new JDBC();

        try {
            ResultSet rst = j.getData("SELECT MAX(customer_id) FROM customer");
            if (rst.next()) {
                count = rst.getInt(1);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return count;
    }

    
    
    public void AddNewCustomer(customer cus) {

        String customer_id = cus.customer_id;
        String customer_name = cus.customer_name;
        String contact_no = cus.contact_no;
        String address = cus.address;
        String email_address = cus.email_address;
        String remark = cus.remark;
        String date = cus.date;

        JDBC j = new JDBC();
        j.setData("INSERT INTO customer(customer_id, customer_name, contact_no, address, email_address, remark, date)"
                + "VALUES('" + customer_id + "', '" + customer_name + "', '" + contact_no + "','" + address + "','" + email_address + "','" + remark + "','"+date+"')");

    }

    public void updateCustomer(customer cus) {

        String customer_id = cus.customer_id;
        String customer_name = cus.customer_name;
        String contact_no = cus.contact_no;
        String address = cus.address;
        String email_address = cus.email_address;
        String remark = cus.remark;
        String date = cus.date;

        JDBC j = new JDBC();
       
        j.setData("UPDATE customer SET customer_name='" + customer_name + "',contact_no='" + contact_no + 
                "',address='" + address + "',email_address='" + email_address + "',remark='" + remark + "',date+'"+date+"')where cusomter_id='"+customer_id+"' ");

    }

    public void deleteCustomer(String customer_id) {
        JDBC j = new JDBC();
        j.setData("DELETE from customer WHERE customer_id='" + customer_id + "'");
    }

    //public void deletecustomer(String customer_id) {
    //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    //}
}
