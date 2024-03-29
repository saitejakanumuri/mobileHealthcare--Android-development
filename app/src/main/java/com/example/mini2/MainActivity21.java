package com.example.mini2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;


public class MainActivity21 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main21);
        Switch dispM = (Switch) findViewById(R.id.displayM);
        dispM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (dispM.isChecked()) {
                    Intent i = new Intent(MainActivity21.this, pneumonia_treatment.class);
                    startActivity(i);
                    dispM.setChecked(false);
                }
            }
        });
    }
}