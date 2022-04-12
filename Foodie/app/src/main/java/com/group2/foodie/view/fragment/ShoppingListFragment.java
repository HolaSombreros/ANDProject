package com.group2.foodie.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.group2.foodie.R;
import com.group2.foodie.list.ShoppingListAdapter;
import com.group2.foodie.viewmodel.ShoppingListViewModel;

public class ShoppingListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ShoppingListViewModel viewModel;
    private ShoppingListAdapter shoppingListAdapter;
    private FloatingActionButton fab;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_shopping_list, container, false);
    }

    private void initViews(View view) {
        navController = Navigation.findNavController(view);
        recyclerView = view.findViewById(R.id.shoppingList_recycler);
        fab = view.findViewById(R.id.shoppingList_fab);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(ShoppingListViewModel.class);
        viewModel.init();
        initViews(view);
        fab.setOnClickListener(v -> {
            navController.navigate(R.id.addShoppingListIngredient);
        });
        shoppingListAdapter = new ShoppingListAdapter();
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewModel.getShoppingListIngredients().observe(getViewLifecycleOwner(),ingredients -> {
            shoppingListAdapter.setIngredients(ingredients);
        });
        recyclerView.setAdapter(shoppingListAdapter);
        shoppingListAdapter.setOnClickListener(ingredient -> {
            viewModel.removeIngredient(ingredient);
            navController.navigate(R.id.fragment_shopping_list);
        });
    }
}
