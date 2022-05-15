package com.group2.foodie.view.fragment;

import android.app.AlertDialog;
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

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.group2.foodie.R;
import com.group2.foodie.list.ViewIngredientsAdapter;
import com.group2.foodie.util.GlideApp;
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
        viewModel.init(getArguments().getString("publisherId"),
                getArguments().getString("recipeId"));
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
        ingredientsAdapter.removeOnClickListener();
        ingredients.setAdapter(ingredientsAdapter);

        viewModel.getCurrentUser().observe(getViewLifecycleOwner(), user ->{});

        viewModel.getRecipe().observe(getViewLifecycleOwner(), recipe -> {
            title.setText(recipe.getName());
            category.setText(recipe.getCategory());
            publisher.setText(recipe.getPublisherUsername());
            instructions.setText(recipe.getInstructions());
            ingredientsAdapter.setIngredients(recipe.getIngredients());
            viewModel.getFavorite().observe(getViewLifecycleOwner(), favorite -> {
                if (favorite)
                    favoriteImage.setImageResource(R.drawable.ic_full_heart);
                else
                    favoriteImage.setImageResource(R.drawable.ic_empty_heart);
            });
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/recipes/" + recipe.getId());
            GlideApp.with(this)
                    .load(storageRef)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(foodImage);

            if (!recipe.getPublisherId().equals(viewModel.getCurrentUser().getValue().getUid())) {
                editButton.setVisibility(View.INVISIBLE);
                removeButton.setVisibility(View.INVISIBLE);
            }
        });
        publisher.setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("profileId",getArguments().getString("publisherId"));
                    navController.navigate(R.id.fragment_user_profile, bundle);
                }
        );

        favoriteImage.setOnClickListener(f -> {
            viewModel.changeFavorite();
        });

        editButton.setOnClickListener(r -> {
            Bundle bundle = new Bundle();
            bundle.putString("recipeId", getArguments().getString("recipeId"));
            bundle.putString("publisherId", getArguments().getString("publisherId"));
            navController.navigate(R.id.fragment_addedit_recipe, bundle);
        });

        AlertDialog.Builder deleteDialogBuilder = new AlertDialog.Builder(getActivity());
        deleteDialogBuilder.setMessage("Are you sure you want to delete this recipe?");
        deleteDialogBuilder.setPositiveButton("Yes", (dialogInterface, i) -> {
            viewModel.removeRecipe();
            navController.popBackStack();
        });
        deleteDialogBuilder.setNegativeButton("No", ((dialogInterface, i) -> {
        }));
        AlertDialog deleteDialog = deleteDialogBuilder.create();

        removeButton.setOnClickListener(r -> {
            deleteDialog.show();
        });
    }
}
