package com.kt.na_social.api;

import com.kt.na_social.api.requests.LoginRequestBody;
import com.kt.na_social.api.response.BaseResponse;
import com.kt.na_social.api.response.PageableResponse;
import com.kt.na_social.api.response.UserResponse;
import com.kt.na_social.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApi {
    @POST("public/auth/login")
    Call<BaseResponse<User>> login(@Body LoginRequestBody requestBody);

    @GET("users/search/{query}")
    Call<BaseResponse<PageableResponse<List<User>>>> searchUser(@Header("Authorization") String token, @Path("query") String query, @Query("page") int page, @Query("size") int size);
}
