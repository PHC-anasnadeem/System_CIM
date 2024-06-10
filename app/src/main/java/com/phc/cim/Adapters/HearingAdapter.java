package com.phc.cim.Adapters;

import android.content.Context;
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
        holder.bind(hearing);
    }

    @Override
    public int getItemCount() {
        return hearingList.size();
    }

    public class HearingViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDistrict, tvPHCQuackSealedFileNo, tvActiveStatus, tvCaseFileId, tvComments, tvCommittees, tvHearingStatusDesc, tvOutletAddress, tvOutletName, tvCreateDate, tvHearingScheduledDate, tvfineimposed, tvFinalid;

        public HearingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDistrict = itemView.findViewById(R.id.tvDistrict);
            //tvPHCQuackSealedFileNo = itemView.findViewById(R.id.tvPHCQuackSealedFileNo);
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
            tvFinalid = itemView.findViewById(R.id.tvFinalid);
        }

        public void bind(Hearing hearing) {
            tvDistrict.setText("District: " + hearing.getDistrict());
           // tvPHCQuackSealedFileNo.setText("File #: " + hearing.getPhcQuackSealedFileNo());
            tvActiveStatus.setText("Status: " + hearing.getActiveStatus());
            tvCaseFileId.setText("File ID: " + hearing.getCaseFileId());
            tvComments.setText("Comments: " + hearing.getComments());
            tvCommittees.setText("Committee: " + hearing.getCommittees());
            tvHearingStatusDesc.setText("Hearing Status: " + hearing.getHearingStatusDesc());
            tvOutletAddress.setText("Outlet Address: " + hearing.getOutletAddress());
            tvOutletName.setText("Outlet Name: " + hearing.getOutletName());
            tvCreateDate.setText("Created Date: " + hearing.getCreateDate());
            tvHearingScheduledDate.setText("Schedule Date: " + hearing.getHearingScheduleDate());
            tvfineimposed.setText("Fine Imposed: " + hearing.getFineImposed());
            tvFinalid.setText("Final Id: " + hearing.getFinalId());
        }

    }
}

