package com.legue.axel.bankingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.legue.axel.bankingapp.database.BakingDatabase;

public class MainActivity extends AppCompatActivity {

    private BakingDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        initialiseDatabase();
    }

    private void initialiseDatabase() {
//        database = BakingDatabase.getsInstance(this);
    }
}
