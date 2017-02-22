package com.example.anjanbharadwaj.agendaplusapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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

public class Messenger extends AppCompatActivity {
    TextView cname;
    ArrayList<String> messages = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    EditText msg;
    Button send;

    DatabaseReference root;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        String classname = (String) (getIntent().getExtras().get("Classname"));
        cname = (TextView)findViewById(R.id.textView8);
        cname.setText(classname);
        listView = (ListView)findViewById(R.id.listview);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, messages);
        listView.setAdapter(arrayAdapter);
        send = (Button)findViewById(R.id.button6);
        msg = (EditText)findViewById(R.id.editText5);
        root = FirebaseDatabase.getInstance().getReference().child("ServerSide").child("ClientClasses").child(classname).child("Messages");

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!msg.getText().toString().isEmpty()){
                    Map<String, Object> map = new HashMap<String, Object>();
                    String email = FirebaseAuth.getInstance().getCurrentUser().getEmail().split("@")[0];
                    map.put(email, msg.getText().toString());
                    root.updateChildren(map);
                    msg.setText("");
                }
            }
        });
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();
                while(i.hasNext()){
                    Object o = i.next();
                    set.add(((DataSnapshot) o).getKey().toString()+ "-" + ((DataSnapshot) o).getValue().toString());

                    //set.add((((DataSnapshot)i.next()).getKey().toString()) + " due " + ((DataSnapshot)i.next()).getValue().toString().replaceAll("-", "/"));

                }
                System.out.println("We made it to this part of messages");
                messages.clear();
                messages.addAll(set);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
