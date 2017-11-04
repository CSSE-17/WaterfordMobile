/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Waruni
 */
public class ServiceModel {
    private String serviceId;
    private String name;
    private String address;
    private String contactNumber;

    public ServiceModel(String service_id, String name, String address, String contact_number) {
        this.serviceId = service_id;
        this.name = name;
        this.address = address;
        this.contactNumber = contact_number ;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }



}



