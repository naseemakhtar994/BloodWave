package com.example.bloodwave;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    ArrayList data;
    EditText editTextEmail, editTextPhone;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String User = "User";
    public static final String Phone = "phoneKey";
    public static final String Email = "emailKey";
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        editTextEmail = findViewById(R.id.editTextTextPersonName3);
        editTextPhone = findViewById(R.id.editTextTextPersonName4);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("doners").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                data = (ArrayList<CreateDonerModel>) dataSnapshot.getValue(Object.class);
                if (data == null) {
                    data = new ArrayList();
                }

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

    public void login(View view) {
        for (HashMap<String, String> myListData : (ArrayList<HashMap<String, String>>) data) {
            String email = myListData.get("email");
            String phone = myListData.get("phone");
            String name = myListData.get("name");
            String gender = myListData.get("gender");
            String bloodgroup = myListData.get("bloodgroup");

            SharedPreferences.Editor editor = sharedpreferences.edit();

            String  jsonobject=   new Gson().toJson(new CreateDonerModel(name, email, phone, gender, bloodgroup));
            editor.putString("User", jsonobject);

            editor.commit();

            if (editTextEmail.getText().toString().equals(email) && editTextPhone.getText().toString().equals(phone)) {

                Intent intent = new Intent(getApplicationContext(), DashBoardActivity.class);
                startActivity(intent);
                finish();
            }


        }
    }
}
