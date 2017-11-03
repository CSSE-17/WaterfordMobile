package models;

/**
 *
 * @author Pasan
 */
public class DeliveryModel {
    private String deliveryId;
    private String orderDate;
    private float deliveryCost;
    private int deliveryQty;
    private float deliverycount;
    private float deliveryCount;

    public DeliveryModel(String delivery_id, String order_date, float delivery_cost, int delivery_qty) {
        this.deliveryId = delivery_id;
        this.orderDate = order_date;
        this.deliveryCost = delivery_cost;
        this.deliveryQty = delivery_qty;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public float getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(float deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public int getDeliveryQty() {
        return deliveryQty;
    }

    public void setDeliveryQty(int deliveryQty) {
        this.deliveryQty = deliveryQty;
    }
   /*public float getDeliveryCount(){
       return deliveryCount;
   }
   public void setDeliveryCount(float deliverycount){
       this.deliverycount=deliverycount;
   }*/
}
