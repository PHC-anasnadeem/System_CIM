package com.phc.cim.Extra;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.phc.cim.R;

public class FilterFragment extends Fragment {
    Spinner sectortypespinner;
    Spinner hcetypespinner;
    Spinner counciltypespiner;
    String sectortypetext;
    String counciltypetext;
    String hceTypetext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View objView = inflater.inflate(R.layout.fragment_filter, container, false);
        sectortypespinner = (Spinner) objView.findViewById(R.id.Sector_Type);
        String[] countype =
                {"Select Sector Type", "Public", "Private"};

        ArrayAdapter<String> countypeadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, countype) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);

                if (position == 0) {
                    // Set the hint text color gray
                    //tv.setTextColor(Color.GRAY);
                } else {
                    // tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        countypeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sectortypespinner.setAdapter(countypeadapter);
        sectortypespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                sectortypetext = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        counciltypespiner = (Spinner) objView.findViewById(R.id.Council_Typespinner);
        String[] councilype =
                {"Select Sector Type", "NCH", "NCT","PMDC", "PNC"};

        ArrayAdapter<String> counciltypeadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, countype) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);

                if (position == 0) {
                    // Set the hint text color gray
                    //tv.setTextColor(Color.GRAY);
                } else {
                    // tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        counciltypeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        counciltypespiner.setAdapter(counciltypeadapter);
        counciltypespiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                counciltypetext = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        hcetypespinner = (Spinner) objView.findViewById(R.id.HCE_Typespinner);
        String[] hcsptype =
                {"Select District Type",
                        "Single Specialty",
                        "Hakim",
                        "Homeopath",
                        "Hair Transplant",
                        "Physiotherapist",
                        "Hospital,Rehabilitation_ Physiotherapy Dept.",
                        "Addiction Treatment Center",
                        "BHU",
                        "Midwifery Clinic",
                        "Single Speciality",
                        "Homeopath,HOMEOPATH",
                        "BHU,Basic Health Unit jabbi",
                        "Lab",
                        "GP,single man clinic",
                        "SSMC",
                        "PESSI",
                        "Imaging Lab",
                        "Dialysis Center",
                        "Dentist",
                        "Homeopath,optometry refrectionist",
                        "FHC",
                        "Teaching",
                        "Poly Clinic",
                        "FP Clinic",
                        "Gyne Clinic",
                        "Main Lab",
                        "Dispensary",
                        "MCHC",
                        "Advance Imaging Lab",
                        "Pathalogy + Advance Imaging Lab",
                        "THQ",
                        "DHQ/Teaching",
                        "Hospital",
                        "SSEC",
                        "LHV Clinic",
                        "GRD",
                        "Mobile Health Clinic",
                        "Nursing Home",
                        "Homeopathic clinic",
                        "Cosmetic Surgery Clinic",
                        "GP",
                        "RHC",
                        "Maternity Home",
                        "Non-Teaching",
                        "Multiple Specialty,Psychotharepies and Homeopathy",
                        "DHQ",
                        "Homeopath,homeopathic clinic",
                        "PWD",
                        "SSD",
                        "Collection Centre",
                        "Acupuncturist",
                        "Pathalogy + Imaging Lab",
                        "Multiple Specialty",
                        "Homeopath,Homoeopathic Clinic",
                        "Collection Center",
                        "Pathalogy Lab",
                        "Wapda",
                        "CMW Clinic",
                        "Dental Hyginist"
                };

        ArrayAdapter<String> hcetypeadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, hcsptype) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);

                if (position == 0) {
                    // Set the hint text color gray
                    //tv.setTextColor(Color.GRAY);
                } else {
                    // tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        hcetypeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hcetypespinner.setAdapter(hcetypeadapter);
        hcetypespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                hceTypetext = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return objView;

    }


}
