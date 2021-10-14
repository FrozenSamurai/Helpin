package com.example.helpin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SeekBar;
import android.widget.TextView;

public class Display extends AppCompatActivity {


    private SeekBar sBar;
    private TextView tView,textfontinc;

    int textsize = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        sBar = (SeekBar) findViewById(R.id.seekBar);
        textfontinc=(TextView)findViewById(R.id.textfont);
        tView = (TextView) findViewById(R.id.textviewprogress);
        textfontinc.setTextSize(textsize);
        tView.setText(sBar.getProgress() + "%");
        sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int pval = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textsize=textsize+(progress-pval);
                pval = progress;
                textfontinc.setTextSize(textsize);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //write custom code to on start progress
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tView.setText(pval + "%");
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbardisp_view_prof);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Settings- Display");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_toolbar, menu);
        return true;
    }
}