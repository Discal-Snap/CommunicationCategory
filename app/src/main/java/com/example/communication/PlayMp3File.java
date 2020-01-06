package com.example.communication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PlayMp3File extends Fragment  {

    public static PlayMp3File newInstance() {
        return new PlayMp3File();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_example, container, false);

        return view;
    }

}
