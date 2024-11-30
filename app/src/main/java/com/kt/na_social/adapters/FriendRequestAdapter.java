package com.kt.na_social.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kt.na_social.R;
import com.kt.na_social.databinding.FriendRequestItemBinding;
import com.kt.na_social.model.Friend;

import java.util.ArrayList;
import java.util.List;

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.FriendRequestViewHolder> {
    private List<Friend> friends = new ArrayList<>();
    private Context context;
    private IFriendRequestAction iFriendRequestAction;

    public FriendRequestAdapter(Context context, IFriendRequestAction iFriendRequestAction) {
        this.context = context;
        this.iFriendRequestAction = iFriendRequestAction;
    }

    public void updateData(List<Friend> friends) {
        this.friends = friends;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FriendRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FriendRequestViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_request_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendRequestViewHolder holder, int position) {
        holder.onBind(friends.get(position), context, iFriendRequestAction);
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public static class FriendRequestViewHolder extends RecyclerView.ViewHolder {
        FriendRequestItemBinding binding;

        public FriendRequestViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = FriendRequestItemBinding.bind(itemView);
        }

        public void onBind(Friend friend, Context context, IFriendRequestAction requestAction) {
            Glide.with(context).load(friend.getSender().getProfileAvatar()).circleCrop().into(binding.imvAvt);
            binding.tvName.setText(friend.getSender().getUsername());
            binding.tvCount.setText(friend.getCreatedAt());
            binding.acceptButton.setOnClickListener(c -> requestAction.accepted(friend.getId()));
            binding.deleteButton.setOnClickListener(c -> requestAction.denied(friend.getId()));
        }
    }

    public interface IFriendRequestAction {
        public void accepted(long requestId);

        public void denied(long requestId);
    }
}
