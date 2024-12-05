package com.kt.na_social.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kt.na_social.R;
import com.kt.na_social.databinding.FriendItemBinding;
import com.kt.na_social.model.Friend;
import com.kt.na_social.model.User;
import com.kt.na_social.viewmodel.AuthStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {
    private List<Friend> friends = new ArrayList<>();

    private IFriendAction iFriendAction;
    private Context context;

    public FriendAdapter(Context context, IFriendAction iFriendAction) {
        this.context = context;
        this.iFriendAction = iFriendAction;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FriendViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        holder.onBind(friends.get(position), context, iFriendAction);
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public void updateData(List<Friend> friends) {
        this.friends = friends;
        notifyDataSetChanged();
    }

    public static class FriendViewHolder extends RecyclerView.ViewHolder {
        FriendItemBinding binding;

        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = FriendItemBinding.bind(itemView);
        }

        public void onBind(Friend friend, Context context, IFriendAction iFriendAction) {
            User user = friend.getReceiver().getUid().equals(AuthStore.getInstance().getLoggedUser().getUid()) ? friend.getSender() : friend.getReceiver();
            Glide.with(context).load(user.getProfileAvatar()).circleCrop().into(binding.imvAvatar);
            binding.tvUsername.setText(user.getUsername());
            binding.tvRelationCount.setText(Math.round(Math.random() * 100) + " mutual friends");
            binding.btnGoProfile.setOnClickListener(l -> iFriendAction.onViewProfile(user.getUid()));
        }
    }

    public interface IFriendAction {
        public void onViewProfile(String userId);
    }
}
