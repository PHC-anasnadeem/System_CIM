package com.phc.cim.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.phc.cim.DataElements.RegistrationItem;
import com.phc.cim.R;

import java.util.ArrayList;
import java.util.List;

public class RegistrationItemAdapter extends RecyclerView.Adapter<RegistrationItemAdapter.ViewHolder> {
    private List<RegistrationItem> items;
    private List<RegistrationItem> filteredItems;

    public RegistrationItemAdapter(List<RegistrationItem> items) {
        this.items = items;
        this.filteredItems = new ArrayList<>(items);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Define your view holder
        public ViewHolder(View view) {
            super(view);
            // Initialize your views here
        }
    }

    @Override
    public RegistrationItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.registration_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RegistrationItem item = filteredItems.get(position);
        // Bind your data to the views
    }

    @Override
    public int getItemCount() {
        return filteredItems.size();
    }

    public void filter(String nameQuery, String idQuery, String statusQuery) {
        filteredItems.clear();
        for (RegistrationItem item : items) {
            // Apply your filtering logic
            boolean matches = true;
            if (!nameQuery.isEmpty() && !item.getName().toLowerCase().contains(nameQuery.toLowerCase())) {
                matches = false;
            }
            if (!idQuery.isEmpty() && !item.getId().toLowerCase().contains(idQuery.toLowerCase())) {
                matches = false;
            }
            if (!statusQuery.isEmpty() && !item.getStatus().toLowerCase().contains(statusQuery.toLowerCase())) {
                matches = false;
            }
            if (matches) {
                filteredItems.add(item);
            }
        }
        notifyDataSetChanged();
    }
}

