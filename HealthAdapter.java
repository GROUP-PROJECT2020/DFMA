package com.example.dfma_app_656995;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HealthAdapter extends BaseAdapter {
    ArrayList<HealthHelper> healthHelper=new ArrayList<>();
    Context context;

    public HealthAdapter(Context context, ArrayList<HealthHelper> healthHelp){
        healthHelper=healthHelp;
        this.context=context;
    }

    public int getCount() {
        return healthHelper.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.view_health_rows,parent,false);

        TextView tag = (TextView) view.findViewById(R.id.tag);
        TextView condition = (TextView) view.findViewById(R.id.condition);
        TextView doctorName = (TextView) view.findViewById(R.id.doc_name);
        TextView doctorContact = (TextView) view.findViewById(R.id.doc_contact);
        TextView farmerContact = (TextView) view.findViewById(R.id.farmer_contact);
        HealthHelper healthHelper1= healthHelper.get(position);

        tag.setText(healthHelper1.getTag());
        condition.setText(healthHelper1.getCondition());
        doctorName.setText(healthHelper1.getDoctorName());
        doctorContact.setText(healthHelper1.getDoctorContact());
        farmerContact.setText(healthHelper1.getFarmerContact());
        return view;
    }
}
