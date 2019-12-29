package com.example.communication.adapter;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.communication.R;
import com.example.communication.model.Words;

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
    public Words getItem(int position) {
        return wordsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Words words=wordsList.get(position);

        convertView= LayoutInflater.from(context).inflate(R.layout.word_item,parent,false);
        tv_word=convertView.findViewById(R.id.tv_words);
        btn_speak=convertView.findViewById(R.id.imgbtn_speak);
        tv_word.setText(words.getEng());

        /*
        Text To Speech Initialization
         */
        textToSpeech=new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    int result=textToSpeech.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        //Toast.makeText(context,"Language not supported",Toast.LENGTH_SHORT).show();
                    }else {
                        //Toast.makeText(context,"Language supported",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(context,"Initialization Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*
           On Click
         */
       btn_speak.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               TextToSpeechFunction(words.getMyn().toString());
           }
       });

       /*
       ListViewItem Click
        */


        return convertView;
    }

    public void TextToSpeechFunction(String string){
        int speechStatus = textToSpeech.speak(string,TextToSpeech.QUEUE_FLUSH,null);
        if (speechStatus == TextToSpeech.ERROR){
            Log.e("TTS","Error converting text to speech");
        }

    }

}
