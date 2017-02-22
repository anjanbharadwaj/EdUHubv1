package com.example.anjanbharadwaj.agendaplusapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

/**
 * Created by anjanbharadwaj on 12/26/16.
 */

public class Addnote extends AppCompatActivity{
    public static String it;
    ListView listview;
    View previouslySelectedItem = null;
    String c;
    Button add;
    ArrayAdapter<String> myAdapter;
    ArrayList<String> arrayList = new ArrayList<>();
    String username = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
    DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("UserSide").child(username).child("NoteNames");
    DatabaseReference root2;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnotes);
        listview = (ListView)findViewById(R.id.yournotes);
        add = (Button)findViewById(R.id.add);
        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listview.setAdapter(myAdapter);
        c = (String) (getIntent().getExtras().get("ClassName").toString());
        root2 = FirebaseDatabase.getInstance().getReference().child("ServerSide").child("ClientClasses").child(c).child("SharedNotes");
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();

                while(i.hasNext()){
                    set.add(((DataSnapshot)i.next()).getKey());

                }
                arrayList.clear();
                arrayList.addAll(set);

                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, int i, long l) {
                 it = adapterView.getItemAtPosition(i).toString();
                if (previouslySelectedItem != null)
                {
                    previouslySelectedItem.setBackgroundColor(
                            getResources().getColor(R.color.transparent));
                }

                view.setBackgroundColor(
                        getResources().getColor(R.color.colorhighlight));

                previouslySelectedItem = view;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.child(it).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put(it, dataSnapshot.getValue().toString());
                        root2.updateChildren(map);
                        arrayList.add(it);
                        ClassFilesActivity.arrayAdapter2.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });




        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8), (int) (height*.6));
    }

    @Override
    protected void onStart() {
        super.onStart();
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }
}
