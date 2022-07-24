package com.example.mini2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivitydemo extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activitydemo);
        final ImageButton uploadImageBtn=(ImageButton) findViewById(R.id.uplaodToCloud);
        final ImageButton folderBtn= (ImageButton) findViewById(R.id.folder);
        final ImageButton diseaseBtn=(ImageButton) findViewById(R.id.disesase_medicines);
        String email=getIntent().getStringExtra("emailid");
        uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          /*      Intent i=new Intent(MainActivitydemo.this,StartingActivity.class);
                startActivity(i); */
                startActivity(new Intent(getApplicationContext(),Todatabase.class).putExtra("emailid",email));
            }
        });
        folderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailid= getIntent().getStringExtra("emailid");
                String[] email =emailid.split("@",2);
                startActivity(new Intent(getApplicationContext(),ShowFolders.class).putExtra("email",email[0]));
            }
        });
        diseaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          /*      Intent i=new Intent(MainActivitydemo.this,MainActivity.class);
                startActivity(i); */
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}