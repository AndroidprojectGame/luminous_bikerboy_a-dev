package com.example.acer.bikerboy.models;

/**
 * Created by Anusha on 17-03-2016.
 */
public class Order {

    public String Order_ID, Beat_Code, Beat_Name, Retailer_Code, Retailer_Name, Destributor_Code, Destributor_Name;

    public Order(String id, String bcode, String bname, String rcode, String rname, String dcode, String dname) {
        // super();
        Order_ID = id;
        Beat_Code = bcode;
        Beat_Name = bname;
        Retailer_Code = rcode;
        Retailer_Name = rname;
        Destributor_Code = dcode;
        Destributor_Name = dname;


        //this.dis_name = dis_name;
    }
}
