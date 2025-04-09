package com.phc.cim.Activities.Notification;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.phc.cim.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Adapter for displaying notifications in a RecyclerView
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<NotificationModel> notifications;
    private Context context;
    private OnNotificationClickListener listener;

    /**
     * Interface for notification click events
     */
    public interface OnNotificationClickListener {
        void onNotificationClick(NotificationModel notification);
    }

    /**
     * Constructor
     * @param context Context
     * @param listener Click listener
     */
    public NotificationAdapter(Context context, OnNotificationClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.notifications = new ArrayList<>();
    }

    /**
     * Set notifications data
     * @param notifications List of notifications
     */
    public void setNotifications(List<NotificationModel> notifications) {
        this.notifications = notifications;
        notifyDataSetChanged();
    }

    /**
     * Add a notification to the list
     * @param notification Notification to add
     */
    public void addNotification(NotificationModel notification) {
        this.notifications.add(0, notification);
        notifyItemInserted(0);
    }

    /**
     * Clear all notifications
     */
    public void clearNotifications() {
        this.notifications.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_list_item, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        NotificationModel notification = notifications.get(position);
        
        holder.titleTextView.setText(notification.getOutletName());
        holder.messageTextView.setText(notification.getMessage());
        
        // Format the date as a relative time span (e.g., "2 hours ago")
        if (notification.getInsertedDate() != null) {
//            String timeAgo = getRelativeTimeSpan(notification.getInsertedDate());
            String timeAgo = notification.getInsertedDate();
            holder.dateTextView.setText(timeAgo);
        } else {
            holder.dateTextView.setText("");
        }
        
        // Show/hide the unread indicator
        if (notification.isRead()) {
            holder.unreadIndicator.setVisibility(View.GONE);
        } else {
            holder.unreadIndicator.setVisibility(View.VISIBLE);
        }
        
        // Set click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onNotificationClick(notification);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    /**
     * Convert a date to a relative time span string
     * @param date Date to convert
     * @return Relative time span string (e.g., "2 hours ago")
     */
    private String getRelativeTimeSpan(Date date) {
        long time = date.getTime();
        long now = System.currentTimeMillis();
        
        CharSequence relativeTimeSpan = DateUtils.getRelativeTimeSpanString(
                time, now, DateUtils.MINUTE_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_RELATIVE);
        
        return relativeTimeSpan.toString();
    }

    /**
     * ViewHolder for notification items
     */
    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImageView;
        TextView titleTextView;
        TextView messageTextView;
        TextView dateTextView;
        View unreadIndicator;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.notification_icon);
            titleTextView = itemView.findViewById(R.id.notification_title);
            messageTextView = itemView.findViewById(R.id.notification_message);
            dateTextView = itemView.findViewById(R.id.notification_date);
            unreadIndicator = itemView.findViewById(R.id.unread_indicator);
        }
    }
} 