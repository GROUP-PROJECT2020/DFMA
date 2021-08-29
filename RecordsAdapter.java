package com.example.dfma_app_656995;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecordsAdapter extends FirebaseRecyclerAdapter <DataBaseHelper,RecordsAdapter.viewHolder>{
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public  RecordsAdapter(@NonNull FirebaseRecyclerOptions options){
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder viewHolder, int i, @NonNull DataBaseHelper dataBaseHelper) {
//        viewHolder.tag.setText(DataBaseHelper.getTag());
//        viewHolder.breed.setText(DataBaseHelper.getBreed());
//        viewHolder.source.setText(DataBaseHelper.getSource());
//        viewHolder.sourceContact.setText(DataBaseHelper.getSourceContact());
//        viewHolder.dob.setText(DataBaseHelper.getDob());
//        viewHolder.mother.setText(DataBaseHelper.getMother());
//        viewHolder.father.setText(DataBaseHelper.getFather());
//        viewHolder.category.setText(DataBaseHelper.getCategory());
        //Glide.with(viewHolder.cow.getContext()).load(DataBaseHelper.getUrl().into(viewHolder.cow));
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cow_rows,parent,false);
        return new viewHolder(view);
    }

    class viewHolder extends  RecyclerView.ViewHolder
    {
        CircleImageView cow;
        TextView tag, breed, source, sourceContact, dob, mother, father, category;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            cow = (CircleImageView)itemView.findViewById(R.id.cow_icon);
            tag = itemView.findViewById(R.id.tag);
            breed = itemView.findViewById(R.id.tag);
            source = itemView.findViewById(R.id.tag);
            sourceContact = itemView.findViewById(R.id.tag);
            dob = itemView.findViewById(R.id.tag);
            mother = itemView.findViewById(R.id.tag);
            father = itemView.findViewById(R.id.tag);
            category = itemView.findViewById(R.id.tag);
        }
    }
}
