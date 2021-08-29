package com.example.dfma_app_656995;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class CowAdapter extends BaseAdapter {

    ArrayList<DataBaseHelper> cowHelper=new ArrayList<DataBaseHelper>();
    Context context;
    DatabaseReference ref;

    public CowAdapter(Context context, int simple_list_item_1, ArrayList<DataBaseHelper> cowHelp){
        cowHelper= cowHelp;
        this.context=context;
    }

    @Override
    public int getCount() {
        return cowHelper.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.view_cow_rows,parent,false);

        TextView tag = (TextView) view.findViewById(R.id.tag);
        TextView breed = (TextView) view.findViewById(R.id.breed);
        TextView source = (TextView) view.findViewById(R.id.source);
        //TextView sourceContact = (TextView) view.findViewById(R.id.source_contact);
        TextView dob = (TextView) view.findViewById(R.id.dob);
        TextView mother = (TextView) view.findViewById(R.id.mother);
        TextView father = (TextView) view.findViewById(R.id.father);
        TextView category = (TextView) view.findViewById(R.id.category);

        DataBaseHelper cowHelper1=cowHelper.get(position);

        tag.setText(cowHelper1.getTag());
        breed.setText(cowHelper1.getBreed());
        source.setText(cowHelper1.getSource());
        //sourceContact.setText(helper1.getSourceContact());
        dob.setText(cowHelper1.getDob());
        mother.setText(cowHelper1.getMother());
        father.setText(cowHelper1.getFather());
        category.setText(cowHelper1.getCategory());

        return view;
    }
}
