package com.test.imagecachedemo.network;

import com.test.imagecachedemo.data.Photo;

import java.util.List;

import retrofit2.Call;

public interface ApiHelper {

    Call<List<Photo>> getPhotos(int pageNumber,int size);
}
