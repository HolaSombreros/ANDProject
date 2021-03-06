package com.group2.foodie.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.group2.foodie.R;
import com.group2.foodie.model.Ingredient;

import java.util.List;

public class EditableIngredientsAdapter extends RecyclerView.Adapter<EditableIngredientsAdapter.ViewHolder> {
    private List<Ingredient> ingredients;
    private OnClickListener<Ingredient> listener;

    public EditableIngredientsAdapter(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    public void setOnRemoveListener(OnClickListener<Ingredient> listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.editable_recipe_ingredient_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);

        holder.name.setText(ingredient.getName());

        if (ingredient.getQuantity() > 0) {
            holder.quantity.setText(new StringBuilder().append(ingredient.getQuantity())
                    .append(" ")
                    .append(ingredient.getMeasurement().toString()));
        } else {
            holder.quantity.setText("");
        }

    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView quantity;
        private Button removeBtn;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.recipe_ingredient_name);
            quantity = itemView.findViewById(R.id.recipe_ingredient_quantity);
            removeBtn = itemView.findViewById(R.id.recipe_ingredient_removeBtn);

            removeBtn.setOnClickListener(v -> {
                listener.onClick(ingredients.get(getBindingAdapterPosition()));
            });
        }
    }


}
