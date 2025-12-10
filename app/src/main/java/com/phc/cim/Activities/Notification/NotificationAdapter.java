package com.phc.cim.Activities.Notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.phc.cim.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for displaying notifications in a RecyclerView
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<NotificationModel> notifications;         // currently displayed list
    private List<NotificationModel> fullNotifications;     // full list for filtering
    private Context context;
    private OnNotificationClickListener listener;

    /**
     * Interface for notification click events
     */
    public interface OnNotificationClickListener {
        void onNotificationClick(NotificationModel notification);
    }

    public NotificationAdapter(Context context, OnNotificationClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.notifications = new ArrayList<>();
        this.fullNotifications = new ArrayList<>();
    }

    /**
     * Set notifications data
     */
    public void setNotifications(List<NotificationModel> notifications) {
        this.notifications = new ArrayList<>(notifications);
        this.fullNotifications = new ArrayList<>(notifications); // keep a copy for filtering
        notifyDataSetChanged();
    }

    /**
     * Add a single notification
     */
    public void addNotification(NotificationModel notification) {
        this.notifications.add(0, notification);
        this.fullNotifications.add(0, notification);
        notifyItemInserted(0);
    }

    /**
     * Clear all notifications
     */
    public void clearNotifications() {
        this.notifications.clear();
        this.fullNotifications.clear();
        notifyDataSetChanged();
    }

    /**
     * Filter notifications by type: "COMPLAINT" or "REVISIT"
     */
    public void filterByType(String type) {
        notifications.clear();
        if (type == null || type.isEmpty()) {
            notifications.addAll(fullNotifications);
        } else {
            for (NotificationModel n : fullNotifications) {
                if (type.equalsIgnoreCase(n.getType())) {
                    notifications.add(n);
                }
            }
        }
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

        // Set notification type badge
        holder.typeTextView.setText(notification.getType() != null ? notification.getType() : "UNKNOWN");

        // Show different info based on type
        if ("COMPLAINT".equalsIgnoreCase(notification.getType())) {
            holder.titleTextView.setText(notification.getTitle() != null ? notification.getTitle() : notification.getOutletName());
            holder.subTitleTextView.setText("Diary #: " + notification.getDiaryNo());
        } else { // REVISIT
            holder.titleTextView.setText(notification.getCategoryType() != null ? notification.getCategoryType() : notification.getOutletName());
            holder.subTitleTextView.setText("Final ID: " + notification.getFinalID());
        }

        // Message preview
        holder.messageTextView.setText(notification.getMessage() != null ? notification.getMessage() : "");

        // Relative time display
        holder.dateTextView.setText(notification.getInsertedDate() != null ? notification.getInsertedDate() : "");

        // Unread indicator
        holder.unreadIndicator.setVisibility(notification.isRead() ? View.GONE : View.VISIBLE);

        // Click listener
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onNotificationClick(notification);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImageView;
        TextView typeTextView;        // type badge
        TextView titleTextView;
        TextView subTitleTextView;
        TextView messageTextView;
        TextView dateTextView;
        View unreadIndicator;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.notification_icon);
            typeTextView = itemView.findViewById(R.id.typeTextView);
            titleTextView = itemView.findViewById(R.id.notification_title);
            subTitleTextView = itemView.findViewById(R.id.notification_subtitle);
            messageTextView = itemView.findViewById(R.id.notification_message);
            dateTextView = itemView.findViewById(R.id.notification_date);
            unreadIndicator = itemView.findViewById(R.id.unread_indicator);
        }
    }
}
