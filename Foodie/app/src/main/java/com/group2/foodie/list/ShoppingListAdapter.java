package com.group2.foodie.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group2.foodie.R;
import com.group2.foodie.model.Ingredient;
import java.util.ArrayList;
import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {
    private List<Ingredient> list;
    private OnClickListener<Ingredient> listener;
    private OnBoughtListener onBoughtListener;

    public ShoppingListAdapter() {
        this.list = new ArrayList<>();
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.list = ingredients;
        notifyDataSetChanged();
    }

    public void setOnBoughtListener(ShoppingListAdapter.OnBoughtListener onBoughtListener) {
        this.onBoughtListener = onBoughtListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.shopping_list_item, parent, false);
        return new ShoppingListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ingredientName.setText(list.get(position).getName());
        holder.quantity.setText(new StringBuilder().append(list.get(position).getQuantity()).append(" ")
                .append(list.get(position).getMeasurement()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnClickListener(OnClickListener<Ingredient> listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView ingredientName;
        private TextView quantity;
        private Button addToFridge;

        public ViewHolder(View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.shoppingList_item_name);
            quantity = itemView.findViewById(R.id.shoppingList_item_quantity);
            addToFridge = itemView.findViewById(R.id.shopping_add_virtual_fridge_button);

            itemView.setOnLongClickListener(v -> {
                listener.onClick(list.get(getBindingAdapterPosition()));
                return true;
            });

            addToFridge.setOnClickListener(v -> onBoughtListener.onBought(list.get(getBindingAdapterPosition())));
        }
    }

    public interface OnBoughtListener {
        void onBought(Ingredient ingredient);
    }
}
