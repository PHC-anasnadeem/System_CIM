package com.phc.cimplus.TabsActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.phc.cimplus.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PlanReportActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReportAdapter adapter;
    private List<Object> reportItems = new ArrayList<>();
    
    private String planId, planCode, district, team, username, email, startDate, endDate, totalVisits, totalFir, totalImages;
    private ArrayList<HashMap<String, String>> visitData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_report);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Extract data from Intent
        Intent intent = getIntent();
        planId = intent.getStringExtra("PlanID");
        planCode = intent.getStringExtra("PlanCode");
        district = intent.getStringExtra("District");
        team = intent.getStringExtra("team");
        username = intent.getStringExtra("username");
        email = intent.getStringExtra("email");
        startDate = intent.getStringExtra("startdat");
        endDate = intent.getStringExtra("enddate");
        totalVisits = intent.getStringExtra("totalvisits");
        totalFir = intent.getStringExtra("totalfir");
        totalImages = intent.getStringExtra("TotalImages");
        visitData = (ArrayList<HashMap<String, String>>) intent.getSerializableExtra("result");

        recyclerView = findViewById(R.id.report_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        prepareReportData();

        adapter = new ReportAdapter(reportItems);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fabShare = findViewById(R.id.fab_share);
        fabShare.setOnClickListener(v -> shareReport());
    }

    private void prepareReportData() {
        reportItems.clear();

        // 1. Header
        reportItems.add(new HeaderItem(planCode, username, team, district, startDate + " to " + endDate));

        // 2. Summary
        reportItems.add(new SummaryItem(totalVisits, totalFir, totalImages));

        // 3. Grouped Visits
        if (visitData != null && !visitData.isEmpty()) {
            // Group by Action (e.g., "Functional Sealed")
            Map<String, List<HashMap<String, String>>> groupedVisits = new TreeMap<>();
            
            for (HashMap<String, String> visit : visitData) {
                String action = visit.get("Action");
                if (action == null || action.isEmpty()) action = "Other Activities";
                
                if (!groupedVisits.containsKey(action)) {
                    groupedVisits.put(action, new ArrayList<>());
                }
                groupedVisits.get(action).add(visit);
            }

            for (Map.Entry<String, List<HashMap<String, String>>> entry : groupedVisits.entrySet()) {
                reportItems.add(new SectionHeaderItem(entry.getKey() + " (" + entry.getValue().size() + ")"));
                for (HashMap<String, String> visit : entry.getValue()) {
                    String date = visit.get("VisitedDate");
                    if (date == null || date.equalsIgnoreCase("null")) {
                        date = "N/A";
                    }
                    reportItems.add(new VisitItem(
                            visit.get("Name"),
                            visit.get("FinalID"),
                            date,
                            visit.get("Address"),
                            visit.get("Comments"),
                            visit.get("district")
                    ));
                }
            }
        } else {
            reportItems.add(new SectionHeaderItem("No visit data available"));
        }
    }

    private void shareReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("PHC CIM - Plan Activity Report\n");
        sb.append("==============================\n");
        sb.append("Plan: ").append(planCode).append("\n");
        sb.append("User: ").append(username).append(" (").append(email).append(")\n");
        sb.append("Team: ").append(team).append("\n");
        sb.append("District: ").append(district).append("\n");
        sb.append("Period: ").append(startDate).append(" - ").append(endDate).append("\n\n");
        
        sb.append("Summary:\n");
        sb.append("- Total Visits: ").append(totalVisits).append("\n");
        sb.append("- Total FIRs: ").append(totalFir).append("\n");
        sb.append("- Total Images: ").append(totalImages).append("\n\n");
        
        // Add detailed visits to text share
        String currentAction = "";
        for (Object item : reportItems) {
            if (item instanceof SectionHeaderItem) {
                currentAction = ((SectionHeaderItem) item).title;
                sb.append("\n[").append(currentAction).append("]\n");
            } else if (item instanceof VisitItem) {
                VisitItem v = (VisitItem) item;
                sb.append("- ").append(v.name).append(" (ID: ").append(v.finalId).append(")\n");
                sb.append("  Date: ").append(v.date).append("\n");
                if (v.district != null && !v.district.equalsIgnoreCase("null")) {
                    sb.append("  District: ").append(v.district).append("\n");
                }
                sb.append("  Address: ").append(v.address).append("\n");
                if (v.comments != null && !v.comments.isEmpty() && !v.comments.equals("null")) {
                    sb.append("  Notes: ").append(v.comments).append("\n");
                }
            }
        }

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, sb.toString());
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, "Share Report via");
        startActivity(shareIntent);
    }

    // --- Data Models for RecyclerView ---

    static class HeaderItem {
        String planCode, username, team, district, dateRange;
        HeaderItem(String pc, String u, String t, String d, String dr) {
            this.planCode = pc; this.username = u; this.team = t; this.district = d; this.dateRange = dr;
        }
    }

    static class SummaryItem {
        String totalVisits, totalFir, totalImages;
        SummaryItem(String v, String f, String i) {
            this.totalVisits = v; this.totalFir = f; this.totalImages = i;
        }
    }

    static class SectionHeaderItem {
        String title;
        SectionHeaderItem(String t) { this.title = t; }
    }

    static class VisitItem {
        String name, finalId, date, address, comments, district;
        VisitItem(String n, String fi, String d, String a, String c, String dist) {
            this.name = n; this.finalId = fi; this.date = d; this.address = a; this.comments = c; this.district = dist;
        }
    }

    // --- RecyclerView Adapter ---

    private class ReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int TYPE_HEADER = 0;
        private static final int TYPE_SUMMARY = 1;
        private static final int TYPE_SECTION = 2;
        private static final int TYPE_VISIT = 3;

        private List<Object> items;

        ReportAdapter(List<Object> items) { this.items = items; }

        @Override
        public int getItemViewType(int position) {
            Object item = items.get(position);
            if (item instanceof HeaderItem) return TYPE_HEADER;
            if (item instanceof SummaryItem) return TYPE_SUMMARY;
            if (item instanceof SectionHeaderItem) return TYPE_SECTION;
            return TYPE_VISIT;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            if (viewType == TYPE_HEADER) return new HeaderViewHolder(inflater.inflate(R.layout.item_report_header, parent, false));
            if (viewType == TYPE_SUMMARY) return new SummaryViewHolder(inflater.inflate(R.layout.item_report_summary, parent, false));
            if (viewType == TYPE_SECTION) return new SectionViewHolder(inflater.inflate(R.layout.item_report_section_header, parent, false));
            return new VisitViewHolder(inflater.inflate(R.layout.item_report_visit, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Object item = items.get(position);
            if (holder instanceof HeaderViewHolder) {
                HeaderItem h = (HeaderItem) item;
                ((HeaderViewHolder) holder).planId.setText(h.planCode);
                ((HeaderViewHolder) holder).userName.setText("User: " + h.username);
                ((HeaderViewHolder) holder).team.setText("Team: " + h.team);
                ((HeaderViewHolder) holder).district.setText("District: " + h.district);
                ((HeaderViewHolder) holder).dates.setText("Period: " + h.dateRange);
            } else if (holder instanceof SummaryViewHolder) {
                SummaryItem s = (SummaryItem) item;
                ((SummaryViewHolder) holder).visits.setText("Total Visits: " + s.totalVisits);
                ((SummaryViewHolder) holder).fir.setText("Total FIRs: " + s.totalFir);
                ((SummaryViewHolder) holder).images.setText("Total Images: " + s.totalImages);
            } else if (holder instanceof SectionViewHolder) {
                ((SectionViewHolder) holder).title.setText(((SectionHeaderItem) item).title);
            } else if (holder instanceof VisitViewHolder) {
                VisitItem v = (VisitItem) item;
                ((VisitViewHolder) holder).name.setText(v.name);
                ((VisitViewHolder) holder).id.setText("Final ID: " + v.finalId);
                ((VisitViewHolder) holder).date.setText("Date: " + v.date);
                if (v.district != null && !v.district.equalsIgnoreCase("null") && !v.district.isEmpty()) {
                    ((VisitViewHolder) holder).address.setText("District: " + v.district + "\nAddress: " + v.address);
                } else {
                    ((VisitViewHolder) holder).address.setText("Address: " + v.address);
                }
                if (v.comments != null && !v.comments.equals("null") && !v.comments.isEmpty()) {
                    ((VisitViewHolder) holder).comments.setVisibility(View.VISIBLE);
                    ((VisitViewHolder) holder).comments.setText("Notes: " + v.comments);
                } else {
                    ((VisitViewHolder) holder).comments.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public int getItemCount() { return items.size(); }

        class HeaderViewHolder extends RecyclerView.ViewHolder {
            TextView planId, userName, team, district, dates;
            HeaderViewHolder(View v) {
                super(v);
                planId = v.findViewById(R.id.report_plan_id);
                userName = v.findViewById(R.id.report_user_name);
                team = v.findViewById(R.id.report_team);
                district = v.findViewById(R.id.report_district);
                dates = v.findViewById(R.id.report_dates);
            }
        }

        class SummaryViewHolder extends RecyclerView.ViewHolder {
            TextView visits, fir, images;
            SummaryViewHolder(View v) {
                super(v);
                visits = v.findViewById(R.id.summary_total_visits);
                fir = v.findViewById(R.id.summary_total_fir);
                images = v.findViewById(R.id.summary_total_images);
            }
        }

        class SectionViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            SectionViewHolder(View v) { super(v); title = v.findViewById(R.id.section_title); }
        }

        class VisitViewHolder extends RecyclerView.ViewHolder {
            TextView name, id, date, address, comments;
            VisitViewHolder(View v) {
                super(v);
                name = v.findViewById(R.id.visit_hce_name);
                id = v.findViewById(R.id.visit_id);
                date = v.findViewById(R.id.visit_date);
                address = v.findViewById(R.id.visit_address);
                comments = v.findViewById(R.id.visit_comments);
            }
        }
    }
}
