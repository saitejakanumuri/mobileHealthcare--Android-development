package com.example.mini2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity20 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main20);
        Switch dispM = (Switch) findViewById(R.id.displayM);
        dispM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (dispM.isChecked()) {
                    Intent i = new Intent(MainActivity20.this, cardiac_arrest_20_treatment.class);
                    startActivity(i);
                    dispM.setChecked(false);
                }
            }
        });

    }
}