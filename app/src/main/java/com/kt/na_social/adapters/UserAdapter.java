package com.kt.na_social.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kt.na_social.R;
import com.kt.na_social.databinding.UserItemBinding;
import com.kt.na_social.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.microedition.khronos.opengles.GL;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> users = new ArrayList<>();
    private Context context;

    private IUserAction iUserAction;

    public UserAdapter(Context context, IUserAction iUserAction) {
        this.context = context;
        this.iUserAction = iUserAction;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.onBind(users.get(position), context, iUserAction);
    }

    public void updateData(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        UserItemBinding binding;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = UserItemBinding.bind(itemView);
        }

        public void onBind(User user, Context context, IUserAction iUserAction) {
            Glide.with(context).load(user.getProfileAvatar()).circleCrop().into(binding.imvAvatar);
            binding.tvUsername.setText(user.getUsername());
            binding.tvRelationCount.setText(Math.round(Math.random() * 100) + " mutual friends");
            binding.btnGoProfile.setOnClickListener(v -> iUserAction.addFriend(user.getUid()));
        }
    }

    public interface IUserAction {
        void addFriend(String userId);
    }
}
