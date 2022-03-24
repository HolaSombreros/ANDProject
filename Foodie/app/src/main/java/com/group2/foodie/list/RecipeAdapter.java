package com.group2.foodie.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group2.foodie.R;
import com.group2.foodie.model.Recipe;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>{

    private ArrayList<Recipe> recipes;
    private OnClickListener listener;

    public RecipeAdapter(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.personal_recipe_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder viewHolder, int position) {
        viewHolder.image.setImageResource(recipes.get(position).getImageId());
        viewHolder.recipeName.setText(recipes.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView recipeName;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.personal_recipe_item_picture);
            recipeName = itemView.findViewById(R.id.personal_recipe_item_name);

            itemView.setOnClickListener(v-> {
                listener.onClick(recipes.get(getBindingAdapterPosition()));
            });
        }
    }
}
