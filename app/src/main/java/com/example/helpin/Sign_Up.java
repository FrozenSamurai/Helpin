package com.example.helpin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Sign_Up extends AppCompatActivity implements View.OnClickListener {
    // All buttons, editText declared below
    private Button signup_done;
    private EditText email, password;
    private String email_get, password_get;
    private TextView forget_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);

        signup_done = (Button) findViewById(R.id.signup_done);
        signup_done.setOnClickListener(this);

        email = (EditText) findViewById(R.id.email_editText);
        password = (EditText) findViewById(R.id.password_editText);

        forget_password = (TextView) findViewById(R.id.forget_password);
        forget_password.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup_done:
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
    }
}