package com.example.acer.bikerboy.models;

import java.io.Serializable;

/**
 * Created by Anusha on 15-03-2016.
 */
public class Distributor {

    public String ID, Dis_Name, Dis_Sap_Code;

  /*    public String getDistributorId() {
        return id;
    }

    public void setDistributorId(String id) {
        this.id = id;
    }

  public String getDistributorName() {
        return dis_name;
    }

    public void setDistributorName(String dis_name) {
        this.dis_name = dis_name;
    } */

    public Distributor(String id, String name, String sap_code) {
       // super();
        ID = id;
        Dis_Name = name;
        Dis_Sap_Code = sap_code;
        //this.dis_name = dis_name;
    }
}
