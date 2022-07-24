package com.example.mini2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.internal.StorageReferenceUri;

import java.util.ArrayList;

public class Filesview extends AppCompatActivity {
    public Button delbtn,viewfilebtn;
    public String folder_selected;
    public StorageReference sref;
    public String email;
    public EditText deletefilename;
    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filesview);
        ListView lv=findViewById(R.id.lsitview12);
        email=getIntent().getStringExtra("EMAIL");
        folder_selected=getIntent().getStringExtra("selected_folder");
        TextView filedescribe = findViewById(R.id.filedescribe);
        filedescribe.setText("FILES INSIDE "+folder_selected+" FOLDER ARE:");
        sref=FirebaseStorage.getInstance().getReference().child(email).child(folder_selected);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ArrayList<String> al_filenames=new ArrayList<>();
        delbtn=findViewById(R.id.deletebtn);
        deletefilename=findViewById(R.id.deletefilename);
        viewfilebtn=findViewById(R.id.viewfilebtn);
        ArrayList<String> al_filelinks= new ArrayList<>();
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,al_filenames);
        DatabaseReference dbref =FirebaseDatabase.getInstance().getReference().child(email).child(folder_selected);
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                delbtn.setClickable(true);
                if(snapshot.getChildrenCount()<1){
                    Toast.makeText(getApplicationContext(),"NO FILES IN CLOUD",Toast.LENGTH_SHORT).show();
                    delbtn.setClickable(false);
                }
                else {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        al_filenames.add(ds.getKey());
                        al_filelinks.add(ds.getValue().toString());
                    }
                }
                lv.setAdapter(aa);
                aa.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ClipboardManager cb=(ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData cd = ClipData.newPlainText("copied filename",al_filenames.get(i));
                cb.setPrimaryClip(cd);
                Toast.makeText(getApplicationContext(),"filename_copied",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(al_filelinks.get(i))));
            }
        });
        delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filename = deletefilename.getText().toString();
                if(deletefilename.getText().toString().isEmpty())
                {
                    deletefilename.setHint("!!Please enter filename!!");
                    Toast.makeText(getApplicationContext(),"provide file name",Toast.LENGTH_SHORT).show();
                }
                else {
                    sref.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                        @Override
                        public void onSuccess(ListResult listResult) {
                            for (StorageReference item : listResult.getItems()) {
                                if (item.toString().equals(filename)) {
                                    String url = sref.child(filename).getDownloadUrl().getResult().toString();
                                    FirebaseStorage.getInstance().getReferenceFromUrl(url).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(getApplicationContext(),"FILE DELETED",Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"NOT VALID FILENAME",Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });
                    dbref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds1 : snapshot.getChildren()) {
                                if (ds1.getKey().equals(filename)) {
                                    dbref.child(ds1.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(getApplicationContext(), "DELETED FROM DATABASE", Toast.LENGTH_SHORT).show();
                                            finish();
                                            startActivity(new Intent(getApplicationContext(),Todatabase.class));
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), "FAILED TO DELETE FROM DATABASE", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    Toast.makeText(getApplicationContext(), "INCORRECT FILENAME PROVIDED", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
}
