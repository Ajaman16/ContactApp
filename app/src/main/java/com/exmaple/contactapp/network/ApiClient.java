package com.exmaple.contactapp.network;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    static private ApiInterface authorizedApiInterface;
    static private ApiInterface unauthorizedApiInterface;

    public static ApiInterface getAuthorizedApiInterface(){
        if(authorizedApiInterface ==null){

            Retrofit retrofit= new Retrofit.Builder().baseUrl("url")
                    .client(new OkHttpClient.Builder().addInterceptor(new MyInterceptor()).build())
                    .addConverterFactory(GsonConverterFactory.create()).build();
            authorizedApiInterface =retrofit.create(ApiInterface.class);
        }
        return authorizedApiInterface;
    }

    public static ApiInterface getUnauthorizedApiInterface(){
        if(unauthorizedApiInterface ==null){

            Retrofit retrofit= new Retrofit.Builder().baseUrl("https://textbelt.com/")
                    .addConverterFactory(GsonConverterFactory.create()).build();
            unauthorizedApiInterface =retrofit.create(ApiInterface.class);
        }
        return unauthorizedApiInterface;
    }

    private static class MyInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            request = request.newBuilder()
                    .addHeader("Authorization", "")
                    .build();
            return chain.proceed(request);
        }
    }
}
