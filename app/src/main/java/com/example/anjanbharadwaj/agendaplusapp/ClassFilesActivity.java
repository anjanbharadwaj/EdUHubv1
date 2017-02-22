package com.example.anjanbharadwaj.agendaplusapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClassFilesActivity extends AppCompatActivity {
    Button chat;
    ArrayList<Map> addingarray;
    TextView pinandpassword;
    String infoofclass;
    ListView listviewhw;
    ListView listofnote;
    String pin;
    String passclass;
    TextView nameofclass;
    public static String thename;
    String user;
    Button pinpass;
    Button addnotes;
    LinearLayout linearLayout;
    public static ArrayAdapter<String> arrayAdapter;
    public static ArrayList<String> classhomework = new ArrayList<>();
    public static ArrayAdapter<String> arrayAdapter2;
    public static ArrayList<String> classnotes = new ArrayList<>();
    DatabaseReference rootnote;
    DatabaseReference rootpin;
    DatabaseReference rootpass;
    DatabaseReference serverroot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_files);
        chat = (Button)findViewById(R.id.openchat);
        addnotes = (Button)findViewById(R.id.addthenote);
        pinandpassword = (TextView)findViewById(R.id.pinandpass);
        linearLayout = (LinearLayout)findViewById(R.id.layoutl);
        nameofclass = (TextView)findViewById(R.id.title);
        thename = (String) (getIntent().getExtras().get("ClassName"));
        user = (String) (getIntent().getExtras().get("Username"));
        pinpass = (Button)findViewById(R.id.pinpass);
        listofnote = (ListView)findViewById(R.id.yournotes);
        nameofclass.setText(thename);
        rootnote = FirebaseDatabase.getInstance().getReference().child("ServerSide").child("ClientClasses").child(thename).child("SharedNotes");
        rootpin =  FirebaseDatabase.getInstance().getReference().child("UserSide").child(user).child("Classes").child(thename).child("PIN");
        rootpass = FirebaseDatabase.getInstance().getReference().child("UserSide").child(user).child("Classes").child(thename).child("Password");
        addnotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Addnote.class);
                intent.putExtra("ClassName", nameofclass.getText().toString());
                intent.putExtra("Array", classnotes);
                startActivity(intent);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), Messenger.class);
                intent2.putExtra("Classname", nameofclass.getText().toString());
                startActivity(intent2);
            }
        });

        rootpin.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //ArrayList<String> pins = new ArrayList<String>();
                Iterator i = dataSnapshot.getChildren().iterator();
                System.out.println("went to datachange");
                /*
                while(i.hasNext()){
                    pins.add(((DataSnapshot)i.next()).getKey());

                }
                */
                pin = dataSnapshot.getValue().toString();
                System.out.println(pin);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        rootpass.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //ArrayList<String> passes = new ArrayList<String>();
                //Iterator i = dataSnapshot.getChildren().iterator();
                /*
                while(i.hasNext()){
                    passes.add(((DataSnapshot)i.next()).getKey());

                }
                */
                passclass = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //pin = joinpin.getText().toString().trim();
        //password = joinpass.getText().toString().trim();



        pinpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoofclass = "(Pin: " + pin + ", Password: " + passclass + ")";
                pinandpassword.setText(infoofclass);
            }
        });
        //root = FirebaseDatabase.getInstance().getReference().child("UserSide").child(user).child(thename).child("Homework");
        serverroot = FirebaseDatabase.getInstance().getReference().child("ServerSide").child("ClientClasses").child(thename).child("Homework");

        listviewhw = (ListView)findViewById(R.id.listofhw);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, classhomework);
        arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, classnotes);
        //arrayAdapter = new MyCustomAdapter(classhomework, this);
        listviewhw.setAdapter(arrayAdapter);
        listofnote.setAdapter(arrayAdapter2);
        rootnote.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<String>();
                //Set<String> set2 = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();
                while(i.hasNext()){
                    set.add(((DataSnapshot) i.next()).getKey());
                    //set.add((((DataSnapshot)i.next()).getKey().toString()) + " due " + ((DataSnapshot)i.next()).getValue().toString().replaceAll("-", "/"));

                }
                classnotes.clear();
                classnotes.addAll(set);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        serverroot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<String>();
                //Set<String> set2 = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();
                while(i.hasNext()){
                    String key = (((DataSnapshot) i.next()).getKey());
                    String value = ((dataSnapshot).child(key).getValue().toString());
                    set.add(key+" due "+value);
                    //set.add((((DataSnapshot)i.next()).getKey().toString()) + " due " + ((DataSnapshot)i.next()).getValue().toString().replaceAll("-", "/"));

                }
                classhomework.clear();
                classhomework.addAll(set);

                /*
                GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};

                List<String> addingarray = new List<String>();
                addingarray.add(dataSnapshot.getValue())
                */
                //ArrayList<String> adding = (ArrayList<String>)(dataSnapshot.getValue());

                //for(int j = 0; j<dataSnapshot.getValue().)
                //Iterator i = dataSnapshot.getChildren().iterator();
                //while(i.hasNext()){
                    //set.add((((DataSnapshot)i.next()).getKey().toString()) + " due " + ((DataSnapshot)i.next()).getValue().toString().replaceAll("-", "/"));

                //}
                //classhomework.clear();
                /*
                for(int i = 0; i<adding.size(); i++) {
                    classhomework.add(adding.get(i));
                    /*
                    for (Object key : addingarray.get(i).keySet()) {
                        classhomework.add(key.toString() + " due " + addingarray.get(i).get(key));
                    }
                    */
                //classhomework.addAll(adding);


                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listviewhw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), DeleteHomework.class);
                intent.putExtra("Homework", ((TextView)view).getText().toString().trim());
                intent.putExtra("Username", FirebaseAuth.getInstance().getCurrentUser().getUid());
                //intent.putExtra("Listofhw", classhomework);
                //intent.putExtra("Pin", );
                //intent.putExtra("Password", pass.getText().toString().trim());
                startActivity(intent);
            }
        });
        listofnote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), Note.class);
                intent.putExtra("Name", thename);
                intent.putExtra("Notename", adapterView.getItemAtPosition(i).toString().trim());
                //Toast.makeText(ClassFilesActivity.this, adapterView.getItemAtPosition(i).toString().trim(), ,
                  //      Toast.LENGTH_SHORT).show();
                //intent.putExtra("Listofhw", classhomework);
                //intent.putExtra("Pin", );
                //intent.putExtra("Password", pass.getText().toString().trim());
                startActivity(intent);
            }
        });

    }
}
