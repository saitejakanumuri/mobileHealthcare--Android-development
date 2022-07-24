package com.example.mini2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.UUID;
import java.util.HashMap;


public class Todatabase extends AppCompatActivity {
    final int requestcode=10;
    static ArrayList<String> al;
    public EditText filename;
    StorageReference pathref;
    ListView lv;
    public String[] email;
    public String fnamearr[];
    static ArrayList<String> alink;
    private Uri filepath;
    StorageReference storagereference;
 //   HashMap<String,String> h = new HashMap<String,String>();
    DatabaseReference dbreference;
    public Todatabase(){
        al=new ArrayList<String>();
        alink=new ArrayList<String>();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String emailid= getIntent().getStringExtra("emailid");
        email=emailid.split("@",2);
        lv =findViewById(R.id.lsitview12);
        dbreference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://health-care-mobile-76dfd-default-rtdb.firebaseio.com/");
        setContentView(R.layout.activity_todatabase);
        Button sbtn=findViewById(R.id.selectbtn);
        Button ubtn=findViewById(R.id.uploadbtn);
        Button vbtn=findViewById(R.id.viewfilebtn);
        vbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in= new Intent(getApplicationContext(),ShowFolders.class);
                in.putExtra("email",email[0]);
                // in.putExtra("fileslist",al);
                //in.putExtra("linksforfiles",alink);
                startActivity(in);
            }
        });
        ubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uplaodtofirebase();
            }
        });
        filename=findViewById(R.id.filename);
        storagereference= FirebaseStorage.getInstance().getReference();
        sbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,requestcode);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==requestcode&&resultCode==RESULT_OK&&data.getData()!=null)
            filepath=data.getData();

    }
    public void uplaodtofirebase(){
        ProgressDialog pd= new ProgressDialog(this);
        if(TextUtils.isEmpty(filename.getText().toString()))
        {
            filename.setText("dummy_folder/"+UUID.randomUUID().toString());
        }
        else {
            pd.setTitle("uploading");
            pd.show();
            String[] nameoffolder= filename.getText().toString().split("/",2);
            pathref = storagereference.child(email[0]).child(nameoffolder[0]).child(nameoffolder[1]);
            pathref.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    pd.dismiss();
                    Toast.makeText(Todatabase.this, "uploaded!!", Toast.LENGTH_SHORT).show();
                   // al.add(filename.getText().toString());
                    DatabaseReference dbref = dbreference.child(email[0]).child(nameoffolder[0]).child(nameoffolder[1]);
                    pathref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            dbref.setValue(url);
            //                h.put(filename.getText().toString(),url);
              //              alink.add(url);
                        }
                    });
                    filename.setText("");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(Todatabase.this, "failedtoupload", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    pd.show();
                }
            });
        }
    }
}