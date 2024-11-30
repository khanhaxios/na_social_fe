package com.kt.na_social.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kt.na_social.R;
import com.kt.na_social.databinding.CommentItemBinding;
import com.kt.na_social.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private List<Comment> comments = new ArrayList<>();
    private Context context;

    public CommentAdapter(Context context) {
        this.context = context;
    }

    public void updateData(List<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new CommentViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.onBind(context, comments.get(position));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        CommentItemBinding binding;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CommentItemBinding.bind(itemView);
        }

        public void onBind(Context context, Comment comment) {
            Glide.with(context).load(comment.getCommenter().getProfileAvatar()).into(binding.imvAvatar);
            binding.tvUserName.setText(comment.getCommenter().getUsername());
            binding.tvCommentText.setText(comment.getContent());
            binding.tvTime.setText(comment.getUpdatedAt());
        }
    }
}
