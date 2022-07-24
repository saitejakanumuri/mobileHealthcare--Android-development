package com.example.mini2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.settings:
                Intent i1=new Intent(MainActivity.this,MainActivitydemo.class);
                startActivity(i1);
                Toast.makeText(this, "settings_Selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner=findViewById(R.id.spinner);


        List<String> diseases=new ArrayList<>();
        diseases.add( 0,"Choose disease");
        diseases.add("Drug Allergy");  //c
        diseases.add("Hypothyroidism");//c
        diseases.add("Psoriasis");     //c
        diseases.add("hepatitis A");   //c
        diseases.add("Diabetes"); //c
        diseases.add("Hypertension");     //c
        diseases.add("Common cold");//c
        diseases.add("Varicose veins");   //c
        diseases.add("Migraine");//c
        diseases.add("Heart Attack"); //c
        diseases.add("Pneumonia");//c
        diseases.add("Arthritis");//c

        ArrayAdapter<String> dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,diseases);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Choose category"))
                 {

                }
                else
                    {
                        String item=parent.getItemAtPosition(position).toString();
                        Toast.makeText(parent.getContext(),"Selected:"+item, Toast.LENGTH_SHORT).show();
                        if(parent.getItemAtPosition(position).equals("Drug Allergy"))
                        {
                            Intent intent=new Intent(MainActivity.this, MainActivity2.class);
                            startActivity(intent);
                        }
                        if(parent.getItemAtPosition(position).equals("Hypothyroidism"))
                        {
                            Intent intent=new Intent(MainActivity.this, MainActivity5.class);
                            startActivity(intent);
                        }

                        if(parent.getItemAtPosition(position).equals("Psoriasis"))
                        {
                            Intent intent=new Intent(MainActivity.this, MainActivity6.class);
                            startActivity(intent);
                        }

                        if(parent.getItemAtPosition(position).equals("hepatitis A"))
                        {
                            Intent intent=new Intent(MainActivity.this, MainActivity7.class);
                            startActivity(intent);
                        }
                        if(parent.getItemAtPosition(position).equals("Diabetes"))
                        {
                            Intent intent=new Intent(MainActivity.this, MainActivity9.class);
                            startActivity(intent);
                        }

                        if(parent.getItemAtPosition(position).equals("Hypertension"))
                        {
                            Intent intent=new Intent(MainActivity.this, MainActivity10.class);
                            startActivity(intent);
                        }

                        if(parent.getItemAtPosition(position).equals("Common cold"))
                        {
                            Intent intent=new Intent(MainActivity.this, MainActivity11.class);
                            startActivity(intent);
                        }
                        if(parent.getItemAtPosition(position).equals("Varicose veins"))
                        {
                            Intent intent=new Intent(MainActivity.this, MainActivity14.class);
                            startActivity(intent);
                        }
                        if(parent.getItemAtPosition(position).equals("Migraine"))
                        {
                            Intent intent=new Intent(MainActivity.this, MainActivity17.class);
                            startActivity(intent);
                        }
                        if(parent.getItemAtPosition(position).equals("Heart Attack"))
                        {
                            Intent intent=new Intent(MainActivity.this, MainActivity20.class);
                            startActivity(intent);
                        }

                        if(parent.getItemAtPosition(position).equals("Pneumonia"))
                        {
                            Intent intent=new Intent(MainActivity.this, MainActivity21.class);
                            startActivity(intent);
                        }

                        if(parent.getItemAtPosition(position).equals("Arthritis"))
                        {
                            Intent intent=new Intent(MainActivity.this, MainActivity22.class);
                            startActivity(intent);
                        }

                    }
            }
         @Override
                public void onNothingSelected(AdapterView<?> parent){
           // TODO Auto-generated method stub
        }

    });
}
}