package com.example.mini2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StartingActivity extends AppCompatActivity {
    EditText emailid;
    Button setemailaskey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);
        emailid=findViewById(R.id.emailid);
        setemailaskey =findViewById(R.id.setemailaskey);
        setemailaskey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emailid.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"YOUR EMAIL-ID REQUIRED",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent it = new Intent(getApplicationContext(), MainActivitydemo.class);
                    it.putExtra("emailid",emailid.getText().toString());
                    startActivity(it);
                }
            }
        });
    }
}