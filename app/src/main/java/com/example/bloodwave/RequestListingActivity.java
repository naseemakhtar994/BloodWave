package com.example.bloodwave;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodwave.adapter.DonerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class RequestListingActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    boolean ismyRequest = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doner_listing);
        Intent intent = getIntent();
        ismyRequest = intent.getBooleanExtra("myrequest", false);
        if (ismyRequest) {
            getSupportActionBar().setTitle("My Request");

        }
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final ProgressBar progressbar = findViewById(R.id.progressbar);
        mDatabase.child("requests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                ArrayList data = (ArrayList<CreateDonerModel>) dataSnapshot.getValue(Object.class);
                ArrayList mydata=new ArrayList();
                if (data == null) {
                    data = new ArrayList();
                }
                if(ismyRequest){
                    for (HashMap<String, String> item : (ArrayList<HashMap<String, String>>) data) {
                        String myemail = getIntent().getStringExtra("myemail");

                        if(myemail.equals(item.get("requesterEmail"))){
                            mydata.add(item);
                        }

                    }
                }else {
                    mydata=data;
                }

                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleView);
                DonerAdapter adapter = new DonerAdapter(mydata);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);
                progressbar.setVisibility(View.GONE);
                for (DataSnapshot zoneSnapshot : dataSnapshot.getChildren()) {
//                    CreateDonerModel post = dataSnapshot.getValue(CreateDonerModel.class);
                    Log.e("", (String) zoneSnapshot.child("phone").getValue());
                }
                Log.e("", "");
//                Log.i(TAG, dataSnapshot.child("ZNAME").getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
//                Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });


    }
}