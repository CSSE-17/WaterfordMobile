package models;

import util.JDBC;
/**
 *
 * @author Pasan
 */
public class delivery {

    public static String getdelivery_id() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void setData(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private String delivery_id;
    private String order_date ;
    private float delivery_cost;
    private int quantity ;

    public delivery ()
    {
        this.delivery_id = "";
        this.order_date = "";
        this.delivery_cost = 0;
        this.quantity = 0;

    }
    public delivery (String delivery_id, String order_date, Float delivery_cost, int quantity){

        this.delivery_id = delivery_id;
        this.order_date = order_date;
        this.delivery_cost = delivery_cost;
        this.quantity = quantity;
    }

    public void addNewdelivery(delivery del) {
        String delivery_id = del.delivery_id;
        String order_date = del.order_date;
        Float delivery_cost = del.delivery_cost;
        int quantity = del.quantity;


        JDBC j = new JDBC();
        j.setData("INSERT INTO delivery(Delivery_ID, Oder_Date, Delivery_cost, Quantity)"
                +"Values('"+delivery_id+"', '"+order_date+"', '"+delivery_cost+"', '"+quantity+"')");

    }

    public String getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(String delivery_id) {
        this.delivery_id = delivery_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public float getDelivery_cost() {
        return delivery_cost;
    }

    public void setDelivery_cost(float delivery_cost) {
        this.delivery_cost = delivery_cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void updateNewDelivery(delivery del){
        String delivery_id = del.delivery_id;
        String order_date = del.order_date;
        float delivery_cost = del.delivery_cost;
        int quantity = del.quantity;

        JDBC j = new JDBC();
        j.setData("UPDATE delivery SET delivery_id='"+delivery_id+"',order_date'"+order_date+"',delivery_cost'"+delivery_cost+"',quantity'"+quantity+"'"+"WHERE delivery_id='"+delivery_id+"'");
    }

    public void deleteDelivery(String del_id){
        JDBC j = new JDBC();
        j.setData("DELETE from delivery WHERE delivery_id='"+"'");
    }

    public int getDeliveryCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getDeliveryId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void AddNewDelivery(delivery del) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}







