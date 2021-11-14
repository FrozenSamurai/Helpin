package com.example.helpin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ViewProfile extends AppCompatActivity {
    Button edit_prof;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    String userid,getemail,getfname,getlname,getdob;
    EditText email_view, fname_view, last_name_view, dob_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        Toolbar toolbar = findViewById(R.id.toolbardisp_view_prof);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Your Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        edit_prof=(Button)findViewById(R.id.Edit_profile_bt);

        email_view=(EditText)findViewById(R.id.email_view_profile_view_edit);
        fname_view=(EditText)findViewById(R.id.first_name_edit_view_profile);
        last_name_view=(EditText)findViewById(R.id.last_name_edit_view_profile);
        dob_view=(EditText)findViewById(R.id.birth_date_edit_view_profile);

        email_view.setEnabled(false);
        fname_view.setEnabled(false);
        last_name_view.setEnabled(false);
        dob_view.setEnabled(false);

        firebaseAuth= FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        userid=firebaseAuth.getCurrentUser().getUid();

        DocumentReference documentReference= firestore.collection("Users").document(userid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                email_view.setText(value.getString("Email"));
                fname_view.setText(value.getString("First Name"));
                last_name_view.setText(value.getString("Last Name"));
                dob_view.setText(value.getString("Date Of Birth"));


            }
        });


        edit_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getemail=email_view.getText().toString();
                getfname=fname_view.getText().toString();
                getlname=last_name_view.getText().toString();
                getdob=dob_view.getText().toString();

                Intent intent = new Intent(ViewProfile.this, profile_info.class);
                intent.putExtra("ed_email", getemail);
                intent.putExtra("ed_fname", getfname);
                intent.putExtra("ed_lname", getlname);
                intent.putExtra("ed_dob", getdob);

                startActivity(intent);
            }
        });
    }
}