package com.test.imagecachedemo.network;

import com.test.imagecachedemo.data.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface GetPhotoService {

    @Headers("user-Accept-Version: v1")
    @GET("/photos")
    Call<List<Photo>> getPhotos(@Query("page") int page, @Query("per_page") int total);
    //Call<List<Photo>> getPhotos(@Query("client_id") String clientId);
}
