package com.example.dfma_app_656995;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MilkAdapter extends BaseAdapter {
    ArrayList<MilkHelper> milkHelper=new ArrayList<>();
    Context context;

    public MilkAdapter(Context context, ArrayList<MilkHelper> help){
        milkHelper=help;
        this.context=context;
    }

    public int getCount() {
        return milkHelper.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.view_milk_rows,parent,false);

        TextView tag = (TextView) view.findViewById(R.id.tag);
        TextView farmer = (TextView) view.findViewById(R.id.farmer_name);
        TextView morning = (TextView) view.findViewById(R.id.morning);
        TextView midDay = (TextView) view.findViewById(R.id.mid_morning);
        TextView evening = (TextView) view.findViewById(R.id.evening);
        TextView domestic = (TextView) view.findViewById(R.id.domestic);
        TextView sale = (TextView) view.findViewById(R.id.sale);
        TextView buyer = (TextView) view.findViewById(R.id.buyer);
        TextView date = (TextView) view.findViewById(R.id.date_today);
        MilkHelper milkHelper1=milkHelper.get(position);

        tag.setText(milkHelper1.getTag());
        farmer.setText(milkHelper1.getFarmerName());
        morning.setText(milkHelper1.getMorning());
        midDay.setText(milkHelper1.getMidDay());
        evening.setText(milkHelper1.getEvening());
        domestic.setText(milkHelper1.getDomestic());
        sale.setText(milkHelper1.getSale());
        buyer.setText(milkHelper1.getBuyer());
        date.setText(milkHelper1.getDateToday());
        return view;
    }
}
