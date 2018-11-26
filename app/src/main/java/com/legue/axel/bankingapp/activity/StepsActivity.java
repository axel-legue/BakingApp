package com.legue.axel.bankingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.legue.axel.bankingapp.Constants;
import com.legue.axel.bankingapp.R;
import com.legue.axel.bankingapp.adapter.StepAdapter;
import com.legue.axel.bankingapp.fragment.StepDetailFragment;
import com.legue.axel.bankingapp.fragment.StepsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsActivity extends AppCompatActivity implements StepsFragment.StepListener {

    private static final String TAG = StepsActivity.class.getName();

    private int recipeIdSelected;
    private boolean mTwoPane;
    private StepDetailFragment stepDetailFragment;
    private StepsFragment stepsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.KEY_RECIPE_ID)) {
            recipeIdSelected = intent.getIntExtra(Constants.KEY_RECIPE_ID, -1);
        }

        stepsFragment = new StepsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.KEY_RECIPE_ID, recipeIdSelected);
        stepsFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.step_container, stepsFragment)
                .commit();


        if (findViewById(R.id.detail_container) != null) {
            mTwoPane = true;
            stepDetailFragment = new StepDetailFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.detail_container, stepDetailFragment)
                    .commit();
        } else {
            mTwoPane = false;
        }
    }

    @Override
    public void stepSelected(int firstStepId, int lastStepId, int stepSelectedId) {
        if (!mTwoPane) {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(Constants.KEY_FIRST_STEP_ID, firstStepId);
            intent.putExtra(Constants.KEY_LAST_STEP_ID, lastStepId);
            intent.putExtra(Constants.KEY_STEPS_ID, stepSelectedId);
            startActivity(intent);
        } else {
            stepDetailFragment.updateDetails(firstStepId, lastStepId, stepSelectedId);
            // TODO : Check how and what Data we will send
        }

    }
}
