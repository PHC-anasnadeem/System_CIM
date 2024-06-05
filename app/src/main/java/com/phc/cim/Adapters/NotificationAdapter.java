package com.phc.cim.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.phc.cim.DataElements.NotificationItem;
import com.phc.cim.R;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private Context context;
    private List<NotificationItem> notificationList;

    public NotificationAdapter(Context context, List<NotificationItem> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }

    public int getNotificationCount() {
        return notificationList.size();
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notificationitem, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        NotificationItem notification = notificationList.get(position);
        holder.outletNameTextView.setText(notification.getOutletName());
        holder.districtTextView.setText(notification.getDistrict());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetailDialog(notification);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView outletNameTextView;
        TextView districtTextView;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            outletNameTextView = itemView.findViewById(R.id.outletNameTextView);
            districtTextView = itemView.findViewById(R.id.districtTextView);
        }
    }

    private void showDetailDialog(NotificationItem notification) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Notification Details");

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_notification_detail, null);
        builder.setView(view);

        // Set notification details in the dialog
        ((TextView) view.findViewById(R.id.detailTitleTextView)).setText(notification.getTitle());
        ((TextView) view.findViewById(R.id.detailOutletNameTextView)).setText(notification.getOutletName());
        ((TextView) view.findViewById(R.id.detailComplainantAddressTextView)).setText(notification.getComplainantAddress());
        ((TextView) view.findViewById(R.id.detailComplaintDateTextView)).setText(notification.getComplaintDate());
        ((TextView) view.findViewById(R.id.detailComplainantContactNoTextView)).setText(notification.getComplainantContactNo());
        ((TextView) view.findViewById(R.id.detailAqcComplaintsSourceDescTextView)).setText(notification.getAQC_ComplaintsSource_Desc());
        ((TextView) view.findViewById(R.id.detailDistrictTextView)).setText(notification.getDistrict());
        ((TextView) view.findViewById(R.id.detailTehsilDescTextView)).setText(notification.getTehsilDesc());
        ((TextView) view.findViewById(R.id.detailPhcRegistrationNoTextView)).setText(notification.getPHC_RegistrationNo());
        ((TextView) view.findViewById(R.id.detailCommentsTextView)).setText(notification.getComments());
        ((TextView) view.findViewById(R.id.detailFinalIdTextView)).setText(notification.getFinalID());
        ((TextView) view.findViewById(R.id.detailTypeDescTextView)).setText(notification.getTypeDesc());
        ((TextView) view.findViewById(R.id.detailIsRegWithPHCTextView)).setText(notification.getIsRegWithPHC());
        ((TextView) view.findViewById(R.id.detailUserNameTextView)).setText(notification.getUser_Name());
        ((TextView) view.findViewById(R.id.detailLastUpdatedByTextView)).setText(notification.getLastUpdatedBy());

        builder.setPositiveButton("OK", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
