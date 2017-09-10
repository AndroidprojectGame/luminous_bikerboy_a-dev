package com.example.acer.bikerboy.models;

/**
 * Created by techfour on 8/9/17.
 */

public class ViewRetalerModel
{
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact_persion() {
        return contact_persion;
    }

    public void setContact_persion(String contact_persion) {
        this.contact_persion = contact_persion;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    String contact_persion;
    String mobile;
    String address;

    public String getCity_area() {
        return city_area;
    }

    public void setCity_area(String city_area) {
        this.city_area = city_area;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    String city_area;
    String state;
}
