/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author dell
 */
public class OfferModel {

    private String offer_id;
    private String name;
    private float discount;
    private String description;
    private String effectivefrom;
    private String effectiveto;

    public OfferModel(String offer_id, String name, float discount, String description, String effectivefrom, String effectiveto) {
        this.offer_id = offer_id;
        this.name = name;
        this.discount = discount;
        this.description = description;
        this.effectivefrom = effectivefrom;
        this.effectiveto = effectiveto;
    }

    public String getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEffectivefrom() {
        return effectivefrom;
    }

    public void setEffectivefrom(String effectivefrom) {
        this.effectivefrom = effectivefrom;
    }

    public String getEffectiveto() {
        return effectiveto;
    }

    public void setEffectiveto(String effectiveto) {
        this.effectiveto = effectiveto;
    }

    public Float getdiscount() {
     return discount;//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
public void setdiscount(){
    this.discount=discount;
}
    

}
