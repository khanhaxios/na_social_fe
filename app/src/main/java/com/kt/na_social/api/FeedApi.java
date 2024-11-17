package com.kt.na_social.api;

import com.kt.na_social.model.Feed;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface FeedApi {
    @GET("feeds/news-feed")
    Call<List<Feed>> getNewsFeed(@Query("limit") int limit, @Query("offset") int offset);
// add header for api request  using  this  : @Header("Authorization") String authHeader , example :
//    Call<List<Feed>> getNewsFeed( @Header("Authorization") String authHeader,@Query("limit") int limit, @Query("offset") int offset);

}
