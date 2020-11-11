package com.example.bloodwave;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodwave.adapter.DonerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class CurrentBloodRequestActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    EditText editTextNumber, editTextEmail, editTextName;
    RadioGroup radioGroupGender, radioBloodGroup;
    private FirebaseAuth mAuth;
    Spinner spinnerBloodGroup;
    Button button3;
    ArrayList data;

    private int year, month, day;


    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
CreateDonerModel createDonerModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doner_listing);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        final ProgressBar progressbar= findViewById(R.id.progressbar);

        mDatabase.child("requests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                for (Object zoneSnapshot : (ArrayList)dataSnapshot.getValue(Object.class)) {

                 ArrayList arrayList=new ArrayList();
                    arrayList.add(  zoneSnapshot);

//                    String name=(String) zoneSnapshot.child("name").getValue();
//                    String phone=(String) zoneSnapshot.child("phone").getValue();
//                    String email=(String) zoneSnapshot.child("email").getValue();
//                    String date=(String) zoneSnapshot.child("date").getValue();
//                    String bloodgroup=(String) zoneSnapshot.child("bloodgroup").getValue();
//
//                    createDonerModel=new CreateDonerModel(name,email,phone,"",bloodgroup,date)
//A
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleView);
                    DonerAdapter adapter = new DonerAdapter(arrayList);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(adapter);
                    progressbar.setVisibility(View.GONE);
                }
                Log.e("", "");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }



}
