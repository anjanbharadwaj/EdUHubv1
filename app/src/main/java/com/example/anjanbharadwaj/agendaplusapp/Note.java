package com.example.anjanbharadwaj.agendaplusapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by anjanbharadwaj on 12/27/16.
 */
public class Note extends AppCompatActivity{
    String classname;
    String notename;
    TextView title;
    ArrayList<String> arraylist;
    DatabaseReference ref;// = FirebaseDatabase.getInstance().getReference().child("ServerSide").child("ClientClasses").child(classname).child("SharedNotes").child(notename);
    EditText note;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharednote);
        note = (EditText)findViewById(R.id.editText4);
        title = (TextView)findViewById(R.id.textView6);
        classname = (String) (getIntent().getExtras().get("Name").toString());
        notename = (String) (getIntent().getExtras().get("Notename").toString());
        ref = FirebaseDatabase.getInstance().getReference().child("ServerSide").child("ClientClasses").child(classname).child("SharedNotes").child(notename);
        title.setText(notename + " - " + classname);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                note.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
