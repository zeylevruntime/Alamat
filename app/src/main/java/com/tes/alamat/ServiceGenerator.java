package com.tes.alamat;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ServiceGenerator {

    public static  String Base_Url = Constants.BaseDir.BaseUrl;
    public static  String Base_Url1 = "https://kodepos-2d475.firebaseio.com/";
    public static  String Base_Url2 = Constants.BaseDir.BaseUrl;
    public static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


    public static <S> S createService(Class<S> serviceClass) {
        httpClient.readTimeout(40, TimeUnit.SECONDS);
        httpClient.connectTimeout(40, TimeUnit.SECONDS);
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base_Url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createService1(Class<S> serviceClass,String url) {
        String Url="https://kodepos-2d475.firebaseio.com"+url;
        System.out.println(Url);
        httpClient.readTimeout(40, TimeUnit.SECONDS);
        httpClient.connectTimeout(40, TimeUnit.SECONDS);
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit.create(serviceClass);
    }


}