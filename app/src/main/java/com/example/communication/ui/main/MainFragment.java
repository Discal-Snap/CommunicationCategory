package com.example.communication.ui.main;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.communication.PlayMp3File;
import com.example.communication.R;
import com.example.communication.database_client.DatabaseAccess;

import java.io.IOException;

public class MainFragment extends Fragment implements View.OnClickListener {

    private TextView textToSpeak;
    private TextView speed;
    private Button speak;
    private SeekBar controlspeech,controlpitch;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    private static final String TAG = "MainFragment";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.main_fragment, container, false);

        controlspeech=view.findViewById(R.id.seekbar_controlspeech);
        controlpitch=view.findViewById(R.id.seekbar_controlpitch);
        textToSpeak=view.findViewById(R.id.tv_textToSpeak);
        speed=view.findViewById(R.id.tv_speed);
        speak=view.findViewById(R.id.btn_speak);

        speak.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle=this.getArguments();
        Log.d(TAG, "onActivityCreated: :::::::::"+bundle);

        if (bundle != null){
            textToSpeak.setText(bundle.getString("Engwords"));
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: Text For Speaking :::::::"+textToSpeak.getText());
     switch (v.getId()){
         case R.id.btn_speak:
             String file= DatabaseAccess.getInstance(getContext()).getFileLocation(textToSpeak.getText().toString());
             Log.d(TAG, "onClick: FileLocation;::::::::::::::"+file);
             if (file != null){
                 try {
                     float speed= (float) (controlspeech.getProgress()*0.01);
                     float pitch= (float) (controlpitch.getProgress()*0.01);
                     Log.d(TAG, "onClick: Speed:::::::::"+speed+"  pitch::::::::"+pitch);
                     PlayMp3File.newInstance().playMp3(file,speed,pitch);
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
             break;
     }
    }



}
