package com.exmaple.contactapp.network;


import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ApiInterface {


    @FormUrlEncoded
    @POST("text")
    Call<HashMap<String, Object>> sendOTP(@FieldMap Map<String, String> data);


}
