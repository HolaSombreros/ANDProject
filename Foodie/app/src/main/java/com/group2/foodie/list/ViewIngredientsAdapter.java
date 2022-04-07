package com.group2.foodie.list;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.group2.foodie.R;
import com.group2.foodie.model.Ingredient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ViewIngredientsAdapter extends RecyclerView.Adapter<ViewIngredientsAdapter.ViewHolder> {
    private List<Ingredient> ingredients;
    private OnClickListenerIngredient listener;

    public ViewIngredientsAdapter() {
        this.ingredients = new ArrayList<>();
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    @Override
    public ViewIngredientsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_recipe_ingredient_item, parent, false);
        return new ViewIngredientsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewIngredientsAdapter.ViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        holder.name.setText(ingredient.getName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(ingredient.getExpirationDate(),formatter);
        if (localDate != null && localDate.isBefore(LocalDate.now()))
            holder.name.setTextColor(Color.RED);
        holder.quantity.setText(String.valueOf(ingredient.getQuantity()));
        holder.measurement.setText(ingredient.getMeasurement().toString());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void setOnClickListener(OnClickListenerIngredient listener) {
        this.listener = listener;
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
