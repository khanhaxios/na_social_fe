package com.kt.na_social.api;

import com.kt.na_social.api.response.BaseResponse;
import com.kt.na_social.api.response.PageableResponse;
import com.kt.na_social.model.Notification;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface NotificationApi {
    @GET("notifications/get-all")
    Call<BaseResponse<PageableResponse<List<Notification>>>> getAllMyNotification(@Header("Authorization") String token, @Query("page") int page, @Query("size") int size);
}
