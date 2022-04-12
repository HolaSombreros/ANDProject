package com.group2.foodie.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.group2.foodie.R;
import com.group2.foodie.model.Recipe;
import com.group2.foodie.util.GlideApp;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>{
    private List<Recipe> recipes;
    private OnClickListener<Recipe> listener;

    public RecipeAdapter() {
        recipes = new ArrayList<>();
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
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
        String id = recipes.get(position).getId();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/" + id + ".jpg");
        GlideApp.with(viewHolder.itemView).load(storageRef).into(viewHolder.image);
        viewHolder.recipeName.setText(recipes.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setOnClickListener(OnClickListener<Recipe> listener) {
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
