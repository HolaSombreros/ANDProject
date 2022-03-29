package com.group2.foodie.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.group2.foodie.R;
import com.group2.foodie.model.Ingredient;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    private List<Ingredient> ingredients;
    private OnRemoveListener listener;

    public IngredientsAdapter(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    public void setOnRemoveListener(OnRemoveListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_ingredient_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);

        holder.name.setText(ingredient.getName());

        if (ingredient.getQuantity() > 0) {
            holder.quantity.setText(String.valueOf(ingredient.getQuantity()));
        } else {
            holder.quantity.setText("");
        }

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
        private Button removeBtn;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.recipe_ingredient_name);
            quantity = itemView.findViewById(R.id.recipe_ingredient_quantity);
            measurement = itemView.findViewById(R.id.recipe_ingredient_measurement);
            removeBtn = itemView.findViewById(R.id.recipe_ingredient_removeBtn);

            removeBtn.setOnClickListener(v -> {
                listener.onRemove(ingredients.get(getBindingAdapterPosition()));
            });
        }
    }

    public interface OnRemoveListener {
        void onRemove(Ingredient ingredient);
    }
}
