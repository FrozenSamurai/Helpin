package com.example.helpin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registeration extends AppCompatActivity {

    EditText reg_email,reg_pass;

    Button register_data;

    String email,password,userid;

    String First_Name= "Not Added";
    String Last_Name= "Not Added";
    String DOB= "Not Added";

    FirebaseFirestore firestore;

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        fAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

        if(fAuth.getCurrentUser() != null){
            Toast.makeText(this, "Already Registered", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        reg_email=(EditText)findViewById(R.id.email_reg);
        reg_pass=(EditText)findViewById(R.id.password_reg);
        register_data=(Button)findViewById(R.id.register_data);

        register_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = reg_email.getText().toString().trim();
                password = reg_pass.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    reg_email.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    reg_pass.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    reg_pass.setError("Password Must be >= 6 Characters");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            userid=fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference=firestore.collection("Users").document(userid);
                            Map<String,Object> user= new HashMap<>();
                            user.put("Email",email);
                            //user.put("Password",password);
                            user.put("First Name",First_Name);
                            user.put("Last Name",Last_Name);
                            user.put("Date Of Birth",DOB);

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Registeration.this, "Successful", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Registeration.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),Dashboard.class));
                        }
                    }
                });
            }
        });





    }
}