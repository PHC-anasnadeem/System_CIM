package com.phc.cim.Extra;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.phc.cim.Adapters.FAQsAdapter;
import com.phc.cim.R;


public class FAQsFragment extends Fragment {
    ListView list_view;
   // ArrayList<Category> category=null;
  //  private DataManager dataManager;
    //private WebApiManager webApiManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View obj_view = inflater.inflate(R.layout.fragment_faqs, container,false);
        list_view = (ListView) obj_view.findViewById(R.id.faqs);
        String[] faqs= {"Q. What is a Healthcare Establishment (District)?"+ "\n" + "\n" +
                                "Ans. A Healthcare Establishment means a hospital, diagnostic center, medical clinic, nursing home, maternity home, dental clinic, homeopathic clinic, tibb clinic, acupuncture clinic, physiotherapy clinic or any other premises or conveyance wholly or partly used for providing healthcare services."+ "\n",
                        "Q. What is Licensing of Health Care Establishments (HCEs)?" + "\n" + "\n" +
                                "Ans. \tLicensing of HCEs is a grant of permission issued to a District by the Punjab Healthcare Commission (PHC) under the PHC Act 2010 for the use of any premises or conveyance as a Healthcare Establishment."+ "\n",
                        "Q. Is every District required to obtain a License?"+ "\n" + "\n" +
                                "Ans. Yes. All HCEs (as described above) in Punjab, both in the public and private sectors, are required to obtain a license from the Commission."+ "\n",
                        "Q. What are the benefits/incentives of Licensing for a District?"+ "\n" + "\n" +
                                "Ans.Licensing certifies that a given District is allowed to operate as a professionally safe place, promising elimination or substantial reduction in the number and severity of system failures, enhancing patient and practitioner confidence in the District thereby raising its profile, professional value, clientele and quality of healthcare services. Licensing will also ensure that the Commission takes cognizance regarding any case of harassment of a healthcare service provider or damage to a District."+ "\n",
                        "Q. What is Registration and what does it entail?"+ "\n" + "\n" +
                                "Ans. Registration: It is a process of enlistment of a healthcare service provider with the Commission. Application for registration is made to the Commission on a Prescribed Form, which can be downloaded from the PHC website or obtained in person from the PHC office. Upon acceptance of application, the Registration Certificate is issued in approximately two weeks but it may take longer depending upon the workload, in which case the registration is deemed to have taken place provisionally."+ "\n"};





        FAQsAdapter adapter = new FAQsAdapter(getContext(),faqs);
        list_view.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return obj_view;
    }
}
