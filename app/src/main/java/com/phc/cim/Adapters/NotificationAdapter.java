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
        NotificationItem item = notificationList.get(position);

        holder.typeTextView.setText(item.getType());
        holder.outletNameTextView.setText(item.getMessage()); // show message
        holder.districtTextView.setText(item.getInsertedDate()); // show date
        holder.districtTextView.setText(item.getDistrictName());

        holder.itemView.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                showDetailDialog(notificationList.get(pos));
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView typeTextView, messageTextView, districtTextView, outletNameTextView;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);

            typeTextView = itemView.findViewById(R.id.typeTextView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
            outletNameTextView = itemView.findViewById(R.id.outletNameTextView);
            districtTextView = itemView.findViewById(R.id.districtTextView);
        }

    }

    private void showDetailDialog(NotificationItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(item.getType().equals("COMPLAINT") ? "Complaint Details" : "Revisit Details");

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_notification_detail, null);
        builder.setView(view);

        // COMMON FIELDS
        ((TextView) view.findViewById(R.id.detailOutletNameTextView))
                .setText(item.getOutletName() != null ? item.getOutletName() : item.getComplainantName());
        ((TextView) view.findViewById(R.id.detailDistrictTextView)).setText(item.getDistrict());

        if ("REVISIT".equals(item.getType())) {
            ((TextView) view.findViewById(R.id.detailPhcRegistrationNoTextView)).setText(item.getCaseFileID());
            ((TextView) view.findViewById(R.id.detailFinalIdTextView)).setText(String.valueOf(item.getFinalID()));
            ((TextView) view.findViewById(R.id.CategoryType)).setText(item.getCategoryType());
            ((TextView) view.findViewById(R.id.SealType)).setText(item.getSealType());
            ((TextView) view.findViewById(R.id.SummonIssueDate)).setText(item.getSummonIssueDate());
            ((TextView) view.findViewById(R.id.sealedBy)).setText(item.getSealedBy());
            ((TextView) view.findViewById(R.id.Comments)).setText(item.getComments());
        } else { // COMPLAINT
            ((TextView) view.findViewById(R.id.detailPhcRegistrationNoTextView)).setText(item.getPHC_RegistrationNo());
            ((TextView) view.findViewById(R.id.detailFinalIdTextView)).setText(item.getDiaryNo());
            ((TextView) view.findViewById(R.id.CategoryType)).setText(item.getTitle());
            ((TextView) view.findViewById(R.id.SealType)).setText(item.getComplaintDetail());
            ((TextView) view.findViewById(R.id.SummonIssueDate)).setText(item.getInsertedDate()); // ComplaintDate
            ((TextView) view.findViewById(R.id.sealedBy)).setText(item.getComplainantName());
            ((TextView) view.findViewById(R.id.Comments)).setText(item.getComments());

            // Additional complaint fields
            ((TextView) view.findViewById(R.id.detailOutletNameTextView)).setText(item.getOutletName());
            ((TextView) view.findViewById(R.id.detailComplainantAddressTextView)).setText(item.getComplainantAddress());
            ((TextView) view.findViewById(R.id.detailComplainantContactNoTextView)).setText(item.getComplainantContactNo());
        }

        builder.setPositiveButton("OK", null);
        builder.create().show();
    }


}
