package com.kt.na_social.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kt.na_social.R;
import com.kt.na_social.model.Feed;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private List<Feed> feedList;

    private final Context context;
    private final IFeedAction iFeedAction;

    public FeedAdapter(List<Feed> feedList, Context context, IFeedAction iFeedAction) {
        this.feedList = feedList;
        this.context = context;
        this.iFeedAction = iFeedAction;
    }

    public void updateData(List<Feed> feedList) {
        this.feedList = feedList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item, parent, false);
        return new FeedViewHolder(root);
    }



    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        holder.onBind(feedList.get(position), context, iFeedAction);
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    public void addFeeds(List<Feed> body) {
        this.feedList.addAll(body);
        notifyDataSetChanged();
    }

    public static class FeedViewHolder extends RecyclerView.ViewHolder {
        TextView txtCaption;
        TextView txtAuthor;
        TextView txtTime;
        ImageView imvThumb;
        ImageView imvAuthor;
        TextView txtLikeCount;
        TextView txtCommentCount;
        ImageButton btnLikeAction;
        ImageView btnOpenCommentAction;
        ImageButton btnShareFeed;
        ImageView btnBookMarkFeed;

        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAuthor = itemView.findViewById(R.id.txt_author);
            txtTime = itemView.findViewById(R.id.txt_time);
            imvThumb = itemView.findViewById(R.id.feed_imv);
            imvAuthor = itemView.findViewById(R.id.author_avt);
            txtLikeCount = itemView.findViewById(R.id.txt_like_count);
            txtCommentCount = itemView.findViewById(R.id.txt_comment_count);
            btnLikeAction = itemView.findViewById(R.id.btn_like_action);
            btnOpenCommentAction = itemView.findViewById(R.id.btn_open_comment);
            btnShareFeed = itemView.findViewById(R.id.btn_share_action);
            btnBookMarkFeed = itemView.findViewById(R.id.btn_book_mark_action);
            txtCaption = itemView.findViewById(R.id.txt_description);
        }

        public void onBind(Feed feed, Context context, IFeedAction iFeedAction) {
            Log.d("here", feed.getLikeCount() + "");
            Glide.with(context).load(feed.getImage()).into(imvThumb);
            Glide.with(context).load(feed.getAuthorAvatar()).circleCrop().into(imvAuthor);
            txtAuthor.setText(feed.getAuthor());
            txtTime.setText(feed.getCreatedAt());
            txtLikeCount.setText(String.valueOf(feed.getLikeCount()));
            txtCommentCount.setText(String.valueOf(feed.getCommentCount()));
            txtCaption.setText(feed.getCaption());
            btnShareFeed.setOnClickListener((e) -> iFeedAction.shareFeed(feed.getId()));
            btnBookMarkFeed.setOnClickListener((e) -> iFeedAction.bookMarkFeed(feed.getId()));
            btnOpenCommentAction.setOnClickListener((e) -> iFeedAction.openFeedComment(feed.getId()));
            btnLikeAction.setOnClickListener((e) -> iFeedAction.likeFeed(feed.getId()));
        }
    }

    public interface IFeedAction {
        void likeFeed(int feedId);

        void openFeedComment(int feedId);

        void shareFeed(int feedId);

        void bookMarkFeed(int feedId);
    }
}
