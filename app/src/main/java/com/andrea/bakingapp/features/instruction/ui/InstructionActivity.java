package com.andrea.bakingapp.features.instruction.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.andrea.bakingapp.R;

public class InstructionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                                       .add(R.id.recipeInstructionsFrameLayout, new InstructionFragment())
                                       .commit();
        }
    }

}
