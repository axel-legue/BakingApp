package com.legue.axel.bakingapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.legue.axel.bakingapp.R;
import com.legue.axel.bakingapp.database.model.Recipe;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

    private final static String TAG = RecipeAdapter.class.getName();
    private Context mContext;
    private List<Recipe> recipeList;
    private RecipeListener recipeListener;


    public interface RecipeListener {
        void onRecipeSelected(int recipeId);
    }


    public RecipeAdapter(Context mContext, List<Recipe> recipeList, RecipeListener recipeListener) {
        this.mContext = mContext;
        this.recipeList = recipeList;
        this.recipeListener = recipeListener;

    }

    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_recipe, parent, false);
        return new RecipeHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeHolder recipeHolder, int position) {
        final Recipe recipe = recipeList.get(position);

        recipeHolder.mRecipeName.setText(recipe.getTitle());
        recipeHolder.mRecipeServings.setText(mContext.getResources().getString(R.string.servings, recipe.getServings()));
        recipeHolder.cardView.setOnClickListener(view -> {
            Log.i(TAG, "recipe selected:  " + recipe.getRecipeId());
            recipeListener.onRecipeSelected(recipe.getRecipeId());
        });

        switch (recipe.getTitle()) {
            case "Nutella Pie":
                loadImage(R.drawable.nutella_pie, recipeHolder.mRecipeImageView);
                break;
            case "Brownies":
                loadImage(R.drawable.brownies, recipeHolder.mRecipeImageView);
                break;
            case "Yellow Cake":
                loadImage(R.drawable.yellow_cake, recipeHolder.mRecipeImageView);
                break;
            case "Cheesecake":
                loadImage(R.drawable.cheese_cake, recipeHolder.mRecipeImageView);
                break;
            default:
                loadImage(R.drawable.placeholder_image, recipeHolder.mRecipeImageView);
                break;
        }
    }

    private void loadImage(int drawable, ImageView imageView) {
        Picasso.get()
                .load(drawable)
                .error(R.drawable.placeholder_image)
                .placeholder(R.drawable.placeholder_image)
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class RecipeHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cv_content)
        CardView cardView;
        @BindView(R.id.iv_recipe)
        RoundedImageView mRecipeImageView;
        @BindView(R.id.tv_recipe_name)
        TextView mRecipeName;
        @BindView(R.id.tv_serving)
        TextView mRecipeServings;


        RecipeHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
