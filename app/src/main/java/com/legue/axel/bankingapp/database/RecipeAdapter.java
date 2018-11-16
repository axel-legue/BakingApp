package com.legue.axel.bankingapp.database;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.legue.axel.bankingapp.R;
import com.legue.axel.bankingapp.database.model.Recipe;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

    private Context mContext;
    private List<Recipe> recipeList;
    private RecipeListener recipeListener;

    public interface RecipeListener {
        void recipeSelected(Recipe recipe);
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
        recipeHolder.mRecipeServings.setText(String.valueOf(recipe.getServings()));
        recipeHolder.cardView.setOnClickListener(view -> recipeListener.recipeSelected(recipe));

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
        Picasso.with(mContext)
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
        @BindView(R.id.iv_level_1)
        ImageView mDifficulty_1;
        @BindView(R.id.iv_level_2)
        ImageView mDifficulty_2;
        @BindView(R.id.iv_level_3)
        ImageView mDifficulty_3;


        public RecipeHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
