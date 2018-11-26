package com.legue.axel.bankingapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.legue.axel.bankingapp.Constants;
import com.legue.axel.bankingapp.R;
import com.legue.axel.bankingapp.fragment.StepDetailFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = DetailsActivity.class.getName();

    private int stepSelectedId;
    private int firstStepId;
    private int lastStepId;

    @BindView(R.id.detail_container)
    FrameLayout stepContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        Intent intent = getIntent();

        if (intent != null) {
            if (intent.hasExtra(Constants.KEY_STEPS_ID)) {
                stepSelectedId = intent.getIntExtra(Constants.KEY_STEPS_ID, -1);
            }
            if (intent.hasExtra(Constants.KEY_FIRST_STEP_ID)) {
                firstStepId = intent.getIntExtra(Constants.KEY_FIRST_STEP_ID, -1);
            }
            if (intent.hasExtra(Constants.KEY_LAST_STEP_ID)) {
                lastStepId = intent.getIntExtra(Constants.KEY_LAST_STEP_ID, -1);
            }
        }

        initData();
    }

    private void initData() {
        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.KEY_STEPS_ID, stepSelectedId);
        bundle.putInt(Constants.KEY_FIRST_STEP_ID, firstStepId);
        bundle.putInt(Constants.KEY_LAST_STEP_ID, lastStepId);
        stepDetailFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.detail_container, stepDetailFragment)
                .commit();
    }
}
