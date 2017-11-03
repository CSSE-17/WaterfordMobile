/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import util.JDBC;

/**
 *
 * @author Waruna
 */
public class InvenModel {

    private String Item_code;
    private String Item_name;
    private String description;
    private int quantity;
    private String receivefrm;
    private String receivedate;
    private String Expiredate;
    private int min_level;
    private Double unit_price;

    public InvenModel() {
        this.Item_code = "";
        this.Item_name = "";
        this.description = "";
        this.quantity = 0;
        this.receivefrm = "";
        this.receivedate = "";
        this.Expiredate = "";
        this.min_level = 0;
        this.unit_price = 0.0;

    }

    public InvenModel(String Item, String name, String descript, int quant, String rcvfrm, String rcvdate, String exrdate, Double unit_price, int min_level) {
        this.Item_code = Item;
        this.Item_name = name;
        this.description = descript;
        this.quantity = quant;
        this.receivefrm = rcvfrm;
        this.receivedate = rcvdate;
        this.Expiredate = exrdate;
        this.unit_price = unit_price;
        this.min_level = min_level;

    }

    public int getMin_level() {
        return min_level;
    }

    public void setMin_level(int min_level) {
        this.min_level = min_level;
    }

    public Double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(Double unit_price) {
        this.unit_price = unit_price;
    }

    public String getItem_code() {
        return Item_code;
    }

    public void setItem_code(String Item_code) {
        this.Item_code = Item_code;
    }

    public String getItem_name() {
        return Item_name;
    }

    public void setItem_name(String Item_name) {
        this.Item_name = Item_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getReceivefrm() {
        return receivefrm;
    }

    public void setReceivefrm(String receivefrm) {
        this.receivefrm = receivefrm;
    }

    public String getReceivedate() {
        return receivedate;
    }

    public void setReceivedate(String receivedate) {
        this.receivedate = receivedate;
    }

    public String getExpiredate() {
        return Expiredate;
    }

    public void setExpiredate(String Expiredate) {
        this.Expiredate = Expiredate;
    }

    public void addNewInventory(InvenModel inventory) {

        JDBC j = new JDBC();
        j.setData("INSERT INTO Inventory(Item_code,Item_name,Description,Quantity,Receive_from,Receive_date,Expire_date,Unit_price,Minqty_level) VALUES ('" + inventory.Item_code + "','" + inventory.Item_name + "','" + inventory.description + "','" + inventory.quantity + "','" + inventory.receivefrm + "','" + inventory.receivedate + "','" + inventory.Expiredate + "','" + inventory.unit_price + "','" + inventory.min_level + "' )");
    }
}
