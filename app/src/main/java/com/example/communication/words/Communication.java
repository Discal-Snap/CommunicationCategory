package com.example.communication.words;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.communication.R;
import com.example.communication.adapter.WordsListAdapter;
import com.example.communication.database_client.DatabaseAccess;
import com.example.communication.model.Words;

import java.util.List;

public class Communication extends Fragment implements View.OnClickListener {


    private SearchView searchView;
    private Button simple,food;
    private ListView lvshowWrod;
    public List<Words> wordsList;
    private WordsListAdapter wordsListAdapter;

    public static Communication newInstance() {
        return new Communication();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_communication, container, false);
        lvshowWrod=view.findViewById(R.id.lv_words);
        searchView=view.findViewById(R.id.search_view);
        simple=view.findViewById(R.id.btn_simple);
        food=view.findViewById(R.id.btn_food);

        simple.setOnClickListener(this);
        food.setOnClickListener(this);

        searchFun();

        return view;
    }
    /*
    Searching Word Form database_client
     */
    private void searchFun(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                wordsList=getAllWord(query);
                SetAdapterView(wordsList);

              return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                wordsList=getAllWord(newText);
                SetAdapterView(wordsList);

                return false;
            }
        });
    }

    /*
    get Each Word List in database_client
     */
    public List<Words> getAllWord(String string){
        return DatabaseAccess.getInstance(getContext()).getAllWord(string);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_simple: wordsList=getAllWord("simple");break;
            case R.id.btn_food: wordsList=getAllWord("food");break;
        }
        SetAdapterView(wordsList);
    }


    @Override
    public void onStart() {
        super.onStart();
        wordsList=getAllWord("");
        SetAdapterView(wordsList);
    }
    /*
  Set Adapter in ListView
   */
    public void SetAdapterView(List<Words> wordsList){
        wordsListAdapter=new WordsListAdapter(wordsList,getContext());
        lvshowWrod.setAdapter(wordsListAdapter);

    }

}
