package com.group2.foodie.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group2.foodie.R;
import com.group2.foodie.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    private List<Ingredient> ingredients;

    public IngredientsAdapter() {
        ingredients = new ArrayList<>();

        addNewListItem();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_ingredient_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.name.;
//        holder.quantity.setText(String.valueOf(ingredients.get(position).getQuantity()));
//        holder.measurement.
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void addNewListItem() {
        ingredients.add(new Ingredient());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Spinner name;
        private EditText quantity;
        private Spinner measurement;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.recipe_ingredient_name);
            quantity = itemView.findViewById(R.id.recipe_ingredient_quantity);
            measurement = itemView.findViewById(R.id.recipe_ingredient_measurement);

            String[] ingredients = {"Burger", "Pizza"};
            String[] measurements = {"g", "l", "tsp"};

            ArrayAdapter nameAdapter = new ArrayAdapter(itemView.getContext(), android.R.layout.simple_spinner_item, ingredients);
            nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            name.setAdapter(nameAdapter);

            ArrayAdapter measurementAdapter = new ArrayAdapter(itemView.getContext(), android.R.layout.simple_spinner_item, measurements);
            measurementAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            measurement.setAdapter(measurementAdapter);
        }
    }
}
