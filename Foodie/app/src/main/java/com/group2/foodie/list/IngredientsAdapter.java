package com.group2.foodie.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.recyclerview.widget.RecyclerView;

import com.group2.foodie.R;
import com.group2.foodie.model.Ingredient;
import com.group2.foodie.model.Measurement;
import com.group2.foodie.repositorx.Repository;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    private List<Ingredient> ingredients;
    private onClickListener onClickListener;

    public IngredientsAdapter(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setOnClickListener(onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_ingredient_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.name.setSelection(, false);
//        holder.quantity.setText(String.valueOf(ingredients.get(position).getQuantity()));
//        holder.measurement.setSelection(holder.measurement.getSelectedItemPosition());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void setList(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Spinner name;
        private EditText quantity;
        private Spinner measurement;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.recipe_ingredient_name);
            quantity = itemView.findViewById(R.id.recipe_ingredient_quantity);
            measurement = itemView.findViewById(R.id.recipe_ingredient_measurement);

            // TODO - FETCH FROM DATABASE
            String[] dummyIngredients = Repository.getInstance().getDummyIngredients();

            ArrayAdapter<String> nameAdapter = new ArrayAdapter<>(itemView.getContext(), android.R.layout.simple_spinner_item, dummyIngredients);
            nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            name.setAdapter(nameAdapter);

            ArrayAdapter<Measurement> measurementAdapter = new ArrayAdapter<>(itemView.getContext(), android.R.layout.simple_spinner_item, Measurement.values());
            measurementAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            measurement.setAdapter(measurementAdapter);

            measurement.setSelection(0, false);
            measurement.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
                    onClickListener.onClick(ingredients.get(getBindingAdapterPosition()));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }
    }

    public interface onClickListener {
        void onClick(Ingredient ingredient);
    }
}
