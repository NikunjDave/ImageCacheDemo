package com.test.imagecachedemo.network;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static final String client_id = "7f1d9ba41429bcc980b456fec4fabc32d49b3b6e1ffffd5ce2391e76038d35c8";
    private static final String BASE_URL = "http://api.unsplash.com";
    private static Retrofit retrofit;


    public static Retrofit getRetrofitInstance() {

        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


    private static OkHttpClient getClient(){
        Interceptor clientInterceptor = new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url().newBuilder().addQueryParameter("client_id",client_id).build();
                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        };
        return new OkHttpClient.Builder().addNetworkInterceptor(clientInterceptor).build();
    }

}
