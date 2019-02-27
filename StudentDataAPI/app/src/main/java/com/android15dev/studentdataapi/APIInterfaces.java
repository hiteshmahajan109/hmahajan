package com.android15dev.studentdataapi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APIInterfaces {

    @POST("create.php")
    Call<ResponseBody> register(@Header("Content-Type") String contentType, @Body UserDetail userDetail);
}
