package com.example.mery.maed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mery on 12/20/2018.
 */

public class Customer_Adapter extends ArrayAdapter<product_list> {
    int groupid;
    ArrayList<product_list> records;
    Context context;
    public Customer_Adapter(Context context,int vg ,int id, ArrayList<product_list> records)
    {
        super(context,vg,id,records);
        this.context=context;
        groupid=vg;
        this.records=records;
    }
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView=inflater.inflate(groupid,parent,false);
        TextView id=(TextView) itemView.findViewById(R.id.id);
        id.setText(records.get(position).getId());
        TextView textName=(TextView)itemView.findViewById(R.id.item_name);
        textName.setText(records.get(position).getItemName());
        TextView textPrice=(TextView)itemView.findViewById(R.id.item_price);
        textPrice.setText(records.get(position).getItemPrice());
        TextView mId=(TextView)itemView.findViewById(R.id.type);
        mId.setText(records.get(position).getType());
        return itemView;
    }

}