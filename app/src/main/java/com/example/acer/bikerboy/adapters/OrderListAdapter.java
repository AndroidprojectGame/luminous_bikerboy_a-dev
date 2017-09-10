package com.example.acer.bikerboy.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.acer.bikerboy.R;
import com.example.acer.bikerboy.UpdateOrderActivity;

import java.util.ArrayList;
import java.util.HashMap;
import static com.example.acer.bikerboy.constants.Constant.FIRST_COLUMN;
import static com.example.acer.bikerboy.constants.Constant.SECOND_COLUMN;
import static com.example.acer.bikerboy.constants.Constant.THIRD_COLUMN;

/**
 * Created by Anusha on 3/14/2016.
 */
public class OrderListAdapter extends BaseAdapter {
    /* Variable Declaration */
    public ArrayList<HashMap<String, String>> list;
    Activity activity;

   // private CouponBean entry;
  //  public com.pocketJini.util.ImageLoader imageLoader;
   public OrderListAdapter(Activity activity, ArrayList<HashMap<String, String>> list) {
       super();
       this.activity = activity;
       this.list = list;
   }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    private class ViewHolder {
        TextView orderId;
        TextView beatId;
        TextView retailerId;
    }

    public View getView(final int position, View convertView,
                        ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        LayoutInflater inflater =  activity.getLayoutInflater();

     //   entry = list.get(position);

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.order_list, null);

            holder = new ViewHolder();

            holder.orderId = (TextView) convertView
                    .findViewById(R.id.tvOrderId);
            holder.beatId = (TextView) convertView
                    .findViewById(R.id.tvBeatId);
            holder.retailerId = (TextView) convertView
                    .findViewById(R.id.tvRetailId);

            convertView.setTag(holder);
            // Set the display text
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HashMap<String, String> map = list.get(position);
        holder.orderId.setText(map.get(FIRST_COLUMN).toString());
        holder.beatId.setText(map.get(SECOND_COLUMN).toString());
        if(map.get(THIRD_COLUMN).toString().equalsIgnoreCase(""))
            holder.retailerId.setVisibility(View.GONE);
        else
            holder.retailerId.setText(map.get(THIRD_COLUMN).toString());
        // display the view corresponding to data at specified position
        return convertView;

    }
}
