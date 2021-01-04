package com.example.demoanu.HelperClasses.HomeAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoanu.R;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {
    ArrayList<CategoriesHelperClass> categoriesHelperClasses;

    public CategoriesAdapter(ArrayList<CategoriesHelperClass> categoriesHelperClasses) {
        this.categoriesHelperClasses = categoriesHelperClasses;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_card_design, parent, false);
        CategoriesAdapter.CategoriesViewHolder categoriesViewHolder = new CategoriesAdapter.CategoriesViewHolder(view);
        return categoriesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {

        CategoriesHelperClass categoriesLocation = categoriesHelperClasses.get(position);
        holder.image.setImageResource(categoriesLocation.getImage());
        holder.title.setText(categoriesLocation.getTitle());
        holder.relativeLayout.setBackground(categoriesLocation.getGradient());
    }

    @Override
    public int getItemCount() {
        return categoriesHelperClasses.size();
    }

    public static class CategoriesViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        RelativeLayout relativeLayout;

        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            //Hooks
            relativeLayout = itemView.findViewById(R.id.background_gradient);
            image = itemView.findViewById(R.id.cv_img);
            title = itemView.findViewById(R.id.cv_title);

        }
    }
}
