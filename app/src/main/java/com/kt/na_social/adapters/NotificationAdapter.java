package com.kt.na_social.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kt.na_social.R;
import com.kt.na_social.databinding.NotificationBinding;
import com.kt.na_social.databinding.NotificationItemBinding;
import com.kt.na_social.model.Notification;
import com.kt.na_social.ultis.TimeUtils;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    private List<Notification> notifications = new ArrayList<>();

    private final Context context;

    public NotificationAdapter(Context context) {
        this.context = context;
    }

    public void updateData(List<Notification> notifications) {
        this.notifications = notifications;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.onBind(notifications.get(position), context);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {

        NotificationItemBinding binding;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = NotificationItemBinding.bind(itemView);
        }

        public void onBind(Notification notification, Context context) {
            Glide.with(context).load(notification.getOwner().getProfileAvatar()).circleCrop().into(binding.imvAvatar);
            binding.txtContent.setText(notification.getContent());
            binding.txtTime.setText(TimeUtils.getRelativeTime(notification.getCreatedAt()));
        }
    }
}
