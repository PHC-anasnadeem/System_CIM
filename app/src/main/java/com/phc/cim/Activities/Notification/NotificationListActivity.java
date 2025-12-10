package com.phc.cim.Activities.Notification;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import com.phc.cim.R;
import com.phc.cim.BuildConfig;

import java.util.ArrayList;
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
    private Button btnComplaint, btnRevisit, btnAll;

    private NotificationManager notificationManager;
    private List<NotificationModel> notificationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);

        // Toolbar setup
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Notifications");

        // Views
        recyclerView = findViewById(R.id.notification_recycler_view);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        emptyView = findViewById(R.id.empty_view);
        progressBar = findViewById(R.id.progress_bar);
        btnComplaint = findViewById(R.id.btnComplaint);
        btnRevisit = findViewById(R.id.btnRevisit);
        btnAll = findViewById(R.id.btnAll);

        // RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new NotificationAdapter(this, this);
        recyclerView.setAdapter(adapter);

        // Swipe refresh
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);

        // Buttons for filter
        btnComplaint.setOnClickListener(v -> adapter.filterByType("COMPLAINT"));
        btnRevisit.setOnClickListener(v -> adapter.filterByType("REVISIT"));
        btnAll.setOnClickListener(v -> adapter.filterByType(null)); // show all

        // Notification manager
        notificationManager = NotificationManager.getInstance(this);

        // Load notifications
        if (notificationList != null && !notificationList.isEmpty()) {
            showNotifications(notificationList);
        } else {
            loadNotifications();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotifications();
    }

    private void loadNotifications() {
        showLoading();

        notificationManager.fetchNotifications(new NotificationApiClient.NotificationResponseListener() {
            @Override
            public void onResponse(List<NotificationModel> notifications) {
                hideLoading();

                if (notifications != null && !notifications.isEmpty()) {
                    adapter.setNotifications(notifications);
                    showNotifications(notifications);
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

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
    }

    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }

    private void showNotifications(List<NotificationModel> notifications) {
        recyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        adapter.setNotifications(notifications);
    }

    private void showEmpty() {
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }

    private void showError(String error) {
        String userMessage = "Unable to load notifications";
        if (BuildConfig.DEBUG || error.contains("User ID")) {
            userMessage += ": " + error;
        }
        Toast.makeText(this, userMessage, Toast.LENGTH_SHORT).show();
        emptyView.setText("Unable to load notifications.\nPull down to refresh.");
        emptyView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

    }

    @Override
    public void onNotificationClick(NotificationModel notification) {
        notificationManager.markNotificationAsRead(notification.getNotificationId(), new NotificationApiClient.NotificationResponseListener() {
            @Override
            public void onResponse(List<NotificationModel> notifications) {
                notification.setRead(true);
                adapter.notifyDataSetChanged();
                openNotificationDetail(notification);
            }

            @Override
            public void onError(String error) {
                openNotificationDetail(notification);
            }
        });
    }

    private void openNotificationDetail(NotificationModel notification) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_notification_detail, null);
        TextView outletTextView = dialogView.findViewById(R.id.detailOutletNameTextView);
        TextView addressTextView = dialogView.findViewById(R.id.detailComplainantAddressTextView);
        TextView contactTextView = dialogView.findViewById(R.id.detailComplainantContactNoTextView);
        TextView districtTextView = dialogView.findViewById(R.id.detailDistrictTextView);
        TextView regTextView = dialogView.findViewById(R.id.detailPhcRegistrationNoTextView);
        TextView finalIdTextView = dialogView.findViewById(R.id.detailFinalIdTextView);
        TextView sealTypeTextView = dialogView.findViewById(R.id.SealType);
        TextView categoryTypeTextView = dialogView.findViewById(R.id.CategoryType);
        TextView summonDateTextView = dialogView.findViewById(R.id.SummonIssueDate);
        TextView sealedByTextView = dialogView.findViewById(R.id.sealedBy);
        TextView commentsTextView = dialogView.findViewById(R.id.Comments);

        outletTextView.setText(notification.getOutletName() != null ? "Outlet Name: " + notification.getOutletName() : "Complainant: " + notification.getComplainantName());
        districtTextView.setText("District: " + notification.getDistrict());

        if ("REVISIT".equals(notification.getType())) {
            regTextView.setText("Case File #: " + notification.getCaseFileID());
            finalIdTextView.setText("Final ID: " + notification.getFinalID());
            sealTypeTextView.setText("Seal Type: " + notification.getSealType());
            categoryTypeTextView.setText("Category: " + notification.getCategoryType());
            summonDateTextView.setText("Summon Issue Date: " + notification.getSummonIssueDate());
            sealedByTextView.setText("Sealed By: " + notification.getSealedBy());
            commentsTextView.setText("Comments: " + notification.getComments());
            contactTextView.setText("Contact #: " + notification.getQuack_ContactNumber());
            addressTextView.setText("Address: " + notification.getComplainantAddress());
        } else {
            regTextView.setText("PHC Registration #: " + notification.getPHC_RegistrationNo());
            finalIdTextView.setText("Diary #: " + notification.getDiaryNo());
            sealTypeTextView.setText("Complaint Detail: " + notification.getComplaintDetail());
            categoryTypeTextView.setText("Title: " + notification.getTitle());
            summonDateTextView.setText("Complaint Date: " + notification.getInsertedDate());
            sealedByTextView.setText("Complainant Name: " + notification.getComplainantName());
            commentsTextView.setText("Comments: " + notification.getComments());
            contactTextView.setText("Complainant Contact #: " + notification.getComplainantContactNo());
            addressTextView.setText("Complainant Address: " + notification.getComplainantAddress());
        }

        new AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle(notification.getType().equals("COMPLAINT") ? "Complaint Details" : "Revisit Details")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public void onRefresh() {
        loadNotifications();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
