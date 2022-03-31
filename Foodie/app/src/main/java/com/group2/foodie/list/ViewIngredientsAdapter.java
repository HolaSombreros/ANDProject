package com.group2.foodie.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.group2.foodie.R;
import com.group2.foodie.model.Ingredient;

import java.util.List;

public class ViewIngredientsAdapter extends RecyclerView.Adapter<ViewIngredientsAdapter.ViewHolder> {
    private List<Ingredient> ingredients;

    public ViewIngredientsAdapter(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setRecipe(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    @Override
    public ViewIngredientsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.editable_recipe_ingredient_item, parent, false);
        return new ViewIngredientsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewIngredientsAdapter.ViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        holder.name.setText(ingredient.getName());
        holder.quantity.setText(String.valueOf(ingredient.getQuantity()));
        holder.measurement.setText(ingredient.getMeasurement().toString());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView quantity;
        private TextView measurement;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.view_recipe_ingredient_name);
            quantity = itemView.findViewById(R.id.view_recipe_ingredient_quantity);
            measurement = itemView.findViewById(R.id.view_recipe_ingredient_measurement);
        }
    }
}
