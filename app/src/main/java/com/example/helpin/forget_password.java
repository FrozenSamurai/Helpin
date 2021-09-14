package com.example.helpin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forget_password extends AppCompatActivity {
private EditText reset_password_email;
private Button reset_password_button;
FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        reset_password_email = (EditText)findViewById(R.id.email_rest_password);
        reset_password_button = (Button)findViewById(R.id.reset_password);
        auth = FirebaseAuth.getInstance();
        reset_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String email = reset_password_email.getText().toString().trim();

        if(email.isEmpty())
        {
            reset_password_email.setError("Fill the email");
            reset_password_email.requestFocus();
            return ;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            reset_password_email.setError("Enter valid email");
            reset_password_email.requestFocus();
            return;
        }
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                     @Override
                                                                     public void onComplete(@NonNull  Task<Void> task) {
                                                                         if(task.isSuccessful()){
                                                                             Toast.makeText(forget_password.this, "Check your email to reset your password!", Toast.LENGTH_SHORT).show();
                                                                         } else {
                                                                             Toast.makeText(forget_password.this, "Try again! Something went wrong ", Toast.LENGTH_SHORT).show();
                                                                         }
                                                                     }
                                                                 }
        );

    }
}