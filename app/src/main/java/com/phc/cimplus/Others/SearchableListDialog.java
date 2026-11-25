package com.phc.cimplus.Others;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.phc.cimplus.R;

import java.util.ArrayList;
import java.util.List;

public class SearchableListDialog {

    public interface OnItemSelectedListener {
        void onItemSelected(String item, int originalPosition);
    }

    public static void show(Context context, String title, final List<String> items, final OnItemSelectedListener listener) {
        if (context == null || items == null || items.isEmpty()) return;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_searchable_spinner, null);
        builder.setView(dialogView);

        TextView titleView = dialogView.findViewById(R.id.dialog_title);
        ImageView btnClose = dialogView.findViewById(R.id.btn_close);
        EditText searchEditText = dialogView.findViewById(R.id.search_box);
        ListView listView = dialogView.findViewById(R.id.list_items);
        TextView txtEmpty = dialogView.findViewById(R.id.txt_empty);

        if (titleView != null && title != null) {
            titleView.setText(title);
        }

        final ArrayList<String> filteredList = new ArrayList<>(items);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, filteredList);
        listView.setAdapter(adapter);

        final AlertDialog dialog = builder.create();

        if (btnClose != null) {
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().toLowerCase().trim();
                filteredList.clear();
                for (String item : items) {
                    if (item != null && item.toLowerCase().contains(query)) {
                        filteredList.add(item);
                    }
                }
                adapter.notifyDataSetChanged();
                if (txtEmpty != null) {
                    txtEmpty.setVisibility(filteredList.isEmpty() ? View.VISIBLE : View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = filteredList.get(position);
                int origPos = items.indexOf(selected);
                if (listener != null) {
                    listener.onItemSelected(selected, origPos);
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}

