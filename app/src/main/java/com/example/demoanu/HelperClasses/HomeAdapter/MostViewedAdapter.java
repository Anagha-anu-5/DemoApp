package com.example.demoanu.HelperClasses.HomeAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoanu.R;

import java.util.ArrayList;

public class MostViewedAdapter extends RecyclerView.Adapter<MostViewedAdapter.MostViewHolder> {
    ArrayList<MostViewedHelperClass> mostViewedHelperClass;

    public MostViewedAdapter(ArrayList<MostViewedHelperClass> mostViewedHelperClass) {
        this.mostViewedHelperClass = mostViewedHelperClass;
    }

    @NonNull
    @Override
    public MostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.most_card_viewed_design,parent,false);
        MostViewedAdapter.MostViewHolder mostViewHolder = new MostViewedAdapter.MostViewHolder(view);
        return mostViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MostViewHolder holder, int position) {

        MostViewedHelperClass mostViewedLocation = mostViewedHelperClass.get(position);
        holder.image.setImageResource(mostViewedLocation.getImage());
        holder.title.setText(mostViewedLocation.getTitle());
        holder.desc.setText(mostViewedLocation.getDescription());
    }

    @Override
    public int getItemCount() {
        return mostViewedHelperClass.size();
    }

    public static class MostViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        TextView title,desc;
        public MostViewHolder(@NonNull View itemView) {
            super(itemView);
            //Hooks
            image = itemView.findViewById(R.id.mv_image);
            title = itemView.findViewById(R.id.mv_title);
            desc = itemView.findViewById(R.id.mv_desc);

        }
    }

}
