package com.group2.foodie.dailyrecipe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group2.foodie.R;

import java.util.List;

public class ExtendedIngredientsAdapter extends RecyclerView.Adapter<ExtendedIngredientsAdapter.ViewHolder>{

    private List<ExtendedIngredient> ingredientList;

    public ExtendedIngredientsAdapter(List<ExtendedIngredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.daily_recipe_ingredient_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ingredientInfo.setText(new StringBuilder().append(ingredientList.get(position).getOriginalName())
                .append(" ").append(ingredientList.get(position).getAmount())
                .append(" ").append(ingredientList.get(position).getUnit()).toString());
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView ingredientInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            ingredientInfo = itemView.findViewById(R.id.daily_ingredients);
        }
    }
}
