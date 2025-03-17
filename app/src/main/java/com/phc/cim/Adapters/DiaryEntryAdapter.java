package com.phc.cim.Adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.phc.cim.DataElements.DiaryEntry;
import com.phc.cim.R;

import java.util.List;

public class DiaryEntryAdapter extends RecyclerView.Adapter<DiaryEntryAdapter.DiaryEntryViewHolder> {

    private List<DiaryEntry> diaryEntryList;

    public DiaryEntryAdapter(List<DiaryEntry> diaryEntryList) {
        this.diaryEntryList = diaryEntryList;
    }

    @NonNull
    @Override
    public DiaryEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diary_entry, parent, false);
        return new DiaryEntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryEntryViewHolder holder, int position) {
        DiaryEntry diaryEntry = diaryEntryList.get(position);
        
        // Set Final ID
        holder.tvFinalID.setText("Final ID: " + diaryEntry.getFinalID());
        
        // Set De-seal Date
        holder.tvDesealDate.setText(diaryEntry.getDesealDate());
        
        // Set Outlet Name (with fallback)
        String outletName = diaryEntry.getOutletName();
        if (TextUtils.isEmpty(outletName) || "N/A".equals(outletName)) {
            holder.tvOutletName.setText("Outlet Name not available");
        } else {
            holder.tvOutletName.setText(outletName);
        }
        
        // Set AQC File No
        holder.tvAqcFileNo.setText(String.valueOf(diaryEntry.getAqcFileNo()));
        
        // Set Diary No
        holder.tvDiaryNo.setText(diaryEntry.getDiaryNo());
        
        // Set District
        holder.tvDistrict.setText(diaryEntry.getDistrict());
        
        // Set Comments (with fallback)
        String comments = diaryEntry.getComments();
        if (TextUtils.isEmpty(comments) || "No comments available".equals(comments)) {
            holder.tvComments.setText("No comments available");
        } else {
            holder.tvComments.setText(comments);
        }
    }

    @Override
    public int getItemCount() {
        return diaryEntryList.size();
    }

    static class DiaryEntryViewHolder extends RecyclerView.ViewHolder {
        TextView tvFinalID, tvAqcFileNo, tvComments, tvDiaryNo, tvDistrict, tvOutletName, tvDesealDate;

        public DiaryEntryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFinalID = itemView.findViewById(R.id.tvFinalID);
            tvAqcFileNo = itemView.findViewById(R.id.tvAqcFileNo);
            tvComments = itemView.findViewById(R.id.tvComments);
            tvDiaryNo = itemView.findViewById(R.id.tvDiaryNo);
            tvDistrict = itemView.findViewById(R.id.tvDistrict);
            tvOutletName = itemView.findViewById(R.id.tvOutletName);
            tvDesealDate = itemView.findViewById(R.id.tvdeseal);
        }
    }
}

