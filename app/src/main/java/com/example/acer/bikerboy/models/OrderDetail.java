package com.example.acer.bikerboy.models;

/**
 * Created by Anusha on 17-03-2016.
 */
public class OrderDetail {

    public String Order_ID, Dist_Name, Beat_Name, Retailer_Name, Status, Required_FanQuantity, Pending_FanQuantity, Required_WireQuantity, Pending_WireQuantity, Required_LightQuantity, Pending_LightQuantity;

    public OrderDetail(String orderId, String dname, String bname, String rname, String status, String fanreqQty, String fanpending, String wirereqQty, String wirepending, String lightreqQty, String lightpending) {
        // super();
        Order_ID = orderId;
        Dist_Name = dname;
        Beat_Name = bname;
        Retailer_Name = rname;
        Status = status;
        Required_FanQuantity = fanreqQty;
        Pending_FanQuantity = fanpending;
        Required_WireQuantity = wirereqQty;
        Pending_WireQuantity = wirepending;
        Required_LightQuantity = lightreqQty;
        Pending_LightQuantity = lightpending;


        //this.dis_name = dis_name;
    }
}
