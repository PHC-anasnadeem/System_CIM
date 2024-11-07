package com.phc.cim.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.phc.cim.Activities.Aqc.ActionActivity;
import com.phc.cim.R;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> imageUrls;


    public ImageAdapter(Context context, ArrayList<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public Object getItem(int position) {
        return imageUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
            holder.imageView = convertView.findViewById(R.id.image_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Convert 100dp to pixels
        int sizeInPixels = (int) (100 * context.getResources().getDisplayMetrics().density);

        Glide.with(context)
                .load(imageUrls.get(position))
                .override(sizeInPixels, sizeInPixels) // Set the width and height in pixels
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(holder.imageView);

        // Set click listener to show popup
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePopup(imageUrls.get(position));
            }
        });

        return convertView;

    }

    // Method to display the image in a popup
    private void showImagePopup(String imageUrl) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View popupView = inflater.inflate(R.layout.image_popup, null);
        ImageView popupImageView = popupView.findViewById(R.id.popup_image_view);

        // Load the image into the popup ImageView using Glide
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(popupImageView);

        builder.setView(popupView);

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        // Set click listener on the popup image to dismiss the dialog
        popupImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    private static class ViewHolder {
        ImageView imageView;
    }
}
