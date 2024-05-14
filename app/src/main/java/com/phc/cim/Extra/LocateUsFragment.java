package com.phc.cim.Extra;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.phc.cim.Adapters.FAQsAdapter;
import com.phc.cim.R;


public class LocateUsFragment extends Fragment {

    ListView list_view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View obj_view= inflater.inflate(R.layout.fragment_locate_us, container, false);
        list_view = (ListView) obj_view.findViewById(R.id.locateus);
        String[] locateus= {"Head Office" + "\n" + "\n" +
                        "Punjab Healthcare Commission\n" +
                        "Office # 1&2,4th Floor, Shaheen Complex. \n" +
                        "38- Abbot Road, Lahore, Pakistan  \n",
                        "Regional Office Rawalpindi" + "\n" + "\n" +
                        "Punjab Healthcare Commission\n" +
                        "1st Floor, Rani Arcade, 1001/B Satellite Town , Haidery Chowk, \n" +
                        "Rawalpindi, Pakistan" + "\n",
                        "Regional Office Multan" + "\n" + "\n" +
                        "Punjab Healthcare Commission\n" +
                        "House No. 27/1, Gulshan-e-Madina Colony, Near Raza Abad Chowk, Suraj Miyani Road, Multan, Pakistan " + "\n",
                        "Regional Office Bahawalpur" + "\n" + "\n" +
                        "Punjab Healthcare Commission\n" +
                        "House No. 1-C, Block A, Model Town, Rashid Minhas Road,\n" +
                        "Bahawalpur, Pakistan " + "\n",
                        "Regional Office Sargodha" + "\n" + "\n" +
                        "Punjab Healthcare Commission\n" +
                        "159 Umar Park, Extension 47-NB, \n" +
                        "Sargodha, Pakistan " + "\n",
        };


        FAQsAdapter adapter = new FAQsAdapter(getContext(),locateus);
        list_view.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return obj_view;
    }

}
