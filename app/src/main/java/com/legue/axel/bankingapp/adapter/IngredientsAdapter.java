package com.legue.axel.bankingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.legue.axel.bankingapp.Constants;
import com.legue.axel.bankingapp.R;
import com.legue.axel.bankingapp.database.model.Ingredient;

import java.text.DecimalFormat;
import java.util.List;

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
        String quantity = null;
        String name = null;

        if (ingredient.getMeasure() != null && ingredient.getMeasure().length() > 0) {
            if (Constants.measureMap.containsKey(ingredient.getMeasure())) {
                measure = Constants.measureMap.get(ingredient.getMeasure());
            }
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        quantity = decimalFormat.format(ingredient.getQuantity());
        if (ingredient.getName() != null && ingredient.getName().length() > 0) {
            name = ingredient.getName();
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

    public class IngredientHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_ingredient_card)
        TextView ingredient;

        public IngredientHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
