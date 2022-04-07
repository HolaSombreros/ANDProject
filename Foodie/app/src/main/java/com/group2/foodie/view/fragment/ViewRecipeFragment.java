package com.group2.foodie.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.group2.foodie.R;
import com.group2.foodie.list.ViewIngredientsAdapter;
import com.group2.foodie.viewmodel.ViewRecipeViewModel;

public class ViewRecipeFragment extends Fragment {
    private ViewRecipeViewModel viewModel;
    private ViewIngredientsAdapter ingredientsAdapter;
    private NavController navController;
    private TextView title;
    private TextView publisher;
    private TextView category;
    private ImageView favoriteImage;
    private ImageView foodImage;
    private RecyclerView ingredients;
    private TextView instructions;
    private Button editButton;
    private Button removeButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_recipe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(ViewRecipeViewModel.class);
        viewModel.init(getArguments().getString("recipe"));
        initializeViews(view);
        setupViews();
    }

    private void initializeViews(View view) {
        navController = Navigation.findNavController(view);
        ingredients = view.findViewById(R.id.viewRecipe_recyclerView);
        title = view.findViewById(R.id.viewRecipe_title);
        category = view.findViewById(R.id.viewRecipe_category);
        publisher = view.findViewById(R.id.viewRecipe_publisher);
        favoriteImage = view.findViewById(R.id.viewRecipe_favoriteButton);
        foodImage = view.findViewById(R.id.viewRecipe_imageView);
        instructions = view.findViewById(R.id.viewRecipe_instructions);
        editButton = view.findViewById(R.id.viewRecipe_editButton);
        removeButton = view.findViewById(R.id.viewRecipe_removeButton);
    }

    private void setupViews() {
        ingredients.hasFixedSize();
        ingredients.setLayoutManager(new LinearLayoutManager(getActivity()));
        ingredientsAdapter = new ViewIngredientsAdapter();
        ingredients.setAdapter(ingredientsAdapter);

        viewModel.getRecipe().observe(getViewLifecycleOwner(), recipe -> {
            title.setText(recipe.getName());
            category.setText(recipe.getCategory());
            publisher.setText(recipe.getPublisherUsername());
//            foodImage.set
            instructions.setText(recipe.getInstructions());
            ingredientsAdapter.setRecipe(recipe.getIngredients());
        });

       // publisher.setText(recipe.getPublisher().getUsername());
        //if (user.getFavoriteRecipes().contains(recipe))
//            foodImage.setImageResource(R.drawable.ic_full_heart);
//        //else
//          //  foodImage.setImageResource(R.drawable.ic_empty_heart);
//        // TODO
//        if (ContextCompat.getDrawable(getActivity(), recipe.getImageId()) != null)
//            foodImage.setImageResource(recipe.getImageId());

        editButton.setOnClickListener(r -> {
            // navigate
        });

        removeButton.setOnClickListener(r -> {
            viewModel.removeRecipe();
            navController.navigate(R.id.fragment_personal_recipes);
        });
    }
}
