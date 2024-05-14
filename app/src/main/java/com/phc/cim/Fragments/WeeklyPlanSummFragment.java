package com.phc.cim.Fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.phc.cim.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;


public class WeeklyPlanSummFragment extends Fragment {


    String email,password,username,isEdit,PlanTitle,PlanStartDate,PlanEndDate,strtdatetext,enddatetext,Vistdate;
    Bundle args;
    Spinner date_spinner;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ArrayList<HashMap<String, String>> indtabresult;
    String FunctionalSealed,CloseSealed,NotSealed;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.weeklyplanfragment_tabs, container, false);



        args = getArguments();

        email= args.getString("email");
        password = args.getString("password");
        username = args.getString("username");
        isEdit = args.getString("isEdit");
        PlanTitle = args.getString("PlanTitle");
        PlanStartDate= args.getString("PlanStartDate");
        PlanEndDate= args.getString("PlanEndDate");
        FunctionalSealed = args.getString("FunctionalSealed");
        CloseSealed= args.getString("CloseSealed");
        NotSealed= args.getString("NotSealed");
        indtabresult= (ArrayList<HashMap<String, String>>) args.getSerializable("indtabresult");
        date_spinner = (Spinner) rootView.findViewById(R.id.date_spinner);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);

        String ackwardDate = PlanStartDate;
        String ackwardRipOff = ackwardDate.replace("/Date(", "").replace("+0500", "").replace(")/", "");
        Long timeInMillis = Long.valueOf(ackwardRipOff);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        Date d= new Date(timeInMillis);
        strtdatetext=new SimpleDateFormat("dd-MMM-yyyy").format(calendar.getTime()).toString();

        String ackwardDate2 = PlanEndDate;
        String ackwardRipOff2 = ackwardDate2.replace("/Date(", "").replace("+0500", "").replace(")/", "");
        Long timeInMillis2 = Long.valueOf(ackwardRipOff2);
        calendar.setTimeInMillis(timeInMillis2);
        Date d2= new Date(timeInMillis2);
        enddatetext=new SimpleDateFormat("dd-MMM-yyyy").format(calendar.getTime()).toString();
        Date stdate = new Date(timeInMillis);
        Date endate = new Date(timeInMillis2);
        ArrayAdapter<String> dateadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getDatesBetween(stdate,endate)) {
            public View getView(int position, View convertView, ViewGroup parent) {
                // Cast the spinner collapsed item (non-popup item) as a text view
                TextView tv = (TextView) super.getView(position, convertView, parent);

                // Set the text color of spinner item
                //  tv.setTextColor(Color.WHITE);

                // Return the view
                return tv;
            }


            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return true;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) super.getDropDownView(position,convertView,parent);

                // Set the text color of drop down items
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    tv.setTextColor(Color.BLACK);
                }else {
                    tv.setTextColor(Color.WHITE);
                }
                if (position == 0) {
                    // Set the hint text color gray
                /*    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        tv.setTextColor(Color.BLACK);
                    }else {
                        tv.setTextColor(Color.WHITE);
                    }*/
                } else {
                    //convertView.setBackgroundColor(Color.WHITE);
                    //((TextView) parent.getChildAt(position)).setTextColor(Color.WHITE);
                /*    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        tv.setTextColor(Color.BLACK);
                    }else {
                        tv.setTextColor(Color.WHITE);
                    }*/
                }
                return tv;
            }
        };
        dateadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            date_spinner.setPopupBackgroundResource(R.drawable.spinner);
        }
        date_spinner.setAdapter(dateadapter);
        date_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
               ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                Vistdate = parent.getItemAtPosition(position).toString();
                DateFormat formatter;
                if (Vistdate != null && Vistdate!="All") {
                    formatter = new SimpleDateFormat("dd-MMM-yyyy");
                    Date date2 = null;
                    try {
                        date2 = (Date) formatter.parse(Vistdate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    SimpleDateFormat newFormat = new SimpleDateFormat("M/d/yyyy");
                    if (date2 != null)
                        Vistdate = newFormat.format(date2);

                }
                args.putString("Vistdate", Vistdate);
                viewPager.setOffscreenPageLimit(3);
                setupViewPager(viewPager);
                tabLayout.setupWithViewPager(viewPager);
                tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#00a652"));
                tabLayout.setSelectedTabIndicatorHeight((int) (3 * getResources().getDisplayMetrics().density));
                tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#00a652"));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return rootView;
    }

    public ArrayList<String> getDatesBetween(
            Date startDate, Date endDate) {

        List<Date> datesInRange = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(endDate);
        ArrayList<String> counciltypelist = new ArrayList<String>();
        counciltypelist.add("All");
        while (calendar.before(endCalendar)) {
            Date result = calendar.getTime();
            SimpleDateFormat newFormat = new SimpleDateFormat("dd-MMM-yyyy");
            String finalString = "";
            if (result != null)
                finalString = newFormat.format(result);
            counciltypelist.add(finalString);
            datesInRange.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        counciltypelist.add(enddatetext);
        return counciltypelist;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(),args);
        adapter.addFragment(new FunSealDaywiseFragment(), "Functional Sealed");
        adapter.addFragment(new CloSealDaywiseFragment(), "Close Sealed");
        adapter.addFragment(new NotSealDaywiseFragment(), "Sealing Not Required");


        // adapter.addFragment(new ThreeFragment(), "THREE");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private final Bundle fragmentBundle;

        public ViewPagerAdapter(FragmentManager manager, Bundle bundle) {
            super(manager);
            fragmentBundle=bundle;
        }

        @Override
        public Fragment getItem(int position) {
            //final MapFragment f = new MapFragment();
            //f.setArguments(this.fragmentBundle);
            mFragmentList.get(position).setArguments(fragmentBundle);
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
