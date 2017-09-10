package com.example.acer.bikerboy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.bikerboy.adapters.OrderListAdapter;
import com.example.acer.bikerboy.controllers.UserController;
import com.example.acer.bikerboy.models.Order;
import com.example.acer.bikerboy.models.OrderDetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.acer.bikerboy.constants.Constant.FIRST_COLUMN;
import static com.example.acer.bikerboy.constants.Constant.SECOND_COLUMN;
import static com.example.acer.bikerboy.constants.Constant.THIRD_COLUMN;

/**
 * Created by Anusha on 2/25/2016.
 */
public class UpdateOrderActivity extends AppCompatActivity {

    private TextView tvName, tvorderval, textView3, tvdistval, tvbeatval, tvretailval, tvstatusval, tvFanReq, tvWireReq, tvLightReq;
    EditText etFanPen, etWirePen, etLightPen, etFanSup, etWireSup, etLightSup;
    Button button3;
    String status;
    LinearLayout linearLayout2;
    private OrderDetail mOrderDetailsList;
    RadioGroup radioGroupStatus;
    String order_status;
    boolean isSerialNumberValid;
    boolean checked;
    int fansup, wiresup,lightsup;
    Toast t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_order);
        //SharedPreferences preferences = getSharedPreferences("SupQty", MODE_PRIVATE);
        //preferences.edit().clear().commit();
        t1 = new Toast(getApplicationContext());
        prepareToolbar();
        //prepareView();
    }

    private void getOrderDetail(String orderid) {
        UserController userController = new UserController(this, "getorderdetail");
        userController.getOrderDetails(orderid);
    }

    public void getorderdetail(Object object) {

        mOrderDetailsList = ((UserController) object).mOrderDetailsList;
        if (mOrderDetailsList != null) {
          //  for (OrderDetail orderdetail: mOrderDetailsList){
            fansup = Integer.parseInt(mOrderDetailsList.Required_FanQuantity.toString()) - Integer.parseInt(mOrderDetailsList.Pending_FanQuantity.toString());
            wiresup = Integer.parseInt(mOrderDetailsList.Required_WireQuantity.toString()) - Integer.parseInt(mOrderDetailsList.Pending_WireQuantity.toString());
            lightsup = Integer.parseInt(mOrderDetailsList.Required_LightQuantity.toString()) - Integer.parseInt(mOrderDetailsList.Pending_LightQuantity.toString());
          //  SharedPreferences.Editor editor = getSharedPreferences("SupQty", MODE_PRIVATE).edit();
          //  editor.putInt("fansup", fansup);
          //  editor.putInt("wiresup", wiresup);
          //  editor.putInt("lightsup",lightsup);
          //  editor.commit();
            tvstatusval.setText(mOrderDetailsList.Status);
                 tvretailval.setText(mOrderDetailsList.Retailer_Name);
                 tvorderval.setText(mOrderDetailsList.Order_ID);
                 tvdistval.setText(mOrderDetailsList.Dist_Name);
                 tvbeatval.setText(mOrderDetailsList.Beat_Name);
                tvFanReq.setText(mOrderDetailsList.Required_FanQuantity);
                etFanPen.setText(mOrderDetailsList.Pending_FanQuantity);
                tvWireReq.setText(mOrderDetailsList.Required_WireQuantity);
                etWirePen.setText(mOrderDetailsList.Pending_WireQuantity);
                tvLightReq.setText(mOrderDetailsList.Required_LightQuantity);
                etLightPen.setText(mOrderDetailsList.Pending_LightQuantity);
            if(mOrderDetailsList.Status.equalsIgnoreCase("Completed")){
                etFanSup.setText("" + fansup);
                etWireSup.setText("" + wiresup);
                etLightSup.setText("" + lightsup);
                etFanPen.setVisibility(View.GONE);
                etWirePen.setVisibility(View.GONE);
                etLightPen.setVisibility(View.GONE);
                button3.setVisibility(View.GONE);
                textView3.setVisibility(View.GONE);
                linearLayout2.setVisibility(View.GONE);
                etFanSup.setEnabled(false);
                etWireSup.setEnabled(false);
                etLightSup.setEnabled(false);
            }
        }
    }

    private void prepareToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvName = (TextView) toolbar.findViewById(R.id.tv_profile);
        textView3 = (TextView)findViewById(R.id.textView3);
        tvFanReq = (TextView)findViewById(R.id.tvFanReq);
        tvWireReq = (TextView)findViewById(R.id.tvWireReq);
        tvLightReq = (TextView)findViewById(R.id.tvLightReq);
        etFanPen = (EditText)findViewById(R.id.etFanPen);
        etWirePen = (EditText)findViewById(R.id.etWirePen);
        etLightPen = (EditText)findViewById(R.id.etLightPen);
        etFanSup = (EditText)findViewById(R.id.etFanSup);
        etWireSup = (EditText)findViewById(R.id.etWireSup);
        etLightSup = (EditText)findViewById(R.id.etLightSup);
        button3 = (Button)findViewById(R.id.button3);
        linearLayout2 = (LinearLayout)findViewById(R.id.linearLayout2);
        radioGroupStatus = (RadioGroup) findViewById(R.id.radioGroupStatus);
        radioGroupStatus.clearCheck();
        tvbeatval=(TextView)findViewById(R.id.tvbeatVal);
        tvdistval=(TextView)findViewById(R.id.tvdistVal);
        tvorderval=(TextView)findViewById(R.id.tvorderVal);
        tvretailval=(TextView)findViewById(R.id.tvRetailerVal);
        tvstatusval=(TextView)findViewById(R.id.tvstatusVal);

        Intent in = getIntent();
        String order = in.getStringExtra("OrderId");
        getOrderDetail(order);
      /*  etFanSup.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    int fanpen = Integer.parseInt(etFanPen.getText().toString()) - Integer.parseInt(etFanSup.getText().toString());
                    etFanPen.setText("" + fanpen);
                }
            }
        });

        etWireSup.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    int wirepen = Integer.parseInt(etWirePen.getText().toString()) - Integer.parseInt(etWireSup.getText().toString());
                    etWirePen.setText("" + wirepen);
                }
            }
        });

        etLightSup.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    int lightpen = Integer.parseInt(etLightPen.getText().toString()) - Integer.parseInt(etLightSup.getText().toString());
                    etLightPen.setText("" + lightpen);
                }
            }
        });  */
       /* etFanSup.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void afterTextChanged(final Editable s) {
                int fanpen = Integer.parseInt(etFanPen.getText().toString()) - Integer.parseInt(etFanSup.getText().toString());
                etFanPen.setText(String.valueOf(fanpen));
            }
        });

        etWireSup.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void onTextChanged(final CharSequence s, int start, int before,
                                      int count) {
            }
            @Override
            public void afterTextChanged(final Editable s) {
                int wirepen = Integer.parseInt(etWirePen.getText().toString()) - Integer.parseInt(etWireSup.getText().toString());
                etWirePen.setText(String.valueOf(wirepen));
            }
        });

        etLightSup.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void onTextChanged(final CharSequence s, int start, int before,
                                      int count) {
            }
            @Override
            public void afterTextChanged(final Editable s) {
                int lightpen = Integer.parseInt(etLightPen.getText().toString()) - Integer.parseInt(etLightSup.getText().toString());
                etLightPen.setText(String.valueOf(lightpen));
            }
        });  */

        radioGroupStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                RadioButton rbcompleted = (RadioButton) group.findViewById(R.id.completedYes);
                RadioButton rbnotcompleted = (RadioButton) group.findViewById(R.id.completedNo);
                if (null != rb && checkedId > -1) {
                    checked = true;
                    // Toast.makeText(NewOrderActivity.this, rb.getText(), Toast.LENGTH_SHORT).show();
                    if (rbnotcompleted.isChecked()) {
                        order_status = "Partially Completed";
                    }
                    if (rbcompleted.isChecked()) {
                        order_status = "Completed";
                    }
                }
                else
                    checked = false;

            }
        });
    }

    private void updateOrder(String orderId, String supFanQty, String supWireQty, String supLightQty, String order_status) {
        UserController userController = new UserController(this, "updateorder");
        userController.updateOrder(orderId, supFanQty, supWireQty, supLightQty, order_status);
    }

    public void updateorder(Object object) {
        isSerialNumberValid = ((UserController) object).isSerialValid;
        if (!isSerialNumberValid) {
            t1.cancel();
            Toast.makeText(this, "" + ((UserController) object).mMessage, Toast.LENGTH_SHORT).show();
            //Global.showToast("" + ((UserController) object).mMessage);
        }
        else {
            //Global.showToast("" + ((UserController) object).mMessage);
            t1.cancel();
            Toast.makeText(this, "" + ((UserController) object).mMessage, Toast.LENGTH_SHORT).show();
            Intent in = new Intent(getApplicationContext(),SelectOrderActivity.class);
            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(in);
        }
    }

    public void submit(View v)
    {
        if(etFanSup.getText().toString().equals("")){
            t1.cancel();
            t1 = Toast.makeText(getApplicationContext(),"Please enter supplied Fan Quantity",Toast.LENGTH_SHORT);
            t1.show();
        }
        else if(etWireSup.getText().toString().equals("")){
            t1.cancel();
            t1 = Toast.makeText(getApplicationContext(),"Please enter supplied Wire Value",Toast.LENGTH_SHORT);
            t1.show();
        }
        else if(etLightSup.getText().toString().equals("")){
            t1.cancel();
            t1 = Toast.makeText(getApplicationContext(),"Please enter supplied Lighting Value",Toast.LENGTH_SHORT);
            t1.show();
        }
        else if(checked == false){
            t1.cancel();
            t1 = Toast.makeText(getApplicationContext(),"Please select a Status",Toast.LENGTH_SHORT);
            t1.show();
        }
        else {
            if(Integer.parseInt(etFanSup.getText().toString()) > Integer.parseInt(etFanPen.getText().toString())){
                t1.cancel();
                t1 = Toast.makeText(getApplicationContext(),"Supplied Quantity should be less than or equal to the Pending Quantity",Toast.LENGTH_SHORT);
                t1.show();
                etFanSup.setText("");
            }
            else if(Integer.parseInt(etWireSup.getText().toString()) > Integer.parseInt(etWirePen.getText().toString())){
                t1.cancel();
                t1 = Toast.makeText(getApplicationContext(),"Supplied Quantity should be less than or equal to the Pending Quantity",Toast.LENGTH_SHORT);
                t1.show();
                etWireSup.setText("");
            }
            else if(Integer.parseInt(etLightSup.getText().toString()) > Integer.parseInt(etLightPen.getText().toString())){
                t1.cancel();
                t1 = Toast.makeText(getApplicationContext(),"Supplied Quantity should be less than or equal to the Pending Quantity",Toast.LENGTH_SHORT);
                t1.show();
                etLightSup.setText("");
            }
            else {
                updateOrder(tvorderval.getText().toString(), etFanSup.getText().toString(), etWireSup.getText().toString(), etLightSup.getText().toString(), order_status);

            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
