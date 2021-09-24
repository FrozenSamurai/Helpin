package com.example.helpin;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Locale;

public class Home_Fragment extends Fragment {

    private EditText multi;
    private Button speak;
    private TextToSpeech textToSpeech;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);


        multi = (EditText)view.findViewById(R.id.text_to_speech);
        speak = (Button)view.findViewById(R.id.speak_for_text);
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech = new TextToSpeech(getActivity().getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if(status == TextToSpeech.SUCCESS){
                            textToSpeech.setLanguage(Locale.US);
                            textToSpeech.setSpeechRate(1.0f);
                            textToSpeech.speak(multi.getText().toString(),TextToSpeech.QUEUE_ADD,null);
                        }
                    }
                });
            }
        });

        return view;
    }
}
