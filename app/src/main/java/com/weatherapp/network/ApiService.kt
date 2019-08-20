package com.weatherapp.network
import com.weatherapp.BuildConfig
import com.weatherapp.model.ResponseForecast
import com.weatherapp.model.ResponseLocation
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit.Builder
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {


    //?key=82659140320e4d15aad123558191408&q=Paris
    @GET("current.json")
    fun getCurrentWeather(@Query("key") key: String, @Query("q") city: String):
            Single<ResponseLocation>

    @GET("forecast.json")
    fun getForecastWeather(@Query("key") key: String, @Query("q") city: String,@Query("days") days
    : Int):
            Single<ResponseForecast>


    companion object {
        fun provideOkHttpClient(): OkHttpClient {
            val okHttpBuilder = OkHttpClient.Builder()
            okHttpBuilder.addInterceptor(LogInterceptor())
            return okHttpBuilder.build()
        }


       // private val weather_bsae_url = "http://api.apixu.com/v1/"

        //   private val BASE_URL = "http://aonecoderz.com/event/api/"
        fun create(): ApiService {
            val retrofit = Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .client(provideOkHttpClient())
                .build()
            // retrofit.client().interceptors().add(LoggingInterceptor())
            return retrofit.create(ApiService::class.java)
        }
    }


}