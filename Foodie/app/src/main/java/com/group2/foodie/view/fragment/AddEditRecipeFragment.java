package com.group2.foodie.view.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group2.foodie.R;
import com.group2.foodie.list.EditableIngredientsAdapter;
import com.group2.foodie.model.Ingredient;
import com.group2.foodie.util.Util;
import com.group2.foodie.viewmodel.AddEditRecipeViewModel;

public class AddEditRecipeFragment extends Fragment {
    private AddEditRecipeViewModel viewModel;
    private NavController navController;
    private EditText recipeNameInput;
    private SwitchCompat publicSwitch;
    private ImageView image;
    private Button uploadBtn;
    private Spinner recipeCategoryInput;
    private EditText ingredientNameInput;
    private EditText ingredientQuantityInput;
    private Spinner ingredientMeasurementInput;
    private Button addIngredientBtn;
    private RecyclerView ingredientsRecyclerView;
    private EditText recipeInstructionsInput;
    private Button saveBtn;
    private EditableIngredientsAdapter ingredientsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_addedit_recipe, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(AddEditRecipeViewModel.class);
        if (getArguments() != null) {
            viewModel.init(getArguments().getString("publisherId"),
                    getArguments().getString("recipeId"));
        } else
            viewModel.init();

        initializeViews(view);
        setupViews();
    }

    private void initializeViews(View view) {
        navController = Navigation.findNavController(view);
        recipeNameInput = view.findViewById(R.id.addedit_recipeNameInput);
        publicSwitch = view.findViewById(R.id.addedit_recipe_publicSwitch);
        image = view.findViewById(R.id.addedit_recipe_image);
        uploadBtn = view.findViewById(R.id.addedit_recipe_uploadButton);
        recipeCategoryInput = view.findViewById(R.id.addedit_recipe_categoryInput);
        ingredientNameInput = view.findViewById(R.id.addedit_recipe_ingredientNameInput);
        ingredientQuantityInput = view.findViewById(R.id.addedit_recipe_ingredientQuantityInput);
        ingredientMeasurementInput = view.findViewById(R.id.addedit_recipe_ingredientMeasurementInput);
        addIngredientBtn = view.findViewById(R.id.addedit_recipe_addIngredientButton);
        ingredientsRecyclerView = view.findViewById(R.id.addedit_recipe_ingredientsRecyclerView);
        recipeInstructionsInput = view.findViewById(R.id.addedit_recipe_instructionsInput);
        saveBtn = view.findViewById(R.id.addedit_recipe_saveButton);
    }

    private void setupViews() {
        ingredientsRecyclerView.hasFixedSize();
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ingredientsAdapter = new EditableIngredientsAdapter(viewModel.getIngredients().getValue());
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);

        viewModel.getCurrentUser().observe(getViewLifecycleOwner(), user -> {
        });

        viewModel.getRecipeCategories().observe(getViewLifecycleOwner(), categoryList -> {
            ArrayAdapter<String> recipeCategoryAdapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_item,
                    categoryList);
            recipeCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            recipeCategoryInput.setAdapter(recipeCategoryAdapter);
        });

        ArrayAdapter<String> recipeCategoryAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item,
                viewModel.getRecipeCategories().getValue());
        recipeCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recipeCategoryInput.setAdapter(recipeCategoryAdapter);

        if (getArguments() != null) {
            viewModel.getRecipe().observe(getViewLifecycleOwner(), recipe -> {
                recipeNameInput.setText(recipe.getName());
                publicSwitch.setChecked(recipe.isPublic());
                recipeCategoryInput.setSelection(viewModel.getRecipeCategories().getValue().indexOf(recipe.getCategory()));
                recipeInstructionsInput.setText(recipe.getInstructions());
                for (Ingredient ingredient : recipe.getIngredients()) {
                    viewModel.addNewIngredient(ingredient.getName(),
                            String.valueOf(ingredient.getQuantity()),
                            ingredient.getMeasurement().toString());
                }
            });
        }

        ingredientsAdapter.setOnRemoveListener(ingredient -> {
            viewModel.removeIngredient(ingredient);
        });

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getData() != null) {
                Uri image = result.getData().getData();
                this.image.setImageURI(image);
            }
        });

        image.setDrawingCacheEnabled(true);
        image.buildDrawingCache();

        uploadBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            intent.setType("image/*");
            activityResultLauncher.launch(intent);
        });

        ArrayAdapter<String> ingredientMeasurementAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item,
                Util.getMeasurements());
        ingredientMeasurementAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ingredientMeasurementInput.setAdapter(ingredientMeasurementAdapter);

        addIngredientBtn.setOnClickListener(v -> {
            if (viewModel.addNewIngredient(ingredientNameInput.getText().toString(),
                    ingredientQuantityInput.getText().toString(),
                    ingredientMeasurementInput.getSelectedItem().toString())) {
                ingredientNameInput.setText("");
                ingredientQuantityInput.setText("");
                ingredientMeasurementInput.setSelection(0);
            }
        });

        viewModel.getIngredients().observe(getViewLifecycleOwner(), ingredients -> {
            ingredientsAdapter.setIngredients(ingredients);
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        saveBtn.setOnClickListener(v -> {
            if (getArguments() != null) {
                if (viewModel.editRecipe(recipeNameInput.getText().toString(),
                        recipeCategoryInput.getSelectedItem().toString(),
                        publicSwitch.isChecked(),
                        recipeInstructionsInput.getText().toString())) {
                    Toast.makeText(getActivity(), "Recipe \"" + recipeNameInput.getText().toString() +
                            "\" saved!", Toast.LENGTH_SHORT).show();
                    navController.popBackStack();
                }
            } else if (viewModel.isValid(recipeNameInput.getText().toString(),
                    recipeCategoryInput.getSelectedItem().toString(),
                    recipeInstructionsInput.getText().toString())) {

                String recipeId = viewModel.addRecipe(recipeNameInput.getText().toString(),
                        recipeCategoryInput.getSelectedItem().toString(),
                        publicSwitch.isChecked(),
                        recipeInstructionsInput.getText().toString()
                );

                if (image.getDrawable() != null) {
                    Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                    viewModel.uploadRecipeImage(bitmap, recipeId);
                }

                Toast.makeText(getActivity(), "Recipe \"" + recipeNameInput.getText().toString() +
                        "\" created!", Toast.LENGTH_SHORT).show();
                navController.popBackStack();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();

        viewModel.reset();
    }
}