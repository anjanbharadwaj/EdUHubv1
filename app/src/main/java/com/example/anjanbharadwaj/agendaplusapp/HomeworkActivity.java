package com.example.anjanbharadwaj.agendaplusapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HomeworkActivity extends AppCompatActivity {
    String whatclass;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> usersclasses = new ArrayList<>();
    ArrayList<String> classhomework = new ArrayList<>();
    Button addhw1;
    EditText homeworktoadd1;
    EditText date1;
    Spinner whatclass1;
    //EditText whatclass1;
    String hw;
    String date;
    //ArrayList<String> homework = new ArrayList<>();
    DatabaseReference hwroot;
    DatabaseReference hwroot2;
    DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("UserSide").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);
        addhw1 = (Button) findViewById(R.id.addhomework);
        homeworktoadd1 = (EditText) findViewById(R.id.homeworktoadd);
        date1 = (EditText) findViewById(R.id.date);
        whatclass1 = (Spinner)findViewById(R.id.classspinner);
        //whatclass1 = (EditText) findViewById(R.id.whatclass);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, usersclasses);
        whatclass1.setAdapter(arrayAdapter);
        whatclass1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                whatclass = (String) (adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("Went into hwactivity eventlistener");
                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();

                while(i.hasNext()){
                    set.add(((DataSnapshot)i.next()).getKey());

                }
                usersclasses.clear();
                usersclasses.addAll(set);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        addhw1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hwroot2 = FirebaseDatabase.getInstance().getReference().child("ServerSide").child("ClientClasses").child(whatclass.toString().trim()).child("Homework");
                //hwroot = FirebaseDatabase.getInstance().getReference().child("UserSide").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("AllMyHomework");
                hw = homeworktoadd1.getText().toString().trim();
                date = date1.getText().toString();

                String info = hw + " due: " + date.replaceAll("/", "-");
                classhomework.add(info);

                //Map<String, Object> map = new HashMap<String, Object>();
                //map.put(info, "");
                //hwroot.updateChildren(map);
                //hwroot2.updateChildren(map);
                hwroot2.setValue(classhomework);
                Toast.makeText(HomeworkActivity.this, "Homework added to Class: " + whatclass.toString().trim(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}
