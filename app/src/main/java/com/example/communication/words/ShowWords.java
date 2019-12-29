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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.communication.R;
import com.example.communication.adapter.WordsListAdapter;
import com.example.communication.database.DatabaseOpenHelper;
import com.example.communication.model.Words;

import java.util.ArrayList;
import java.util.List;

public class ShowWords extends Fragment implements View.OnClickListener {

    private SQLiteDatabase database;
    private SQLiteOpenHelper DBHelper;

    private SearchView searchView;
    private Button simple,food;
    private ListView lvshowWrod;
    public List<Words> wordsList;
    private WordsListAdapter wordsListAdapter;

    public static ShowWords newInstance() {
        return new ShowWords();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_showwords, container, false);
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
    Searching Word Form database
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
    ItemClickListener of ListView
     */
    public void ListItemClickFun(){
        lvshowWrod.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),parent.getItemIdAtPosition(position)+"",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
    Set Adapter in ListView
     */
    public void SetAdapterView(List<Words> wordsList){
        wordsListAdapter=new WordsListAdapter(wordsList,getContext());
        lvshowWrod.setAdapter(wordsListAdapter);
    }

    /*
    get Each Word List in database
     */
    public List<Words> getAllWord(String string){
        Words words=null;
        List<Words> wordsList=new ArrayList<>();
        DBHelper=new DatabaseOpenHelper(getContext());
        database=DBHelper.getWritableDatabase();
        Cursor cursor=database.rawQuery("select * from EngMyn where category like "+"'%"+string+"%'",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            words=new Words(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
            wordsList.add(words);
            cursor.moveToNext();
        }
        cursor.close();
        database.close();

        return wordsList;
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

}
