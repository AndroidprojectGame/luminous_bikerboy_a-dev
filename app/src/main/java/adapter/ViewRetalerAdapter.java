package adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.acer.bikerboy.R;
import com.example.acer.bikerboy.RetailerDetailsActivity;
import com.example.acer.bikerboy.ViewRetailerActivity;
import com.example.acer.bikerboy.models.ViewRetalerModel;

import java.util.ArrayList;

/**
 * Created by techfour on 8/9/17.
 */

public class ViewRetalerAdapter extends BaseAdapter
{
    ArrayList<ViewRetalerModel> arrayList;
    Activity activity;

    public ViewRetalerAdapter(Activity activity,ArrayList<ViewRetalerModel> arrayList)
    {
        this.activity=activity;
        this.arrayList=arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=activity.getLayoutInflater();
        final ViewRetalerModel viewRetalerModel=arrayList.get(position);
        View view=inflater.inflate(R.layout.view_retaler_model,parent,false);
        TextView tv_name=(TextView)view.findViewById(R.id.tv_name);
        TextView tv_contact_person=(TextView)view.findViewById(R.id.tv_contact_persion);
        TextView tv_mobile=(TextView)view.findViewById(R.id.tv_mobile);
        TextView tv_address=(TextView)view.findViewById(R.id.tv_address);
        LinearLayout main_layout=(LinearLayout)view.findViewById(R.id.main_layout);
        tv_name.setText(""+viewRetalerModel.getName());
        tv_contact_person.setText(""+viewRetalerModel.getContact_persion());
        tv_mobile.setText(""+viewRetalerModel.getMobile());
        tv_address.setText(""+viewRetalerModel.getAddress()+","+viewRetalerModel.getCity_area()+","+viewRetalerModel.getState());
        main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, RetailerDetailsActivity.class);
                intent.putExtra("name",viewRetalerModel.getName());
                intent.putExtra("contactperson",viewRetalerModel.getContact_persion());
                intent.putExtra("mobile",viewRetalerModel.getMobile());
                intent.putExtra("addres",viewRetalerModel.getAddress());
                activity.startActivity(intent);
            }
        });
        return view;
    }
}
