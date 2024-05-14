package com.phc.cim.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.phc.cim.Adapters.QuackClusterAdapter;
import com.phc.cim.R;

import java.util.ArrayList;
import java.util.HashMap;


public class QuackClusterListFragment extends Fragment {

    String jsonStr = null;
    ListView listView;
    ArrayList<HashMap<String, String>> indtabresult;
    ArrayList<HashMap<String, String>> functionsealedlist;
    String FunctionalSealedID;
    String email,password,username,isEdit,districtText,TehsilText;
    ProgressDialog pDialog;
    int count=1;
    private OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.quack_culuster_list, container, false);

        pDialog = new ProgressDialog(getActivity());

        listView = (ListView) rootView.findViewById(R.id.list);
        TextView textView=(TextView) rootView.findViewById(R.id.distTeh_name);
        final Bundle args = getArguments();
        indtabresult= (ArrayList<HashMap<String, String>>) args.getSerializable("indtabresult");
        email= args.getString("email");
        password= args.getString("password");
        username= args.getString("username");
        isEdit= args.getString("isEdit");
        districtText= args.getString("districtText");
        TehsilText= args.getString("tehsilText");
        textView.setText("District: "+districtText+", Tehsil: "+TehsilText);
        ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);
        QuackClusterAdapter quackClusterAdapter =new QuackClusterAdapter(getContext(),indtabresult,email,password,username,isEdit,viewPager,mListener);
        listView.setAdapter(quackClusterAdapter);
        quackClusterAdapter.notifyDataSetChanged();

        return rootView;
    }
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String lat, String lng);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
