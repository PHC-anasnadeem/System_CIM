package com.phc.cim.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.phc.cim.DataElements.RegistrationItem;
import com.phc.cim.R;

import java.util.ArrayList;
import java.util.List;

public class RegistrationItemAdapter extends RecyclerView.Adapter<RegistrationItemAdapter.ViewHolder> {

    private List<RegistrationItem> itemList = new ArrayList<>();

    public RegistrationItemAdapter(List<RegistrationItem> list) {
        this.itemList = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRegNo, tvName, tvType, tvDistrict, tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRegNo = itemView.findViewById(R.id.itemTitle);       // Registration_Number
            tvName = itemView.findViewById(R.id.itemSubtitle1);    // HCE_Name
            tvType = itemView.findViewById(R.id.itemSubtitle2);    // HCE_License_Type
            tvDistrict = itemView.findViewById(R.id.itemSubtitle3);// HCE_District
            tvDate = itemView.findViewById(R.id.itemSubtitle4);    // Registration_Date
        }
    }

    @NonNull
    @Override
    public RegistrationItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.registration_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegistrationItemAdapter.ViewHolder holder, int position) {

        RegistrationItem item = itemList.get(position);

        holder.tvRegNo.setText(item.getRegistrationNumber());
        holder.tvName.setText("HCE Name: " + item.getHceName());
        holder.tvType.setText("License Type: " + item.getHceLicenseType());
        holder.tvDistrict.setText("HCE District: " + item.getHceDistrict());
        holder.tvDate.setText("Registration Date: " + item.getRegistrationDate());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void updateList(List<RegistrationItem> newList) {
        this.itemList = newList;
        notifyDataSetChanged();
    }
}
