package com.example.bloodwave;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.bloodwave.adapter.DonerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public  class DonerListingActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doner_listing);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final ProgressBar progressbar= findViewById(R.id.progressbar);
        mDatabase.child("doners").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


             ArrayList   data=(ArrayList<CreateDonerModel>)dataSnapshot.getValue(Object.class);
                if (data == null) {
                    data = new ArrayList();
                }
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleView);
                DonerAdapter adapter = new DonerAdapter(data);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);
                progressbar.setVisibility(View.GONE);
                for (DataSnapshot zoneSnapshot: dataSnapshot.getChildren()) {
//                    CreateDonerModel post = dataSnapshot.getValue(CreateDonerModel.class);
                    Log.e("", (String) zoneSnapshot.child("phone").getValue());
                }
                Log.e("","");
//                Log.i(TAG, dataSnapshot.child("ZNAME").getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("","");
//                Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });


    }
}