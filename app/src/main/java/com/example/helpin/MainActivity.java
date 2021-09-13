package com.example.helpin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

private Button sign_in, sign_up;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Main Screen SignUp button
        sign_in = (Button) findViewById(R.id.sign_in);
        sign_in.setOnClickListener(this);

        sign_up=(Button)findViewById(R.id.bt_signup);
        sign_up.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in:
                startActivity(new Intent(MainActivity.this,Sign_Up.class));
                break;
            case R.id.bt_signup:
                startActivity(new Intent(MainActivity.this,Registeration.class));
                break;
        }
    }
}