package com.phc.cim.Adapters;


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
        holder.tvFinalID.setText("Final ID: " + diaryEntry.getFinalID());
        holder.tvAqcFileNo.setText("AQC File No: " + diaryEntry.getAqcFileNo());
        holder.tvComments.setText("Comments: " + diaryEntry.getComments());
        holder.tvDiaryNo.setText("Diary No: " + diaryEntry.getDiaryNo());
        holder.tvDistrict.setText("District: " + diaryEntry.getDistrict());
        holder.tvOutletName.setText("Outlet Name: " + diaryEntry.getOutletName());
        holder.tvdeseal.setText("De-seal Date: " + diaryEntry.getDesealDate());
    }

    @Override
    public int getItemCount() {
        return diaryEntryList.size();
    }

    static class DiaryEntryViewHolder extends RecyclerView.ViewHolder {
        TextView tvFinalID, tvAqcFileNo, tvComments, tvDiaryNo, tvDistrict, tvOutletName, tvdeseal;

        public DiaryEntryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFinalID = itemView.findViewById(R.id.tvFinalID);
            tvAqcFileNo = itemView.findViewById(R.id.tvAqcFileNo);
            tvComments = itemView.findViewById(R.id.tvComments);
            tvDiaryNo = itemView.findViewById(R.id.tvDiaryNo);
            tvDistrict = itemView.findViewById(R.id.tvDistrict);
            tvOutletName = itemView.findViewById(R.id.tvOutletName);
            tvdeseal = itemView.findViewById(R.id.tvdeseal);
        }
    }
}

