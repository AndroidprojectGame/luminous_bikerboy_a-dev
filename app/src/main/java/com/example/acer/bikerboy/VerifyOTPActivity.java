package com.example.acer.bikerboy;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.acer.bikerboy.constants.UrlConstants;
import com.example.acer.bikerboy.controllers.UserController;
import com.example.acer.bikerboy.utils.AppSharedPrefrences;
import com.example.acer.bikerboy.utils.Navigator;

public class VerifyOTPActivity extends AppCompatActivity {

    private String otpPin;
    private EditText etCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        prepareViews();
    }

    private void prepareViews() {
        etCode = (EditText) findViewById(R.id.activity_verify_et_id);
    }

    public void attemptLogin(View v)
    {
        otpPin = etCode.getText().toString();
        if (otpPin.trim().length() > 0) {
            etCode.setError(null);
            getDataAndValidateOtp();
        } else {
            etCode.setError("Enter Code");
        }
        //Intent in = new Intent(this, HomePageActivity.class);
       // startActivity(in);
    }

    private void getDataAndValidateOtp() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String Imei = telephonyManager.getDeviceId();
        String deviceVersion = android.os.Build.VERSION.RELEASE;
        String deviceId = Build.DEVICE;
        UserController userController = new UserController(this,
                "updateOtpStatus");
        userController
                .validateOtp(AppSharedPrefrences.getInstance(this).getUserId(), Imei, deviceVersion, "Android", otpPin, deviceId);
    }


    public void updateOtpStatus(Object object) {
        UserController userController = (UserController) object;
        if (userController.mCode.equalsIgnoreCase(UrlConstants.STATUS_SUCCESS)) {

            Toast.makeText(this, userController.mMessage, Toast.LENGTH_LONG).show();

            AppSharedPrefrences.getInstance(this).setOtp(otpPin);
            Navigator.getInstance().navigateToActivity(this, HomePageActivity.class);
            finish();

        } else {
            Toast.makeText(this, userController.mMessage, Toast.LENGTH_LONG).show();
        }
    }

}
