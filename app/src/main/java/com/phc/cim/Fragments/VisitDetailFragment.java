package com.phc.cim.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.phc.cim.R;

import java.util.HashMap;

public class VisitDetailFragment extends Fragment {

    HashMap<String, String> visitDetail;

    public VisitDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_visit_detail, container, false);

        TextView tvActionType = rootView.findViewById(R.id.tv_action_type);
        TextView tvSubAction = rootView.findViewById(R.id.tv_sub_action);
        TextView tvVisitedDate = rootView.findViewById(R.id.tv_visited_date);
        TextView tvVisitedBy = rootView.findViewById(R.id.tv_visited_by);
        TextView tvFirSubmit = rootView.findViewById(R.id.tv_fir_submit);
        TextView tvComments = rootView.findViewById(R.id.tv_comments);

        Bundle args = getArguments();
        if (args != null) {
            visitDetail = (HashMap<String, String>) args.getSerializable("visitDetail");
        }

        if (visitDetail != null) {
            tvActionType.setText(visitDetail.get("Action"));
            tvSubAction.setText(visitDetail.get("SubAction"));
            tvVisitedDate.setText(visitDetail.get("VisitedDate"));
            tvVisitedBy.setText(visitDetail.get("UserName"));
            
            String fir = visitDetail.get("isFIRSubmit");
            if ("1".equals(fir)) {
                tvFirSubmit.setText("Yes");
            } else if ("0".equals(fir)) {
                tvFirSubmit.setText("No");
            } else {
                tvFirSubmit.setText("N/A");
            }
            
            String comments = visitDetail.get("Comments");
            if (comments != null && !comments.isEmpty()) {
                tvComments.setText(comments);
            } else {
                tvComments.setText("No comments.");
            }
        }

        return rootView;
    }
}
