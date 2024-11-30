package com.kt.na_social.api;

import com.kt.na_social.api.requests.ResponeFriendRequestBody;
import com.kt.na_social.api.response.BaseResponse;
import com.kt.na_social.api.response.PageableResponse;
import com.kt.na_social.model.Friend;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FriendApi {
    @GET("friends/my-friend")
    Call<BaseResponse<PageableResponse<List<Friend>>>> getMyFriend(@Header("Authorization") String token, @Query("page") int page, @Query("size") int size);

    @GET("friends/all-request")
    Call<BaseResponse<PageableResponse<List<Friend>>>> getAllRequest(@Header("Authorization") String token, @Query("page") int page, @Query("size") int size);

    @POST("friends/response-add-friend-request")
    Call<BaseResponse<Void>> responseFriendRequest(@Header("Authorization") String token, @Body ResponeFriendRequestBody requestBody);

    @POST("friends/send-add-friend-request/{id}")
    Call<BaseResponse<Void>> sendAddFriendRequest(@Header("Authorization") String token, @Path("id") String userId);
}
