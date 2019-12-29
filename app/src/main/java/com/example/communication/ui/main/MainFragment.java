package com.example.communication.ui.main;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.communication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainFragment extends Fragment implements TextToSpeech.OnInitListener {

    private MainViewModel mViewModel;
    private TextToSpeech textToSpeech;
    private EditText textToSpeak;
    private Button speak;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.main_fragment, container, false);
        textToSpeak=view.findViewById(R.id.ed_textToSpeak);
        speak=view.findViewById(R.id.btn_speak);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel

        textToSpeech=new TextToSpeech(getContext(),this);
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TextToSpeechFunction();
                Locale[] locales=Locale.getAvailableLocales();
                List<Locale> localeList= new ArrayList<Locale>();
                for (Locale localeto : locales){
                    int res=textToSpeech.isLanguageAvailable(localeto);
                    if (res == TextToSpeech.LANG_COUNTRY_AVAILABLE){
                        localeList.add(localeto);
                    }
                }
                for (Locale lo: localeList
                     ) {Log.e("availabile Language",lo.getDisplayLanguage(lo));

                }
            }
        });
    }

    public void TextToSpeechFunction(){
        String string=textToSpeak.getText().toString();
        int speechStatus = textToSpeech.speak(string,TextToSpeech.QUEUE_FLUSH,null);
        if (speechStatus == TextToSpeech.ERROR){
            Log.e("TTS","Error converting text to speech");
        }

    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS){
            int result=textToSpeech.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(getContext(),"Language not supported",Toast.LENGTH_SHORT).show();
            }else {
                TextToSpeechFunction();
            }
        }else {
            Toast.makeText(getContext(),"Initialization Failed",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
