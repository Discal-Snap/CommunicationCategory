package com.example.communication.database_client;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface FileDownloadClient {

    @GET("videos/Screenshot1.png")
    Call<ResponseBody> downloadFileFixedUrl();

    @GET
    @Streaming
    Call<ResponseBody> downloadFile(@Url String fileUrl);
}
