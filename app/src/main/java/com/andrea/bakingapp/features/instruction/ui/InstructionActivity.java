package com.andrea.bakingapp.features.instruction.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.andrea.bakingapp.R;

public class InstructionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        InstructionFragment fragment = new InstructionFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                       .add(R.id.recipeInstructions, fragment)
                       .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        InstructionFragment fragment = new InstructionFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.recipeInstructions, fragment)
                .commit();
    }
}
