package com.example.helpin;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Sign_Up extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "s";
    // All buttons, editText declared below
    private Button signup_done;
    private EditText email, password;
    private String email_get, password_get;
    private TextView forget_password;
    private ImageView googleIcon;
    private final static  int RC_SIGN_IN = 123;
    FirebaseAuth fAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);

//        requestGoogle();
        signup_done = (Button) findViewById(R.id.register_data);
        signup_done.setOnClickListener(this);

        email = (EditText) findViewById(R.id.email_reg);
        password = (EditText) findViewById(R.id.password_reg);

        forget_password = (TextView) findViewById(R.id.forget_password);
        forget_password.setOnClickListener(this);

//        googleIcon = (ImageView)findViewById(R.id.google);
//        googleIcon.setOnClickListener(this);
        mAuth=FirebaseAuth.getInstance();
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
//            case R.id.google:
//                googleSign();
//                break;

        }
    }

//    private void googleSign() {
//
//        resultLauncher.launch(new Intent(mGoogleSignInClient.getSignInIntent()));
//    }



//    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//        @Override
//        public void onActivityResult(ActivityResult result) {
//            if(result.getResultCode() == Activity.RESULT_OK){
//                Intent intent = result.getData();
//
//                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
//                try {
//                    // Google Sign In was successful, authenticate with Firebase
//                    GoogleSignInAccount account = task.getResult(ApiException.class);
//
//                    firebaseAuthWithGoogle(account.getIdToken());
//                } catch (ApiException e) {
//                    // Google Sign In failed, update UI appropriately
//
//                }
//            }
//
//        }
//    });









//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//
//        }
//    }

//    private void firebaseAuthWithGoogle(String idToken) {
//        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            startActivity(new Intent(Sign_Up.this,Dashboard.class));
//
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Toast.makeText(Sign_Up.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                });
//    }

//    private void requestGoogle() {
//        // Configure Google Sign In
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//    }

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