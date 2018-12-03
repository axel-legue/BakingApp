package com.legue.axel.bakingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.legue.axel.bakingapp.Constants;
import com.legue.axel.bakingapp.R;
import com.legue.axel.bakingapp.database.model.Ingredient;

import java.text.DecimalFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientHolder> {

    private Context mContext;
    private List<Ingredient> ingredientList;

    public IngredientsAdapter(Context mContext, List<Ingredient> ingredientList) {
        this.mContext = mContext;
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public IngredientHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_ingredient, viewGroup, false);
        return new IngredientHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientHolder ingredientHolder, int position) {
        final Ingredient ingredient = ingredientList.get(position);
        String measure = null;
        String quantity;
        String name;

        if (ingredient.getMeasure() != null && ingredient.getMeasure().length() > 0) {
            if (Constants.measureMap.containsKey(ingredient.getMeasure())) {
                measure = Constants.measureMap.get(ingredient.getMeasure());
            }
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        quantity = decimalFormat.format(ingredient.getQuantity());
        if (ingredient.getName() != null && ingredient.getName().length() > 0) {
            name = ingredient.getName().toLowerCase();
        } else {
            name = "unknown";
        }

        String ingredientText = mContext.getString(R.string.ingredient_quantity, quantity, measure, name);
        ingredientHolder.ingredient.setText(ingredientText);
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    class IngredientHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_ingredient_card)
        TextView ingredient;

        IngredientHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
