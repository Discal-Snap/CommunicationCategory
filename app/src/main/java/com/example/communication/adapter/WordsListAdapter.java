package com.example.communication.adapter;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.communication.R;
import com.example.communication.model.Words;
import com.example.communication.ui.main.MainFragment;

import java.util.List;
import java.util.Locale;

public class WordsListAdapter extends BaseAdapter  {
    private TextToSpeech textToSpeech;

    private List<Words> wordsList;
    private Context context;
    private TextView tv_word;
    private ImageButton btn_speak;

    public WordsListAdapter(List<Words> wordsList, Context context) {
        this.wordsList = wordsList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return wordsList.size();
    }

    @Override
    public Object getItem(int position) {
        return wordsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View view, ViewGroup parent) {
        final Words words=wordsList.get(position);

         View convertView= LayoutInflater.from(context).inflate(R.layout.word_item,parent,false);

        tv_word=convertView.findViewById(R.id.tv_words);
        btn_speak=convertView.findViewById(R.id.imgbtn_speak);
        tv_word.setText(words.getEng());

        /*
        On Click to intent
         */
        tv_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("words",wordsList.get(position).getEng());

                Fragment fragment=MainFragment.newInstance();
                fragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction=((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container,fragment);
                fragmentTransaction.addToBackStack(WordsListAdapter.class.toString());
                fragmentTransaction.commit();
            }
        });

        /*
           On Click to speaking transition
         */
       btn_speak.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

           }
       });

        return convertView;
    }


}
