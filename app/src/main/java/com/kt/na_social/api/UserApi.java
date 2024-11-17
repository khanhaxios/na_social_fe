package com.kt.na_social.api;

import com.kt.na_social.api.requests.LoginRequestBody;
import com.kt.na_social.api.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApi {
    @POST("public/auth/login")
    Call<UserResponse> login(@Body LoginRequestBody requestBody);
}
