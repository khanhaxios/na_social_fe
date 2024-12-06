package com.kt.na_social.feed;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.kt.na_social.R;
import com.kt.na_social.adapters.UserAdapter;
import com.kt.na_social.api.FriendApi;
import com.kt.na_social.api.UserApi;
import com.kt.na_social.api.response.BaseResponse;
import com.kt.na_social.api.response.PageableResponse;
import com.kt.na_social.databinding.FragmentSearchBinding;
import com.kt.na_social.model.User;
import com.kt.na_social.ultis.ApiUtils;
import com.kt.na_social.ultis.RetrofitApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    FragmentSearchBinding binding;

    UserAdapter userAdapter;

    FriendApi friendApi;
    String querya = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        userAdapter = new UserAdapter(requireContext(), new UserAdapter.IUserAction() {
            @Override
            public void addFriend(String userId) {
                sendFriendRequest(userId);
            }
        });
        binding.rcyUsers.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        binding.rcyUsers.setAdapter(userAdapter);
        binding.btnSearch.setOnClickListener(s -> {
            String query = binding.searchBar.getText().toString().trim();
            querya = query;
            performSearch(query);
        });
        return binding.getRoot();
    }

    public void sendFriendRequest(String userId) {
        friendApi = RetrofitApi.getInstance().create(FriendApi.class);
        friendApi.sendAddFriendRequest(ApiUtils.getApiToken(), userId).enqueue(new Callback<BaseResponse<Void>>() {
            @Override
            public void onResponse(Call<BaseResponse<Void>> call, Response<BaseResponse<Void>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(requireContext(), "Send Success", Toast.LENGTH_LONG).show();
                    performSearch(querya);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Void>> call, Throwable t) {

            }
        });
    }

    public void performSearch(String query) {
        UserApi userApi = RetrofitApi.getInstance().create(UserApi.class);
        userApi.searchUser(ApiUtils.getApiToken(), query, 0, 100).enqueue(new Callback<BaseResponse<PageableResponse<List<User>>>>() {
            @Override
            public void onResponse(Call<BaseResponse<PageableResponse<List<User>>>> call, Response<BaseResponse<PageableResponse<List<User>>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userAdapter.updateData(response.body().getData().getContent());
                    Log.d("dev ::: ", response.body().getData().getContent().size() + "");
                } else {
                    Toast.makeText(requireContext(), "acasc", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<PageableResponse<List<User>>>> call, Throwable t) {
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }
}