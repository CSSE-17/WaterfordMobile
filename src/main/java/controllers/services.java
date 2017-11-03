/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;


import java.sql.ResultSet;
import util.JDBC;


public class services {

    static String getservice_id() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String service_id;
    private String name ;
    private String address;
    private String contact_number ;

    public services ()
    {
        this.service_id = "";
        this.name = "";
        this.address ="";
        this.contact_number = "";

    }
    public services (String service_id, String name, String address, String contact_number){

        this.service_id = service_id;
        this.name = name;
        this.address = address;
        this.contact_number = contact_number;
    }
    public String getService_id(){
        return service_id;
    }

    public void service_id(String service_id){
        this.service_id=service_id;
    }

    public String getname(){
        return name;
    }

    public void setname(String name){
        this.name= name;
    }

    public String address(){
        return address;
    }

    public void setaddress(String address) {
        this.address=address;
    }

    public String getContact_number (){
        return contact_number;
    }

    public void setContact_number(String contact_number){
        this.contact_number= contact_number;
    }



    public void addNewServices(services ser) {
        String service_id = ser.service_id;
        String name = ser.name;
        String address = ser.address;
        String contact_number = ser.contact_number;


        JDBC j = new JDBC();
        j.setData("INSERT INTO service(service_id, name, address, contact_number)"
                +"Values('"+service_id+"', '"+name+"', '"+address+"', '"+contact_number+"')");

    }
    public void updateNewServices(services ser){
        String service_id = ser.service_id;
        String name = ser.name;
        String address = ser.address;
        String contact_number = ser.contact_number;

        JDBC j = new JDBC();

        j.setData ("UPDATE services SET service_id='"+service_id+"',name='"+name+"',address='"+address+"',contact_number='"+contact_number+"'"+"WHERE service_id='"+service_id+"'");
    }

    public void deleteServices(String ser_id){
        JDBC j = new JDBC();
        j.setData("DELETE from services WHERE service_id='"+"'");

    }

    void addNewservices(services ser) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void setData(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}





