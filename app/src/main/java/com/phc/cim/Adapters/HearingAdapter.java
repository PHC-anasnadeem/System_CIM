package com.phc.cim.Adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.phc.cim.DataElements.Hearing;
import com.phc.cim.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HearingAdapter extends RecyclerView.Adapter<HearingAdapter.HearingViewHolder> {

    private List<Hearing> hearingList;
    private Context context;

    public HearingAdapter(Context context, List<Hearing> hearingList) {
        this.context = context;
        this.hearingList = hearingList;
    }

    @NonNull
    @Override
    public HearingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hearing, parent, false);
        return new HearingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HearingViewHolder holder, int position) {
        Hearing hearing = hearingList.get(position);
        
        // Set Final ID
        holder.tvFinalid.setText("Final ID: " + hearing.getFinalId());
        
        // Set Hearing Status Description
        String hearingStatus = hearing.getHearingStatusDesc();
        if (!TextUtils.isEmpty(hearingStatus) && !"N/A".equals(hearingStatus)) {
            holder.tvHearingStatusDesc.setText(hearingStatus);
        } else {
            holder.tvHearingStatusDesc.setText("Pending");
        }
        
        // Set Outlet Name
        String outletName = hearing.getOutletName();
        if (!TextUtils.isEmpty(outletName) && !"N/A".equals(outletName)) {
            holder.tvOutletName.setText(outletName);
        } else {
            holder.tvOutletName.setText("Outlet name not available");
        }
        
        // Set Outlet Address
        String outletAddress = hearing.getOutletAddress();
        if (!TextUtils.isEmpty(outletAddress) && !"N/A".equals(outletAddress)) {
            holder.tvOutletAddress.setText(outletAddress);
        } else {
            holder.tvOutletAddress.setText("Address not available");
        }
        
        // Set District
        String district = hearing.getDistrict();
        if (!TextUtils.isEmpty(district) && !"N/A".equals(district)) {
            holder.tvDistrict.setText(district);
        } else {
            holder.tvDistrict.setText("N/A");
        }
        
        // Set Case File ID
        String caseFileId = hearing.getCaseFileId();
        if (!TextUtils.isEmpty(caseFileId) && !"N/A".equals(caseFileId)) {
            holder.tvCaseFileId.setText(caseFileId);
        } else {
            holder.tvCaseFileId.setText("N/A");
        }
        
        // Set Active Status
        String activeStatus = hearing.getActiveStatus();
        if (!TextUtils.isEmpty(activeStatus) && !"N/A".equals(activeStatus)) {
            holder.tvActiveStatus.setText(activeStatus);
        } else {
            holder.tvActiveStatus.setText("N/A");
        }
        
        // Set Create Date
        holder.tvCreateDate.setText(hearing.getCreateDate());
        
        // Set Hearing Scheduled Date
        holder.tvHearingScheduledDate.setText(hearing.getHearingScheduleDate());
        
        // Set Fine Imposed
        String fineImposed = hearing.getFineImposed();
        if (!TextUtils.isEmpty(fineImposed) && !"N/A".equals(fineImposed)) {
            holder.tvfineimposed.setText(fineImposed);
        } else {
            holder.tvfineimposed.setText("Not specified");
        }
        
        // Set Committees
        String committees = hearing.getCommittees();
        if (!TextUtils.isEmpty(committees) && !"N/A".equals(committees)) {
            holder.tvCommittees.setText(committees);
        } else {
            holder.tvCommittees.setText("Not specified");
        }
        
        // Set Comments
        String comments = hearing.getComments();
        if (!TextUtils.isEmpty(comments) && !"No comments available".equals(comments)) {
            holder.tvComments.setText(comments);
        } else {
            holder.tvComments.setText("No comments available");
        }
    }

    @Override
    public int getItemCount() {
        return hearingList.size();
    }

    public class HearingViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDistrict, tvActiveStatus, tvCaseFileId, tvComments, tvCommittees, 
                         tvHearingStatusDesc, tvOutletAddress, tvOutletName, tvCreateDate, 
                         tvHearingScheduledDate, tvfineimposed, tvFinalid;

        public HearingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFinalid = itemView.findViewById(R.id.tvFinalid);
            tvDistrict = itemView.findViewById(R.id.tvDistrict);
            tvActiveStatus = itemView.findViewById(R.id.tvActiveStatus);
            tvCaseFileId = itemView.findViewById(R.id.tvCaseFileId);
            tvComments = itemView.findViewById(R.id.tvComments);
            tvCommittees = itemView.findViewById(R.id.tvCommittees);
            tvHearingStatusDesc = itemView.findViewById(R.id.tvHearingStatusDesc);
            tvOutletAddress = itemView.findViewById(R.id.tvOutletAddress);
            tvOutletName = itemView.findViewById(R.id.tvOutletName);
            tvCreateDate = itemView.findViewById(R.id.tvCreateDate);
            tvHearingScheduledDate = itemView.findViewById(R.id.tvHearingScheduledDate);
            tvfineimposed = itemView.findViewById(R.id.tvfineimposed);
        }
    }
}

