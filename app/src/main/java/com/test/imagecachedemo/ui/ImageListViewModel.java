package com.test.imagecachedemo.ui;

import androidx.lifecycle.ViewModel;

import com.test.imagecachedemo.data.Photo;
import com.test.imagecachedemo.network.ApiHelper;
import com.test.imagecachedemo.network.GetPhotoService;
import com.test.imagecachedemo.network.MyApiHelper;
import com.test.imagecachedemo.network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageListViewModel extends ViewModel {


    private ImageListNavigator mNavigator;
    private ApiHelper myApiHelper;

    public ImageListViewModel(ImageListNavigator _navigator) {
        this.mNavigator = _navigator;
        myApiHelper = new MyApiHelper();
    }

    public void getPhotoList(int pageNumber) {
        myApiHelper.getPhotos(pageNumber,30).enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                if (mNavigator == null)
                    return;
                if (response.isSuccessful() && response.body() != null) {
                    mNavigator.onPhotoFetched(response.body());
                } else {
                    mNavigator.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                mNavigator.onError(t.getMessage());

            }
        });

    }
}
