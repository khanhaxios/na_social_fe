package com.kt.na_social.feed;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.kt.na_social.R;
import com.kt.na_social.adapters.FeedAdapter;
import com.kt.na_social.api.FeedApi;
import com.kt.na_social.model.Feed;
import com.kt.na_social.ultis.Navigator;
import com.kt.na_social.ultis.RetrofitApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFeedFragment extends Fragment {
    private FeedAdapter feedAdapter;
    private RecyclerView feedCycle;
    ImageButton mBtnGoNewFeed;
    ImageButton mBtnGoSearch;
    ImageButton mBtnGoNoti;
    private int LIMIT = 10;
    private int PAGE = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_feed, container, false);
        initElement(root);
        return root;
    }

    public int getStartRecord(int page) {
        return ((page - 1) * LIMIT);
    }

    public int getEndRecord(int page) {
        return getStartRecord(page) + LIMIT;
    }
    // page , limit
    // PAGE += 1;
    // get feed by page

    public void initElement(View root) {
        feedCycle = root.findViewById(R.id.rcy_feed);
        mBtnGoNewFeed = root.findViewById(R.id.btn_go_new_feed);
        mBtnGoNewFeed.setOnClickListener(v -> {
            Navigator.navigateTo(R.id.link_go_to_new_feed, null);
        });
        mBtnGoSearch = root.findViewById(R.id.btn_go_search);
        mBtnGoSearch.setOnClickListener(v -> {
            Navigator.navigateTo(R.id.link_go_to_search, null);
        });
        mBtnGoNoti = root.findViewById(R.id.btn_go_noti);
        mBtnGoNoti.setOnClickListener(v -> {
            Navigator.navigateTo(R.id.link_go_to_noti, null);
        });
        FeedAdapter.IFeedAction iFeedAction = new FeedAdapter.IFeedAction() {
            @Override
            public void likeFeed(int feedId) {

            }

            @Override
            public void openFeedComment(int feedId) {

            }

            @Override
            public void shareFeed(int feedId) {

            }

            @Override
            public void bookMarkFeed(int feedId) {

            }
        };
        feedAdapter = new FeedAdapter(new ArrayList<>(), requireContext(), iFeedAction);
        feedCycle.setAdapter(feedAdapter);
        feedCycle.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        loadFeed(PAGE);
        feedCycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
                    PAGE += 1;
                    loadFeed(PAGE);
                }
            }
        });
    }

    public void loadFeed(int offsets) {
        // fetch from server
        FeedApi feedApi = RetrofitApi.getInstance().create(FeedApi.class);
        Call<List<Feed>> call = feedApi.getNewsFeed(getStartRecord(offsets), LIMIT);
        call.enqueue(new Callback<List<Feed>>() {
            @Override
            public void onResponse(Call<List<Feed>> call, Response<List<Feed>> response) {
                if (response.isSuccessful()) {
                    Log.d("fetch success and data length :: ", response.body().size() + "");
                    feedAdapter.addFeeds(response.body());
                } else {
                    Log.d("fetch error1 :: ", response.message());
                    Toast.makeText(requireActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Feed>> call, Throwable t) {
                Log.d("fetch error :: ", t.getMessage());
                Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}