package com.test.imagecachedemo.ui;

import com.test.imagecachedemo.data.Photo;

import java.util.List;

public interface ImageListNavigator {

    void onError(String message);
    void onPhotoFetched(List<Photo> photoList);


}
