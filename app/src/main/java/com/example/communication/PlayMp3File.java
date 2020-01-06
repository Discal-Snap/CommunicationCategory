package com.example.communication;

import android.app.Activity;

import android.media.AudioManager;
import android.media.MediaPlayer;

import android.media.PlaybackParams;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.IOException;
public class PlayMp3File{

    public static PlayMp3File newInstance() {
        return new PlayMp3File();
    }

    private static final String TAG = "PlayMp3File";

//    public static final String url="http://192.168.8.103/videos/naykaunglr.m4a";
//    String writtenToDisk=null;
//
//    public String downloadFile(String url, final int position){
//
//        Retrofit.Builder builder=new Retrofit.Builder().baseUrl("http://192.168.8.103");
//        Retrofit retrofit=builder.build();
//
//        FileDownloadClient downloadService = retrofit.create(FileDownloadClient.class);
//
//        Call<ResponseBody> call = downloadService.downloadFile(url);
//
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
//                if (response.isSuccessful()) {
//                    Log.d(TAG, "server contacted and has file");
//
//                    new AsyncTask<Void, Void, String>() {
//                        @Override
//                        protected String doInBackground(Void... voids) {
//                            writtenToDisk = writeResponseBodyToDisk( response.body());
//
//                            Log.d(TAG, "file download was a success? " + writtenToDisk);
//                            return writtenToDisk;
//                        }
//
//                        @Override
//                        protected void onPostExecute(String aVoid) {
//                            super.onPostExecute(aVoid);
//                            playMp3(writtenToDisk);
//                            List<Words> wordsList=new WordsListAdapter().wordsList;
//                            int result= DatabaseAccess.getInstance(newInstance()).update(wordsList.get(position).getwID(),aVoid);
//                            Log.d(TAG, "onPostExecute: UPDATE Database:::::::::::"+result+"===="+wordsList.get(position).getFile_location());
//
//                        }
//                    }.execute();
//                } else {
//                    Log.d(TAG, "server contact failed  :  "+response.errorBody()+"_____"+response.body());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.e(TAG, "error");
//            }
//        });
//
//        return writtenToDisk;
//    }
//
//    public String writeResponseBodyToDisk(ResponseBody body) {
//        try {
//            // todo change the file location/name according to your needs
//            File futureStudioFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/"+"Commu");
//            if (!futureStudioFile.exists()){
//                futureStudioFile.mkdir();
//            }
//
//            File futureStudioIconFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator+ "Commu" + File.separator+"nykaunglr.mp3");
//
//
//            InputStream inputStream = null;
//            OutputStream outputStream = null;
//
//            try {
//                byte[] fileReader = new byte[4096];
//
//                long fileSize = body.contentLength();
//                long fileSizeDownloaded = 0;
//
//                inputStream = body.byteStream();
//                outputStream = new FileOutputStream(futureStudioIconFile);
//
//                while (true) {
//                    int read = inputStream.read(fileReader);
//
//                    if (read == -1) {
//                        break;
//                    }
//
//                    outputStream.write(fileReader, 0, read);
//
//                    fileSizeDownloaded += read;
//
//                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
//                }
//
//                outputStream.flush();
//
//                return futureStudioIconFile.getPath();
//            } catch (IOException e) {
//                return "false";
//            } finally {
//                if (inputStream != null) {
//                    inputStream.close();
//                }
//
//                if (outputStream != null) {
//                    outputStream.close();
//                }
//            }
//        } catch (IOException e) {
//            return "false";
//        }
//    }

    public void playMp3(String location) throws IOException {

        if (location != null) {
            Log.d(TAG, "playMp3: ::::::::::::::::::::::::" + location);

                MediaPlayer mPlayer = new MediaPlayer();
                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mPlayer.setDataSource(location);
                mPlayer.prepare();
                mPlayer.start();
            }
        }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void playMp3(String location, float speed, float pitch) throws IOException {

        if (location != null) {
            Log.d(TAG, "playMp3: ::::::::::::::::::::::::" + location);


            MediaPlayer mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setDataSource(location);

            PlaybackParams playbackParams = new PlaybackParams();
            playbackParams.setSpeed(speed);
            playbackParams.setPitch(pitch);
            playbackParams.setAudioFallbackMode(PlaybackParams.AUDIO_FALLBACK_MODE_DEFAULT);
            mPlayer.setPlaybackParams(playbackParams);

            mPlayer.prepare();
            mPlayer.start();
        }
    }
    }


