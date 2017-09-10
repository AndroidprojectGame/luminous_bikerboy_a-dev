package com.example.acer.bikerboy.constants;

public class UrlConstants {

    public static final String NAMESPACE = "http://tempuri.org/";
    //public static final String URL = "http://biker.luminousindia.com/webservice/Service1.asmx";
    public static final String URL = "http://50.62.56.149:5055/Service1.asmx";
    // KEYS
    public static final String KEY_CODE = "Code";
    public static final String KEY_DESCRIPTION = "des";
    public static final String KEY_ANY = "anyType{}";
    public static final String KEY_SLASH = "-";
    public static final String KEY_BLANK = "";

    // STATUS
    public static final String STATUS_SUCCESS = "SUCCESS";
    public static final String STATUS_ERROR = "ERROR";

    // CREATE OTP
    public static final String METHOD_CREATE_OTP = "BikerLogInCreateOtp";

    // VERIFY OTP
    public static final String METHOD_VALIDATE_OTP = "OTPAuthentication";

    //TOWN LIST
    public static final String METHOD_GET_STATE = "getState";

    // DISTRIBUTOR, BEAT AND RETAILER LIST
    public static final String METHOD_DISTRIBUTOR_LIST = "GetDistributors";
    public static final String METHOD_BEAT = "GetBeatDetails";
    public static final String METHOD_RETAILER_LIST = "GetRetailerList";
    public static final String METHOD_RETAILER_LIST_BY_NAME = "GetRetailerListByName";

    //SAVE ORDER
    public static final String METHOD_SAVE_ORDER = "SaveNewOrder";

    //New Retailer
    public static final String METHOD_CREATE_RETAILER = "AddRetailer";

    //ACHIEVEMENTS
    public static final String METHOD_ACHIEVEMENTS = "GetBikerMonthTarget";

    //GET CURRENT DATE
    public static final String METHOD_GET_DATE = "GetCurrentDate";

    //UPDATE ORDERS
    public static final String METHOD_GET_ORDER = "GetOrders";
    public static final String METHOD_GET_ORDER_DETAILS = "OrderDetails";
    public static final String METHOD_UPDATE_ORDER = "UpateOrder";

    //GET BIKER NAME
    public static final String METHOD_GET_BIKER_NAME = "GetBikerNameByBikerCode";
}
