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
    private OnClickListener<Ingredient> listener;

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
        if (ingredient.getExpirationDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(ingredient.getExpirationDate(), formatter);
            if (localDate.isBefore(LocalDate.now()))
                holder.name.setTextColor(Color.RED);
        }

        if (ingredient.getQuantity() > 0)
            holder.quantity.setText(new StringBuilder().append((ingredient.getQuantity()))
                    .append(" ")
                    .append(ingredient.getMeasurement().toString()));
        else
            holder.quantity.setText("");
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void setOnClickListener(OnClickListener<Ingredient> listener) {
        this.listener = listener;
    }

    public void removeOnClickListener() {
        listener = null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView quantity;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.view_recipe_ingredient_name);
            quantity = itemView.findViewById(R.id.view_recipe_ingredient_quantity);

            itemView.setOnClickListener(v -> {
                if (listener != null)
                    listener.onClick(ingredients.get(getBindingAdapterPosition()));
            });
        }
    }
}
