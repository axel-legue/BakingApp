package com.legue.axel.bankingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.legue.axel.bankingapp.R;
import com.legue.axel.bankingapp.fragment.StepsFragment;
import com.legue.axel.bankingapp.database.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepHolder> {

    private Context mContext;
    private List<Step> stepList;
    private StepsFragment.StepListener stepListener;


    public StepAdapter(Context mContext, List<Step> stepList, StepsFragment.StepListener stepListener) {
        this.mContext = mContext;
        this.stepList = stepList;
        this.stepListener = stepListener;
    }

    @NonNull
    @Override
    public StepHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_step, viewGroup, false);
        return new StepHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StepHolder stepHolder, int i) {
        Step step = stepList.get(i);

        String stepDescription = mContext.getString(R.string.step_card, i , step.getShortDescription());
        stepHolder.stepShortDescription.setText(stepDescription);
        stepHolder.cardViewStep.setOnClickListener(view -> stepListener.stepSelected(stepList.get(0).getStepId(), stepList.get(stepList.size()-1).getStepId(), step.getStepId()));

    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    public class StepHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cv_step)
        CardView cardViewStep;
        @BindView(R.id.tv_step)
        TextView stepShortDescription;

        public StepHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
