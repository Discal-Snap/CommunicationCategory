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
import android.widget.TextView;
import android.widget.Toast;

import com.example.communication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainFragment extends Fragment {

    private TextView textToSpeak;
    private TextView speed;
    private Button speak;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    private static final String TAG = "MainFragment";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.main_fragment, container, false);

        textToSpeak=view.findViewById(R.id.tv_textToSpeak);
        speed=view.findViewById(R.id.tv_speed);
        speak=view.findViewById(R.id.btn_speak);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle=this.getArguments();
        Log.d(TAG, "onActivityCreated: :::::::::"+bundle);

        if (bundle != null){
            textToSpeak.setText(bundle.getString("words"));
        }

    }

}
