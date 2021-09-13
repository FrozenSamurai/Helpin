package com.example.helpin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sign_Up extends AppCompatActivity implements View.OnClickListener {
    // All buttons, editText declared below
    private Button signup_done;
    private EditText email, password;
    private String email_get, password_get;
    private TextView forget_password;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);

        signup_done = (Button) findViewById(R.id.register_data);
        signup_done.setOnClickListener(this);

        email = (EditText) findViewById(R.id.email_reg);
        password = (EditText) findViewById(R.id.password_reg);

        forget_password = (TextView) findViewById(R.id.forget_password);
        forget_password.setOnClickListener(this);

        fAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_data:
                userSignUp();
                break;
            case R.id.forget_password:
                startActivity(new Intent(Sign_Up.this,forget_password.class));
                break;

        }
    }

    private void userSignUp() {

        email_get = email.getText().toString().trim();
        password_get = password.getText().toString().trim();

        if (email_get.isEmpty()) {
            email.setError("Email required");
            email.requestFocus();
            return;
        }
        if (password_get.isEmpty()) {
            password.setError("Password Required");
            password.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email_get).matches()) {
            email.setError("Enter Valid email");
            email.requestFocus();
            return;
        }
        if (password_get.length() < 6) {
            password.setError("Length short");
            password.requestFocus();
            return;
        }

        fAuth.signInWithEmailAndPassword(email_get,password_get).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Sign_Up.this, " Logged in Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),Dashboard.class));
                }
                else {
                    Toast.makeText(Sign_Up.this, "You are not REGISTERED!, Please do register in our APP first ", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}