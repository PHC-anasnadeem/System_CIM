package com.phc.cim.Extra;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import com.phc.cim.Managers.DataManager;
import com.phc.cim.R;


public class Vist_Memb_Fragment extends Fragment {


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
       View objview= inflater.inflate(R.layout.vist_memb_fragment, container, false);
        Button member= (Button) objview.findViewById(R.id.Member);

        BottomNavigationView navigation = (BottomNavigationView) objview.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        member.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                RegistrationFragment registrationFragment = new RegistrationFragment();  //this is your new fragment.
                FragmentManager fragmentManager1 = ((FragmentActivity) getContext()).getSupportFragmentManager();
                fragmentManager1.beginTransaction().add(R.id.container1, registrationFragment).addToBackStack(null).commit();

            }
        });
        Button visitor= (Button) objview.findViewById(R.id.Visitor);
        visitor.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Toast.makeText(getContext(), "Loading...", Toast.LENGTH_SHORT).show();
               DataManager webApiManager = new DataManager(getContext());
                Intent firstpage= new Intent(getContext(), MapsActivity.class);
                //mTextMessage.setText(R.string.title_dashboard);
                firstpage.putExtra("email",email);
                getContext().startActivity(firstpage);



            }
        });

        return objview;
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
          /*      case R.id.navigation_home:

                    IntentIntegrator intentIntegrator=new IntentIntegrator(getActivity());
                    intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                    intentIntegrator.setPrompt("scan");
                    intentIntegrator.setCameraId(0);
                    intentIntegrator.setBeepEnabled(false);
                    intentIntegrator.setBarcodeImageEnabled(false);
                    intentIntegrator.initiateScan();
                    // firstpage.putExtra("stop",result);
                    // firstpage.putExtra("des_latitude",getDes_latitude());
                    // firstpage.putExtra("des_longitude",getDes_longitude());


                    //mapsActivity.startActivity(firstpage);

                    //mTextMessage.setText(R.string.title_home);
                    return true;*/
                case R.id.navigation_dashboard:
                    LocateUsFragment locateUsFragment = new LocateUsFragment();  //this is your new fragment.
                    FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
                    fragmentManager.beginTransaction().add(R.id.container1, locateUsFragment).addToBackStack(null).commit();
                    return true;
                case R.id.navigation_notifications:
                    FAQsFragment faqFragment = new FAQsFragment();  //this is your new fragment.
                    FragmentManager fragmentManager1 = ((FragmentActivity) getContext()).getSupportFragmentManager();
                    fragmentManager1.beginTransaction().add(R.id.container1, faqFragment).addToBackStack(null).commit();

                    return true;
            }
            return false;
        }


    };
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult intentResult= IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (intentResult!=null){


            if (intentResult.getContents()==null) {
                Toast.makeText(getContext(), "You Cancelled the scanning", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getContext(), intentResult.getContents(), Toast.LENGTH_LONG).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
