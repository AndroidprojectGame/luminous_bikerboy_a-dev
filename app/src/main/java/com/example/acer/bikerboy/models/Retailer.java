package com.example.acer.bikerboy.models;

/**
 * Created by Anusha on 16-03-2016.
 */
public class Retailer {

    public String ID, Name, Code, Contact_Number, Address, CityArea, State, Contact_Person;

    public Retailer(String id, String code, String name, String contact_Number, String address, String cityArea, String state, String contact_Person) {
        // super();
        ID = id;
        Code = code;
        Name = name;
        Contact_Number = contact_Number;
        Address = address;
        CityArea = cityArea;
        State = state;
        Contact_Person = contact_Person;
        //this.dis_name = dis_name;
    }
}
