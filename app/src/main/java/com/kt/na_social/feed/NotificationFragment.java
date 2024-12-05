package com.kt.na_social.feed;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kt.na_social.R;
import com.kt.na_social.adapters.NotificationAdapter;
import com.kt.na_social.api.NotificationApi;
import com.kt.na_social.api.response.BaseResponse;
import com.kt.na_social.api.response.PageableResponse;
import com.kt.na_social.databinding.FragmentNotificationBinding;
import com.kt.na_social.databinding.NotificationBinding;
import com.kt.na_social.model.Notification;
import com.kt.na_social.ultis.ApiUtils;
import com.kt.na_social.ultis.RetrofitApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends Fragment {
    FragmentNotificationBinding notificationBinding;

    int page = 0;
    int limit = 100;

    NotificationApi notificationApi;
    NotificationAdapter notificationAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        notificationBinding = FragmentNotificationBinding.inflate(inflater, container, false);
        notificationApi = RetrofitApi.getInstance().create(NotificationApi.class);
        notificationAdapter = new NotificationAdapter(requireContext());
        notificationBinding.rcyRecent.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        notificationBinding.rcyRecent.setAdapter(notificationAdapter);
        loadNotification();
        notificationBinding.refreshControl.setOnRefreshListener(() -> {
            loadNotification();
        });

        return notificationBinding.getRoot();
    }

    public void loadNotification() {
        Call<BaseResponse<PageableResponse<List<Notification>>>> call = notificationApi.getAllMyNotification(ApiUtils.getApiToken(), page, limit);
        call.enqueue(new Callback<BaseResponse<PageableResponse<List<Notification>>>>() {
            @Override
            public void onResponse(Call<BaseResponse<PageableResponse<List<Notification>>>> call, Response<BaseResponse<PageableResponse<List<Notification>>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Notification> notifications = response.body().getData().getContent();
                    notificationAdapter.updateData(notifications);
                    notificationBinding.refreshControl.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<PageableResponse<List<Notification>>>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}