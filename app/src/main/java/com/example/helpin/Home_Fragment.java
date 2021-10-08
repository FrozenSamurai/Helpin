package com.example.helpin;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsService;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Locale;

public class Home_Fragment extends Fragment implements TextToSpeech.OnInitListener {

    private EditText multi;
    private TextView speech_edit;
    private Button speak,clear,stop;
    private TextToSpeech textToSpeech;
    private SeekBar speed;
    private ImageButton mic;



    @Override
    public void onDestroy() {
        if(textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        textToSpeech = new TextToSpeech(getActivity(),this);
        multi = (EditText)view.findViewById(R.id.text_to_speech);
        speak = (Button)view.findViewById(R.id.speak_for_text);
        clear = (Button)view.findViewById(R.id.clear_for_text);
        stop = (Button)view.findViewById(R.id.stop_for_text);
        speed = (SeekBar) view.findViewById(R.id.seek_bar_speed);
        speech_edit = (TextView)view.findViewById(R.id.speech_text);
        mic = (ImageButton)view.findViewById(R.id.mic_off);



        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speech_edit.setText("");
//                mic.setBackgroundResource(R.drawable.ic_baseline_mic_24);
                Intent record = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//                record.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,"en-US");
                record.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                record.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                record.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");
                try {
                    startActivityForResult(record,10001);
                } catch(Exception e) {
                    Toast.makeText(getActivity(), "Speech Recognizer now work", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.RECORD_AUDIO},1);
//        }
//         SPPECH TO TEXT ( onRequestPermissionResult depricated) issue

//        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//            if(requestCode == 1){
//                if(grantResults[0]==PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
//                }
//            }
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }

        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech = new TextToSpeech(getActivity().getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {

                        if(status == TextToSpeech.SUCCESS){
                            Locale locale = new Locale("en","IN");
                            textToSpeech.setLanguage(locale);
                            float speedRange = (float) speed.getProgress() / 50;
                            if(speedRange < 0.1) speedRange = 0.1f;
                            textToSpeech.setSpeechRate(speedRange);
                            textToSpeech.speak(multi.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
                        }

                    }
                });
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multi.setText("");
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textToSpeech.isSpeaking()) {
                    textToSpeech.stop();
                    textToSpeech.shutdown();
                } else {
                    Toast.makeText(getActivity(), "Not Speaking", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10001){
            if(resultCode == Activity.RESULT_OK   && data != null){
                ArrayList<String> list = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                speech_edit.setText(list.get(0));
            }
        }
    }

    @Override
    public void onPause() {
        if(textToSpeech.isSpeaking() || textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }


    @Override
    public void onInit(int status) {

    }
}


