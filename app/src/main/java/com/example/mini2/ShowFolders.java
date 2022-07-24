package com.example.mini2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

public class ShowFolders extends AppCompatActivity {
    public Button del,viewfilebtn;
    Intent it;
    public EditText foldername;
    public String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_folders);
        ListView lv=findViewById(R.id.lsitview12);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        TextView folderdescribe=findViewById(R.id.folderdescribe);
        ArrayList<String> foldernames = new ArrayList<>();
        email = getIntent().getStringExtra("email");
        folderdescribe.setText("FOLDERS INSIDE "+email+" ARE:");
        it=new Intent(getApplicationContext(),Filesview.class);
        it.putExtra("EMAIL",email);
        del=findViewById(R.id.deletebtn);
        viewfilebtn=findViewById(R.id.viewfilebtn);
        ArrayAdapter<String> aa = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,foldernames);
        DatabaseReference dref =FirebaseDatabase.getInstance().getReference().child(email);
        StorageReference sref = FirebaseStorage.getInstance().getReference().child(email);
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    foldernames.add(ds.getKey());
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
                ClipData cd = ClipData.newPlainText("copied filename",foldernames.get(i));
                cb.setPrimaryClip(cd);
                Toast.makeText(getApplicationContext(),"filename_copied",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                it.putExtra("selected_folder",foldernames.get(i));
                startActivity(it);
            }
        });
      /*  del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String folder=foldername.getText().toString();
                if(folder.isEmpty()){
                    foldername.setHint("!! Foldername !!");
                }
                else {
                    dref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds1 : snapshot.getChildren()) {
                                if (ds1.getKey().equals(folder)) {
                                    dref.child(folder).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(getApplicationContext(), "Folder  DELETED", Toast.LENGTH_SHORT).show();
                                            finish();
                                            startActivity(getIntent());
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), "FAILED TO DELETE", Toast.LENGTH_SHORT).show();
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
                    sref.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                        @Override
                        public void onSuccess(ListResult listResult) {
                            for(StorageReference item:listResult.getItems())
                            {
                                if(item.toString().equals(folder))
                                {
                                    item.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(getApplicationContext(),"FOLDER DELETED!!",Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(),"Failed to Delete!!",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"NO FOLDER",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }
        }); */
    }
}