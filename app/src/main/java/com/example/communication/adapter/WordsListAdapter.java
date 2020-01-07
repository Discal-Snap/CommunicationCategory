package com.example.communication.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.communication.PlayMp3File;
import com.example.communication.R;
import com.example.communication.database_client.DatabaseAccess;
import com.example.communication.database_client.FileDownloadClient;
import com.example.communication.model.Words;
import com.example.communication.ui.main.MainFragment;
import com.example.communication.words.Communication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WordsListAdapter extends BaseAdapter{
    private static final String TAG = "WordsListAdapter";

    public List<Words> wordsList;
    public Context context;
    private TextView tv_word;
    private ImageButton btn_speak;

    public static final String url="http://192.168.8.103/videos/";
    public String writtenToDisk=null;

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
    public View getView(final int position, final View view, ViewGroup parent) {
        final Words words=wordsList.get(position);

         View convertView= LayoutInflater.from(context).inflate(R.layout.word_item,parent,false);

        tv_word=convertView.findViewById(R.id.tv_words);
        btn_speak=convertView.findViewById(R.id.imgbtn_speak);
        btn_speak.setFocusable(false);
        tv_word.setText(words.getEng());

        /*
           On Click to speaking transition
         */
       btn_speak.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (wordsList.get(position).getFile_location() == null){
                   Log.d("DownLoad File", "onClick: :::::::::::::"+wordsList.get(position).getFile_location());
                   final String  Murl=url+wordsList.get(position).getFile_name();
                   Log.w(TAG, "onClick: URL location:::::::::::::::::"+Murl);

                   downloadFile(Murl,position);

               }else {
                   String file=wordsList.get(position).getFile_location();
                   Log.d("DownLoad File", "onClick: ::::::::::::;;;;;;;;;;;;;;:"+file);
                       try {
                           PlayMp3File.newInstance().playMp3(file);
                       }catch (IOException e){
                           e.printStackTrace();

                           String Murl=url+wordsList.get(position).getFile_name();
                           Log.w(TAG, "onClick: URL location:::::::::::::::::"+Murl);
                           downloadFile(Murl,position);


                       }

               }
           }
       });

        return convertView;
    }

    /*
    DownLoad file from Server and store file location
     */

    public void downloadFile(String url, final int position){

        Retrofit.Builder builder=new Retrofit.Builder().baseUrl("http://192.168.8.103");
        Retrofit retrofit=builder.build();

        FileDownloadClient downloadService = retrofit.create(FileDownloadClient.class);

        Call<ResponseBody> call = downloadService.downloadFile(url);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "server contacted and has file");

                    new AsyncTask<Void, Void, String>() {
                        @Override
                        protected String doInBackground(Void... voids) {
                             writtenToDisk = writeResponseBodyToDisk( response.body(),position);
                            Log.d(TAG, "file download was a success? " + writtenToDisk);


                            return writtenToDisk;
                        }

                        @Override
                        protected void onPostExecute(String aVoid) {
                            super.onPostExecute(aVoid);

                            //store file location to database

                            int result= DatabaseAccess.getInstance(context).update(wordsList.get(position).getwID(),aVoid);

                            Log.d(TAG, "onPostExecute: UPDATE Database:::::::::::"+wordsList.get(position).getFile_location());

                            //Communication.newInstance().wordsListAdapter.notifyDataSetChanged();
                            //Play Mp3 file
                            String file=wordsList.get(position).getFile_location();
                            Log.d("DownLoad File", "onClick: ::::::::::::;;;;;;;;;;;;;;:"+file);
                            try {
                                PlayMp3File.newInstance().playMp3(file);
                            }catch (IOException e) {
                                e.printStackTrace();
                            }


                        }
                    }.execute();
                } else {
                    Log.d(TAG, "server contact failed  :  "+response.errorBody()+"_____"+response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "error");
            }
        });

    }

    /*
    Write Network file to Local File
     */

    public String writeResponseBodyToDisk(ResponseBody body,int position) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/"+"Commu");
            if (!futureStudioFile.exists()){
                futureStudioFile.mkdir();
            }

            File futureStudioIconFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator+ "Commu" + File.separator+wordsList.get(position).getFile_name());


            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return futureStudioIconFile.getPath();
            } catch (IOException e) {
                return "false";
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return "false";
        }
    }

}
