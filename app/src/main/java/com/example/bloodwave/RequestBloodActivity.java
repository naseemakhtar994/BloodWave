package com.example.bloodwave;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class RequestBloodActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    EditText editTextNumber, editTextEmail, editTextName,editTexAddressr;
    RadioGroup radioGroupGender, radioBloodGroup;
    private FirebaseAuth mAuth;
    Spinner spinnerBloodGroup;
    Button button3;
    ArrayList data;

    private int year, month, day;


    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_blood);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        data=new ArrayList();
        radioGroupGender = findViewById(R.id.radioGroupGender);
        spinnerBloodGroup = findViewById(R.id.spinnerBloodGroup);
        editTexAddressr = findViewById(R.id.editTexAddressr);
        editTextNumber = findViewById(R.id.editTextNumber);
        dateView = findViewById(R.id.dateView);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextName = findViewById(R.id.editTextName);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);

        mDatabase.child("requests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                data = (ArrayList<CreateDonerModel>) dataSnapshot.getValue(Object.class);

                for (DataSnapshot zoneSnapshot : dataSnapshot.getChildren()) {
                    Log.e("", (String) zoneSnapshot.child("phone").getValue());
                }
                Log.e("", "");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
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

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
//        Toast.makeText(getApplicationContext(), "ca",
//                Toast.LENGTH_SHORT)
//                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    public void subMitData() {
        final String name = editTextName.getText().toString();
        final String email = editTextEmail.getText().toString();
        final String mobile = editTextNumber.getText().toString();
        final String userEmail=getIntent().getStringExtra("myemail");
        final  String addrss=editTexAddressr.getText().toString();

        if (validateForm(name, email))
            if (mAuth.getCurrentUser() == null) {
                mAuth.signInAnonymously()
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {


                                    FirebaseUser user = mAuth.getCurrentUser();

                                    String key = mDatabase.child("request").push().getKey();


                                    String bloodgroup = spinnerBloodGroup.getSelectedItem().toString();



                                    String date = dateView.getText().toString();
                                    if(data==null){
                                        data=new ArrayList();
                                    }


                                    CreateDonerModel createDonerModel=new CreateDonerModel();

                                    createDonerModel.name=name;
                                    createDonerModel.email=email;
                                    createDonerModel.phone=mobile;
                                    createDonerModel.gender="gender";
                                    createDonerModel.bloodgroup=bloodgroup;
                                    createDonerModel.requesterEmail=userEmail;
                                    createDonerModel.date=date;
                                    createDonerModel.address=addrss;

                                    data.add(createDonerModel);

//                                    data.add(new CreateDonerModel(name, email, mobile, "gender", bloodgroup, date,userEmail));
                                    mDatabase.child("requests").setValue(data);
                                    new Common().showDialog(RequestBloodActivity.this,"Blood Request Submit Sucessfully");


                                } else {

                                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            } else {
                String bloodgroup = spinnerBloodGroup.getSelectedItem().toString();
                String date = dateView.getText().toString();

//                            ArrayList  data=new ArrayList<CreateDonerModel>();

                if(data==null){
                    data=new ArrayList();
                }
                CreateDonerModel createDonerModel=new CreateDonerModel();

                createDonerModel.name=name;
                createDonerModel.email=email;
                createDonerModel.phone=mobile;
                createDonerModel.gender="gender";
                createDonerModel.bloodgroup=bloodgroup;
                createDonerModel.requesterEmail=userEmail;
                createDonerModel.date=date;
                createDonerModel.address=addrss;

                data.add(createDonerModel);

//                data.add(new CreateDonerModel(name, email, mobile, "gender", bloodgroup, date,userEmail));
//                            data.add(new CreateDonerModel("hhh",editTextEmail.getText().toString(),editTextNumber.getText().toString()));
                mDatabase.child("requests").setValue(data);
                new Common().showDialog(RequestBloodActivity.this,"Blood Request Submit Sucessfully");

            }


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

    public void SubmitData(View view) {
        subMitData();
    }
}
