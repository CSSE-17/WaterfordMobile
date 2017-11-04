/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Pasan
 */
public class propertyModel {
    private int propertyId;
    private int electricityBill;
    private int waterBill;
    private float rentalValue;

    public propertyModel(int pid, int elec_bill_id, int water_bill_id, float rental) {
        this.propertyId = pid;
        this.electricityBill = elec_bill_id;
        this.waterBill = water_bill_id;
        this.rentalValue = rental;
    }



    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public int getElectricityBill() {
        return electricityBill;
    }

    public void setElectricityBill(int electricityBill) {
        this.electricityBill = electricityBill;
    }

    public int getWaterBill() {
        return waterBill;
    }

    public void setWaterBill(int waterBill) {
        this.waterBill = waterBill;
    }

    public float getRentalValue() {
        return rentalValue;
    }

    public void setRentalValue(float rentalValue) {
        this.rentalValue = rentalValue;
    }
}
