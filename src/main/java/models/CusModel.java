/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author hp
 */
public class CusModel {
     private String customerId;
    private String customer_name;
    private String contact_no;
    private String address;
    private String email_address;
    private String remark;
    public int getCustomerCount;
    private String date;
    
    public CusModel(String customer_id, String customer_name, String contact_no, String address, String email_address, String remark, String date) {

        this.customerId = customer_id;
        this.customer_name = customer_name;
        this.contact_no = contact_no;
        this.address = address;
        this.email_address = email_address;
        this.remark = remark;
        this.date =date;

    }
     public CusModel(String customer_id, String customer_name, String contact_no, String address, String email_address,String date) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getGetCustomerCount() {
        return getCustomerCount;
    }

    public void setGetCustomerCount(int getCustomerCount) {
        this.getCustomerCount = getCustomerCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

   

}