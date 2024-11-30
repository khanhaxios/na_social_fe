package com.kt.na_social.friend;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kt.na_social.R;
import com.kt.na_social.adapters.FriendAdapter;
import com.kt.na_social.adapters.FriendRequestAdapter;
import com.kt.na_social.api.FriendApi;
import com.kt.na_social.api.requests.ResponeFriendRequestBody;
import com.kt.na_social.api.response.BaseResponse;
import com.kt.na_social.api.response.PageableResponse;
import com.kt.na_social.databinding.FragmentFriendBinding;
import com.kt.na_social.model.Friend;
import com.kt.na_social.ultis.ApiUtils;
import com.kt.na_social.ultis.RetrofitApi;
import com.kt.na_social.viewmodel.AuthStore;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendFragment extends Fragment {
    FragmentFriendBinding binding;

    FriendApi friendApi;
    int page = 0;
    int size = 20;
    FriendRequestAdapter friendRequestAdapter;
    FriendAdapter friendAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFriendBinding.inflate(inflater, container, false);
        friendApi = RetrofitApi.getInstance().create(FriendApi.class);
        FriendAdapter.IFriendAction iFriendAction = new FriendAdapter.IFriendAction() {
            @Override
            public void onViewProfile(String userId) {

            }
        };
        FriendRequestAdapter.IFriendRequestAction iFriendRequestAction = new FriendRequestAdapter.IFriendRequestAction() {
            @Override
            public void accepted(long requestId) {
                ResponeFriendRequestBody requestBody = new ResponeFriendRequestBody();
                requestBody.setRequestId(requestId);
                requestBody.setResponse(1);
                friendApi.responseFriendRequest(ApiUtils.getApiToken(), requestBody).enqueue(new Callback<BaseResponse<Void>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Void>> call, Response<BaseResponse<Void>> response) {
                        if (response.isSuccessful()) {
                            loadMyFriend();
                            loadRequest();
                        } else {
                            Log.d("DEV :::: ", response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<Void>> call, Throwable t) {
                        Log.d("DEV :::: ", t.getMessage());
                    }
                });
            }

            @Override
            public void denied(long requestId) {
                ResponeFriendRequestBody requestBody = new ResponeFriendRequestBody();
                requestBody.setRequestId(requestId);
                requestBody.setResponse(0);
                friendApi.responseFriendRequest(ApiUtils.getApiToken(), requestBody).enqueue(new Callback<BaseResponse<Void>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Void>> call, Response<BaseResponse<Void>> response) {
                        if (response.isSuccessful()) {
                            loadMyFriend();
                            loadRequest();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<Void>> call, Throwable t) {

                    }
                });
            }
        };

        friendAdapter = new FriendAdapter(requireContext(), iFriendAction);
        friendRequestAdapter = new FriendRequestAdapter(requireContext(), iFriendRequestAction);
        binding.rcyFriendRequest.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        binding.rcyMyFriend.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        binding.rcyFriendRequest.setAdapter(friendRequestAdapter);
        binding.rcyMyFriend.setAdapter(friendAdapter);
        loadRequest();
        loadMyFriend();
        return binding.getRoot();
    }

    public void loadRequest() {
        Call<BaseResponse<PageableResponse<List<Friend>>>> call = friendApi.getAllRequest(ApiUtils.getApiToken(), page, size);
        call.enqueue(new Callback<BaseResponse<PageableResponse<List<Friend>>>>() {
            @Override
            public void onResponse(Call<BaseResponse<PageableResponse<List<Friend>>>> call, Response<BaseResponse<PageableResponse<List<Friend>>>> response) {
                Log.d("DEVV ::: ", response.code() + "");
                if (response.isSuccessful() && response.body() != null) {
                    List<Friend> friends = response.body().getData().getContent();
                    Log.d("DEVV ::: Response Body", new Gson().toJson(response.body()));

                    friendRequestAdapter.updateData(friends);
                } else {
                    Toast.makeText(requireContext(), response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<PageableResponse<List<Friend>>>> call, Throwable t) {
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void loadMyFriend() {
        Call<BaseResponse<PageableResponse<List<Friend>>>> call = friendApi.getMyFriend(ApiUtils.getApiToken(), page, size);
        call.enqueue(new Callback<BaseResponse<PageableResponse<List<Friend>>>>() {
            @Override
            public void onResponse(Call<BaseResponse<PageableResponse<List<Friend>>>> call, Response<BaseResponse<PageableResponse<List<Friend>>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Friend> friends = response.body().getData().getContent();
                    friendAdapter.updateData(friends);
                } else {
                    Toast.makeText(requireContext(), response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<PageableResponse<List<Friend>>>> call, Throwable t) {
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}