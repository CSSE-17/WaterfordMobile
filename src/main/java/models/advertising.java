/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.Alert;
import util.JDBC;

/**
 *
 * @author dell
 */
public class advertising {

    private String offer_id;
    private String name;
    private float discount;
    private String description;
    private String from;
    private String to;

    public advertising() {
        this.discount = 0;
        this.description = "";
        this.from = "";
        this.name = "";
        this.offer_id = "";
        this.to = "";

    }

    /**
     *
     * @param offer_id
     * @param name
     * @param discount
     * @param description
     * @param from
     * @param to
     */
    public advertising(String offer_id, String name, float discount, String description, String from, String to) {

        this.description = description;
        this.discount = discount;
        this.from = from;
        this.to = to;
        this.name = name;
        this.offer_id = offer_id;

    }

    public String getOffre_id() {
        return offer_id;
    }

    public String getName() {
        return name;
    }

    public float getDiscount() {
        return discount;
    }

    public String getDescription() {
        return description;
    }

    public String getfrom() {
        return from;
    }

    public String getto() {
        return to;
    }

    public void addNewAdvertising(advertising adv) {

        String offer_id = adv.offer_id;
        String name = adv.name;
        float discount = adv.discount;
        String description = adv.description;
        String from = adv.from;
        String to = adv.to;

        JDBC j = new JDBC();
        try {
            j.setData("INSERT INTO offers(offer_id,name,discount,description,effectivefrom,effectiveto)"
                    + "VALUES('" + offer_id + "','" + name + "','" + discount + "','" + description + "','" + from + "','" + to + "')");
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage() +"Cannot be addd");
            alert.showAndWait();
        }


    }

    public void updateNewAdvertising(advertising adv) {
        String offer_id = adv.offer_id;
        String name = adv.name;
        float discount = adv.discount;
        String description = adv.description;
        String effectivefrom = adv.from;
        String effectiveto = adv.to;

        JDBC j = new JDBC();
        j.setData("UPDATE offers SET name='"+name+"', description='"+description+"', discount='"+discount+"', effectivefrom='"+effectivefrom+"', effectiveto='"+effectiveto+"' WHERE offer_id='"+offer_id+"' ");
    }

   public void deleteAdvertising(String offer_id){
        JDBC j = new JDBC();
        j.setData("DELETE from offers WHERE offer_id='"+offer_id+"'");
    }




   public int getOfferCount(){
        int count = 0;
       JDBC j = new JDBC();
       
       try {
           ResultSet rst = j.getData("SELECT COUNT(offer_id)FROM offers");
           if (rst.next()){
               count = rst.getInt(1);
           
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return count;
       }

    public void setData(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
           
   
   
   }
    

