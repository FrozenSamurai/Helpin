package com.example.helpin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class profile_info extends AppCompatActivity {
    EditText email_edit, fname_edit, lname_edit, dob_edit;

    String get_email,get_fname,get_lname,get_dob;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    Button save_profile;

    String userid;

    String up_email,up_fname,up_lname,up_dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);

        firebaseAuth= FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        userid=firebaseAuth.getCurrentUser().getUid(); //gets the useruid

        email_edit=(EditText)findViewById(R.id.email_view_profile_view_edit);
        fname_edit=(EditText)findViewById(R.id.first_name_edit_view_profile);
        lname_edit=(EditText)findViewById(R.id.last_name_edit_view_profile);
        dob_edit=(EditText)findViewById(R.id.birth_date_edit_view_profile);
        save_profile=(Button)findViewById(R.id.Edit_profile_bt);

        email_edit.setEnabled(false);

        Bundle bundle = getIntent().getExtras();

        get_email=bundle.getString("ed_email");
        get_fname=bundle.getString("ed_fname");
        get_lname=bundle.getString("ed_lname");
        get_dob=bundle.getString("ed_dob");

        email_edit.setText(get_email);
        fname_edit.setText(get_fname);
        lname_edit.setText(get_lname);
        dob_edit.setText(get_dob);


        Toolbar toolbar = findViewById(R.id.toolbardisp_view_prof);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        save_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                up_email=email_edit.getText().toString();
                up_fname=fname_edit.getText().toString();
                up_lname=lname_edit.getText().toString();
                up_dob=dob_edit.getText().toString();

                DocumentReference documentReference=firestore.collection("Users").document(userid);
                Map<String,Object> user= new HashMap<>();
                user.put("Email",up_email);
                user.put("First Name",up_fname);
                user.put("Last Name",up_lname);
                user.put("Date Of Birth",up_dob);

                documentReference.set(user);




            }
        });


    }
}