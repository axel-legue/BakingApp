package com.legue.axel.bankingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.legue.axel.bankingapp.database.BakingDatabase;
import com.legue.axel.bankingapp.database.DataBaseUtils;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private BakingDatabase mDatabase;
    private DataBaseUtils dataBaseUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = BakingDatabase.getsInstance(this);
        AppExecutors.getInstance().getDiskIO().execute(() -> mDatabase.clearAllTables());

        dataBaseUtils = new DataBaseUtils(this, mDatabase);
        dataBaseUtils.fillDatabase();
    }

}
