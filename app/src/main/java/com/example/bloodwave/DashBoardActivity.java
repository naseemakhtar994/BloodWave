package com.example.bloodwave;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;;import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class DashBoardActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    ArrayList data;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String User = "User";
    SharedPreferences sharedpreferences;
    CreateDonerModel createDonerModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("donation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                data = (ArrayList<CreateDonerModel>) dataSnapshot.getValue(Object.class);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("", "");
            }
        });

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        String userdata = sharedpreferences.getString(User, null);
         createDonerModel = new Gson().fromJson(userdata, CreateDonerModel.class);
    }

    public void DonationHistory(View view) {
        Intent intent = new Intent(getApplicationContext(), DonationListingActivity.class);
        startActivity(intent);
    }

    public void AllDoners(View view) {
        Intent intent = new Intent(getApplicationContext(), DonerListingActivity.class);
        startActivity(intent);
    }

    public void CurrentBloodReq(View view) {
        Intent intent = new Intent(getApplicationContext(), CurrentBloodRequestActivity.class);
        startActivity(intent);
    }

    public void RequestHistory(View view) {
        Intent intent = new Intent(getApplicationContext(), RequestListingActivity.class);
        startActivity(intent);
    }

    public void MyRequestHistory(View view) {
        Intent intent = new Intent(getApplicationContext(), RequestListingActivity.class);
        intent.putExtra("myemail",createDonerModel.email);
        intent.putExtra("myrequest",true);
        startActivity(intent);
    }

    public void ShowHoapirtal(View view) {
        String uri = "https://www.google.com/maps/search/hospitals+%26+clinics/@50.7424218,-120.4388117,11z/data=!3m1!4b1!4m8!2m7!3m6!1shospitals+%26+clinics!2sKamloops,+BC,+Canada!3s0x537e2cd33d0d3b31:0xd23e96aa9a6945e7!4m2!1d-120.3272675!2d50.674522";

        if (data == null) {
            data = new ArrayList();
        }
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
// Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");

        startActivity(mapIntent);

        Boolean isAlradyValue = false;
        for (HashMap<String, String> myListData : (ArrayList<HashMap<String, String>>) data) {
            String email = myListData.get("email");
            String phone = myListData.get("phone");


            if (email.equals(createDonerModel.email) && phone.equals(createDonerModel.phone)) {

                isAlradyValue = true;
            }


        }

        if (!isAlradyValue) {
            data.add(createDonerModel);
            mDatabase.child("donation").setValue(data);

        }


//                            data.add(new CreateDonerModel("hhh",editTextEmail.getText().toString(),editTextNumber.getText().toString()));
    }


    public void RequestBlood(View view) {
        Intent intent = new Intent(getApplicationContext(), RequestBloodActivity.class);
        intent.putExtra("myemail",createDonerModel.email);

        startActivity(intent);
    }

    public void signOut(View view) {

        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(User, null);

        editor.commit();

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

        startActivity(intent);
        finish();

    }
}