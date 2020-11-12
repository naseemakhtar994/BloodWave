package com.example.bloodwave;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialDialogs;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateDonerAccountActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    EditText editTextNumber, editTextEmail, editTextName,editTexAddressr;
    RadioGroup radioGroupGender, radioBloodGroup;
    private FirebaseAuth mAuth;
    Spinner blood_spinner;
    Button button3;
    ArrayList data;



    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String User = "User";
    public static final String Phone = "phoneKey";
    public static final String Email = "emailKey";
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.careate_doner_account);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        radioGroupGender = findViewById(R.id.radioGroupGender);
        blood_spinner = findViewById(R.id.blood_spinner);
        editTextNumber = findViewById(R.id.editTextNumber);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTexAddressr = findViewById(R.id.editTexAddressr);
        editTextName = findViewById(R.id.editTextName);
        button3 = findViewById(R.id.button3);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedRadioButtonId = radioGroupGender.getCheckedRadioButtonId();
                if (selectedRadioButtonId != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                    String selectedRbText = selectedRadioButton.getText().toString();
                    Toast.makeText(
                            getApplicationContext(), selectedRbText + " is Selected", Toast.LENGTH_LONG).show();
                } else {
//                    textView.setText("Nothing selected from the radio group");
                }
                subMitData();
            }
        });

        mDatabase.child("doners").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                data = (ArrayList<CreateDonerModel>) dataSnapshot.getValue(Object.class);

                for (DataSnapshot zoneSnapshot : dataSnapshot.getChildren()) {
//                    CreateDonerModel post = dataSnapshot.getValue(CreateDonerModel.class);
                    Log.e("", (String) zoneSnapshot.child("phone").getValue());
                }
                Log.e("", "");
//                Log.i(TAG, dataSnapshot.child("ZNAME").getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("", "");
            }
        });
    }

    public String getValueFromRadioGroup(RadioGroup radioGroupGender) {
        int selectedRadioButtonId = radioGroupGender.getCheckedRadioButtonId();
        String selectedRbText = "";
        if (selectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            selectedRbText = selectedRadioButton.getText().toString();
        }
        return selectedRbText;
    }

    public void subMitData() {
        final String name = editTextName.getText().toString();
        final String email = editTextEmail.getText().toString();
        final String mobile = editTextNumber.getText().toString();
      final  String addrss=editTexAddressr.getText().toString();

        if (validateForm(name, email))
            mAuth.signInAnonymously()
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInAnonymously:success");

                                FirebaseUser user = mAuth.getCurrentUser();

                                String key = mDatabase.child("request").push().getKey();


                                String gender = getValueFromRadioGroup(radioGroupGender);
                                String bloodgroup = blood_spinner.getSelectedItem().toString();
//                            ArrayList  data=new ArrayList<CreateDonerModel>();

                                CreateDonerModel createDonerModel=new CreateDonerModel();

                                createDonerModel.name=name;
                                createDonerModel.email=email;
                                createDonerModel.phone=mobile;
                                createDonerModel.gender=gender;
                                createDonerModel.bloodgroup=bloodgroup;
                                createDonerModel.address=addrss;

                                data.add(createDonerModel);
//                            data.add(new CreateDonerModel("hhh",editTextEmail.getText().toString(),editTextNumber.getText().toString()));
                                mDatabase.child("doners").setValue(data);

                                SharedPreferences.Editor editor = sharedpreferences.edit();

                                String  jsonobject=   new Gson().toJson(new CreateDonerModel(name, email, mobile, gender, bloodgroup));
                                editor.putString(User, jsonobject);

                                editor.commit();


                                new Common().CreatAccount(CreateDonerAccountActivity.this);
                          //                            mDatabase.child("request").updateChildren(childUpdates);

                            } else {
                                // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });


    }

    private boolean validateForm(String name, String email) {
        if (TextUtils.isEmpty(name)) {
            editTextName.setError("Please Enter Name");
            return false;
        } else if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please Enter Email");
            return false;
        } else {
            editTextName.setError(null);
            editTextEmail.setError(null);
            return true;
        }
    }
}
