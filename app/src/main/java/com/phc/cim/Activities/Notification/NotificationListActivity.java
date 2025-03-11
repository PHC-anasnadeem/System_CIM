package com.phc.cim.Activities.Notification;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.phc.cim.Activities.Common.NotificationActivity;
import com.phc.cim.R;

import java.util.List;

/**
 * Activity to display a list of notifications
 */
public class NotificationListActivity extends AppCompatActivity implements 
        NotificationAdapter.OnNotificationClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "NotificationListActivity";
    
    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView emptyView;
    private ProgressBar progressBar;
    
    private NotificationManager notificationManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);
        
        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Notifications");
        
        // Initialize views
        recyclerView = findViewById(R.id.notification_recycler_view);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        emptyView = findViewById(R.id.empty_view);
        progressBar = findViewById(R.id.progress_bar);
        
        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        
        // Set up adapter
        adapter = new NotificationAdapter(this, this);
        recyclerView.setAdapter(adapter);
        
        // Set up SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorAccent,
                R.color.colorPrimaryDark
        );
        
        // Initialize notification manager
        notificationManager = NotificationManager.getInstance(this);
        
        // Load notifications
        loadNotifications();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // Refresh notifications when activity is resumed
        loadNotifications();
    }
    
    /**
     * Load notifications from the API
     */
    private void loadNotifications() {
        showLoading();
        
        notificationManager.fetchNotifications(new NotificationApiClient.NotificationResponseListener() {
            @Override
            public void onResponse(List<NotificationModel> notifications) {
                hideLoading();
                
                if (notifications != null && !notifications.isEmpty()) {
                    adapter.setNotifications(notifications);
                    showNotifications();
                } else {
                    showEmpty();
                }
            }
            
            @Override
            public void onError(String error) {
                hideLoading();
                showError(error);
            }
        });
    }
    
    /**
     * Show loading state
     */
    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
    }
    
    /**
     * Hide loading state
     */
    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }
    
    /**
     * Show notifications
     */
    private void showNotifications() {
        recyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
    }
    
    /**
     * Show empty state
     */
    private void showEmpty() {
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }
    
    /**
     * Show error message
     * @param error Error message
     */
    private void showError(String error) {
        Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show();
        Log.e(TAG, "Error loading notifications: " + error);
    }
    
    /**
     * Handle notification click
     * @param notification Clicked notification
     */
    @Override
    public void onNotificationClick(NotificationModel notification) {
        // Mark notification as read
        notificationManager.markNotificationAsRead(notification.getId(), new NotificationApiClient.NotificationResponseListener() {
            @Override
            public void onResponse(List<NotificationModel> notifications) {
                // Update notification in the list
                notification.setRead(true);
                adapter.notifyDataSetChanged();
                
                // Open notification detail
                openNotificationDetail(notification);
            }
            
            @Override
            public void onError(String error) {
                Log.e(TAG, "Error marking notification as read: " + error);
                // Still open notification detail even if marking as read fails
                openNotificationDetail(notification);
            }
        });
    }
    
    /**
     * Open notification detail
     * @param notification Notification to open
     */
    private void openNotificationDetail(NotificationModel notification) {
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.putExtra("notificationId", notification.getId());
        intent.putExtra("notificationTitle", notification.getTitle());
        intent.putExtra("notificationMessage", notification.getMessage());
        intent.putExtra("notificationType", notification.getType());
        startActivity(intent);
    }
    
    /**
     * Handle swipe to refresh
     */
    @Override
    public void onRefresh() {
        loadNotifications();
    }
    
    /**
     * Handle back button in toolbar
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
} 