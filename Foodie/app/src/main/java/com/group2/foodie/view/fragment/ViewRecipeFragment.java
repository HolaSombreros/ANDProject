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

import com.group2.foodie.R;
import com.group2.foodie.list.ViewIngredientsAdapter;
import com.group2.foodie.model.Recipe;
import com.group2.foodie.model.User;
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
        initializeViews(view);
        setupViews();
    }

    private void initializeViews(View view) {
        navController = Navigation.findNavController(view);
        ingredients = view.findViewById(R.id.personalRecipes_recycleView);
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
        Bundle bundle = getActivity().getIntent().getExtras();
        Recipe recipe = viewModel.getRecipe(bundle.getString("recipe")).getValue();
        User user = viewModel.getCurrentUser().getValue();
        ingredients.hasFixedSize();
        ingredients.setLayoutManager(new LinearLayoutManager(getActivity()));
        ingredientsAdapter = new ViewIngredientsAdapter(recipe.getIngredients());
        ingredients.setAdapter(ingredientsAdapter);

        title.setText(recipe.getName());
        category.setText(recipe.getCategory());
        publisher.setText(recipe.getPublisher().getUsername());
        if (user.getFavoriteRecipes().contains(recipe))
            foodImage.setImageResource(R.drawable.ic_full_heart);
        else
            foodImage.setImageResource(R.drawable.ic_empty_heart);
        foodImage.setImageResource(recipe.getImageId());
        instructions.setText(recipe.getInstructions());

        editButton.setOnClickListener(r -> {
            // navigate
        });

        removeButton.setOnClickListener(r -> {
            viewModel.removeRecipe(recipe.getId());
            navController.navigate(R.id.fragment_personal_recipes);
        });
    }

}
