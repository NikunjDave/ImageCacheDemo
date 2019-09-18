package com.test.imagecachedemo.network;

import com.test.imagecachedemo.data.Photo;

import java.util.List;

import retrofit2.Call;

public class MyApiHelper implements ApiHelper {

    private GetPhotoService mService;
    public MyApiHelper(){
        if(mService == null){
            mService = RetrofitClientInstance.getRetrofitInstance().create(GetPhotoService.class);
        }
    }

    @Override
    public Call<List<Photo>> getPhotos(int pageNumber, int size) {
        return mService.getPhotos(pageNumber,size);
    }
}
