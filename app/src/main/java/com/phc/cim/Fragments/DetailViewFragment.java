package com.phc.cim.Fragments;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.phc.cim.Activities.Aqc.ActionActivity;
import com.phc.cim.Activities.Common.BasicInfroHistoryActivity;
import com.phc.cim.Activities.GalleryActivity;
import com.phc.cim.Adapters.ImageAdapter;
import com.phc.cim.Adapters.MyCustomPagerAdapter;
import com.phc.cim.DataElements.CouncilType;
import com.phc.cim.DataElements.District;
import com.phc.cim.DataElements.OrgType;
import com.phc.cim.DataElements.SectorType;
import com.phc.cim.DataElements.UpdateStatL1;
import com.phc.cim.DataElements.UpdateStatL2;
import com.phc.cim.Managers.DataManager;
import com.phc.cim.Others.CurrentLocation;
import com.phc.cim.R;

//import junit.framework.Assert;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


import android.graphics.Bitmap;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;

public class DetailViewFragment extends Fragment {
    public static final String TAG = "GalleryActivity";
    public static final String EXTRA_NAME = "images";

    private ArrayList<String> _images;
    private GalleryPagerAdapter _adapter;

    ViewPager _pager;
    LinearLayout _thumbnails;
   /* @BindView(R.id.btn_close) ImageButton _closeButton;*/

    ArrayList<String> mylist;
    double des_lat;
    double des_lng;
    DataManager dataManager;
    String MID;
    String MText;
    String jsonStr;
    CurrentLocation gps;
    EditText hce_nameEdit;
    EditText AddressEdit;
    EditText HCSP_nameEdit;
    EditText HCSP_SOEdit;
    EditText CNIC_Edit;
    EditText HCSP_ContactEdit;
    EditText Reg_NoEdit;
    EditText coun_NoEdit;
    EditText coments;

    TextInputLayout regNoInput;
    TextInputLayout counTypeInput;
    TextInputLayout counNoInput;
    TextInputLayout substatusInput;

    EditText district_spinner;
    EditText sectortypespinner;
    EditText hcetypespinner;
    EditText HCSP_spinner;
    EditText regStatus_spinner;
    EditText counStatus_spinner;
    EditText counciltypespiner;
    EditText updatestatusspinner;
    EditText substatusspinner;
    EditText bedsEdit;
    TextView vistedtext,updatetext;
    String hce_nameText = "";
    String AddressText = "";
    String HCSP_nameText = "";
    String HCSP_SOText = "";
    String CNIC_Text = "";
    String HCSP_ContactText = "";
    String Reg_NoText = "";
    String coun_NoText = "";
    String comnt = "";
    String final_id = "";
    String RegType = "";
    String password;
    String isEdit;
    String username;
    String ImagePath;
    String FinalID;

    String districtText = "";
    String sectortypetext = "";
    String hceTypetext = "";
    String HCSPTypeText = "";
    String RegstatusText = "";
    String counStatusText = "";
    String counciltypetext = "";
    String LastVisitedDate = "";
    String UserName = "";
    String counStatusID = "";
    String RegstatusID = "";
    String RecordLockedForUpdate = "";
    String total_beds = "";
    double latitude;
    double longitude;
    String email = "";
    double cur_latitude;
    double cur_longitude;

    ArrayList<District> districts;
    ArrayList<SectorType> sectorTypes;
    ArrayList<CouncilType> councilTypes;
    ArrayList<OrgType> orgTypes;
    ArrayList<UpdateStatL1> updateStatL1s;
    ArrayList<UpdateStatL2> updateStatL2s;


    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_PHOTOS = "photos";
    private static final String TAG_MOVIES = "videos";
    private static final String TAG_NOTIFICATIONS = "notifications";
    private static final String TAG_SETTINGS = "settings";
    public static String CURRENT_TAG = TAG_HOME;
    // toolbar titles respected to selected nav menu item
    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private static final String LOG_TAG = "AppUpgrade";
    private String activityTitles;
    private int versionCode = 0;
    String date;
    String appURI = "";
    String time1;
    MyCustomPagerAdapter myCustomPagerAdapter;
    String index;
    private DownloadManager downloadManager;
    private long downloadReference;
    private GridView gridView;
    private ArrayList<String> imageUrls;
    private ImageAdapter imageAdapter;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    // private GoogleApiClient client;
    //private static final String urlNavHeaderBg = "http://api.androidhive.info/images/nav-menu-header-bg.jpg";
    //private static final String urlProfileImg = "https://lh3.googleusercontent.com/eCtE_G34M9ygdkmOpYvCag1vBARCmZwnVS6rS5t4JLzJ6QgQSBquM0nuTsCpLhYbKljoyS-txg";
    public static int navItemIndex = 0;






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View objView = inflater.inflate(R.layout.activity_detail_view, container, false);

        ButterKnife.bind(getActivity());
        _pager = (ViewPager) objView.findViewById(R.id.pager);
        _thumbnails = (LinearLayout) objView.findViewById(R.id.thumbnails);
        dataManager = new DataManager(getContext());
        gps = new CurrentLocation(getContext());
        hce_nameEdit = (EditText) objView.findViewById(R.id.hce_name);
        AddressEdit = (EditText) objView.findViewById(R.id.Address);
        HCSP_nameEdit = (EditText) objView.findViewById(R.id.HCSP_Name);
        HCSP_SOEdit = (EditText) objView.findViewById(R.id.HCSP_SO);
        CNIC_Edit = (EditText) objView.findViewById(R.id.CNIC);
        HCSP_ContactEdit = (EditText) objView.findViewById(R.id.Mobile);
        Reg_NoEdit = (EditText) objView.findViewById(R.id.reg_no);
        coun_NoEdit = (EditText) objView.findViewById(R.id.council_no);
        district_spinner = (EditText) objView.findViewById(R.id.district);
        sectortypespinner = (EditText) objView.findViewById(R.id.Sector_Type);
        hcetypespinner = (EditText) objView.findViewById(R.id.hcetype);
        HCSP_spinner = (EditText) objView.findViewById(R.id.HCSPType);
        bedsEdit = (EditText) objView.findViewById(R.id.beds);
        regStatus_spinner = (EditText) objView.findViewById(R.id.registration);
        counStatus_spinner = (EditText) objView.findViewById(R.id.council_reg);
        counciltypespiner = (EditText) objView.findViewById(R.id.counType);
        //coments = (EditText) findViewById(R.id.comments);
        regNoInput = (TextInputLayout) objView.findViewById(R.id.reg);
        counTypeInput = (TextInputLayout) objView.findViewById(R.id.counclType);
        counNoInput = (TextInputLayout) objView.findViewById(R.id.council);
        substatusInput = (TextInputLayout) objView.findViewById(R.id.substatus);
        vistedtext = (TextView) objView.findViewById(R.id.vistedtext);
        updatetext = (TextView) objView.findViewById(R.id.updatetext);


        hce_nameEdit.setEnabled(false);
        AddressEdit.setEnabled(false);
        HCSP_nameEdit.setEnabled(false);
        HCSP_SOEdit.setEnabled(false);
        CNIC_Edit.setEnabled(false);
        HCSP_ContactEdit.setEnabled(false);
        Reg_NoEdit.setEnabled(false);
        coun_NoEdit.setEnabled(false);
        bedsEdit.setEnabled(false);

        district_spinner.setEnabled(false);
        sectortypespinner.setEnabled(false);
        hcetypespinner.setEnabled(false);
        HCSP_spinner.setEnabled(false);
        regStatus_spinner.setEnabled(false);
        counStatus_spinner.setEnabled(false);
        counciltypespiner.setEnabled(false);



        final Bundle args = getArguments();
        hce_nameText = args.getString("HCEName");
        AddressText = args.getString("HCEAddress");
        districtText = args.getString("District");
        sectortypetext = args.getString("SectorType");
        hceTypetext = args.getString("OrgType");
        HCSPTypeText = args.getString("HCSPType");
        HCSP_nameText = args.getString("HCSPName");
        HCSP_SOText = args.getString("HCSPSO");
        CNIC_Text = args.getString("HCSPCNIC");
        HCSP_ContactText = args.getString("HCSPContactNo");
        RegstatusText = args.getString("RegStatus");
        Reg_NoText = args.getString("RegNum");
        counStatusText = args.getString("CouncilStatus");
        counciltypetext = args.getString("CouncilName");
        coun_NoText = args.getString("CouncilNum");
        RegType = args.getString("RegType");
        total_beds = args.getString("total_beds");
        RecordLockedForUpdate = args.getString("RecordLockedForUpdate");
        final_id = args.getString("final_id");
        latitude = args.getDouble("latitude");
        longitude = args.getDouble("longitude");
        email = args.getString("email");
        password = args.getString("password");
        username = args.getString("username");
        isEdit = args.getString("isEdit");
        index = args.getString("index");
        UserName = args.getString("UserName");
        LastVisitedDate = args.getString("LastVisitedDate");
        _images = args.getStringArrayList("imageurls");
        if(LastVisitedDate==null){
            LastVisitedDate="null";
        }
        if(counStatusText==null){
            counStatusText="";
        }
        if(RegstatusText==null){
            RegstatusText="";
        }
          //  Assert.assertNotNull(_images);
        _adapter = new GalleryPagerAdapter(getActivity());
        _pager.setAdapter(_adapter);
        _pager.setOffscreenPageLimit(6); // how many images to load into memory

       /* _closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Close clicked");
                finish();
            }
        });*/
        // substatusInput.setVisibility(View.GONE);
        //hce_nameEdit.setHint(index+" HCE Name");
        hce_nameEdit.setText(index + ". "+final_id +" - "+ hce_nameText);
        if(LastVisitedDate.equals("null")) {
            vistedtext.setText("Last updated by: "+UserName);

        }
        else {
            String ackwardDate = LastVisitedDate;
            Calendar calendar = Calendar.getInstance();
            String ackwardRipOff = ackwardDate.replace("/Date(", "").replace("+0500", "").replace(")/", "");
            Long timeInMillis = Long.valueOf(ackwardRipOff);
            calendar.setTimeInMillis(timeInMillis);
            date = new SimpleDateFormat("dd-MMM-yyyy").format(calendar.getTime()).toString();
            vistedtext.setText("Last updated by: "+UserName +", Dated: "+ date);
        }

        AddressEdit.setText(AddressText);
        HCSP_nameEdit.setText(HCSP_nameText);
        HCSP_SOEdit.setText(HCSP_SOText);
        CNIC_Edit.setText(CNIC_Text);
        HCSP_ContactEdit.setText(HCSP_ContactText);
        Reg_NoEdit.setText(Reg_NoText);
        coun_NoEdit.setText(coun_NoText);
        bedsEdit.setText(total_beds);
        district_spinner.setText(districtText);
        sectortypespinner.setText(sectortypetext);
        hcetypespinner.setText(hceTypetext);
        HCSP_spinner.setText(HCSPTypeText);
        regStatus_spinner.setText(RegstatusText);
        counStatus_spinner.setText(counStatusText);
        counciltypespiner.setText(counciltypetext);

        regNoInput.setVisibility(View.GONE);
        counTypeInput.setVisibility(View.GONE);
        counNoInput.setVisibility(View.GONE);
        if (counStatusText.equals("Yes")) {
            counTypeInput.setVisibility(View.VISIBLE);
            counNoInput.setVisibility(View.VISIBLE);
        } else {
            counTypeInput.setVisibility(View.GONE);
            counNoInput.setVisibility(View.GONE);
        }
        if (RegstatusText.equals("Yes")) {
            regNoInput.setVisibility(View.VISIBLE);
        } else {
            regNoInput.setVisibility(View.GONE);
        }


        updatetext.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                Intent firstpage = new Intent(getContext(), BasicInfroHistoryActivity.class);
                firstpage.putExtra("hce_nameText",hce_nameText);
                firstpage.putExtra("final_id",final_id);
                startActivity(firstpage);
            }
        });


        setupGridView(objView);


        //-------------------------Current Location----------------------------
        if (gps.canGetLocation()) {
            cur_latitude = gps.getLatitude();
            cur_longitude = gps.getLongitude();
            // latlangListener.onlatlang(cur_latitude, cur_longitude);
            if (cur_latitude != 0.0 && cur_longitude != 0.0) {

            } else {
                cur_latitude = gps.getLatitude();
                cur_longitude = gps.getLongitude();


            }
        } else {
            // Can't get location.
            // GPS or network is not enabled.
            // Ask user to enable GPS/network in settings.
            gps.showSettingsAlert();
        }




        return objView;
    }

    private void setupGridView(View view) {
        gridView = view.findViewById(R.id.image_grid_view);
        imageUrls = new ArrayList<>();
        imageAdapter = new ImageAdapter(getContext(), imageUrls);
        gridView.setAdapter(imageAdapter);


        fetchImages(final_id);
    }

    private void fetchImages(String final_id) {
        String baseurl = requireContext().getResources().getString(R.string.baseurl);
        String url = baseurl + "GetCensusAttachmentList?FinalID=" + final_id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        imageUrls.clear();

                        try {
                            // Assuming the API returns an object with an image URL field
                            String imageUrl = response.getString("File_Path");
                            imageUrls.add(imageUrl); // Add the image URL to the list
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        imageAdapter.notifyDataSetChanged(); // Notify adapter of data change
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the RequestQueue.
        Volley.newRequestQueue(getContext()).add(jsonObjectRequest);
    }


    public void loadImage(String imageUrl, ImageView imageView) {
        // Use Glide to load images with error handling
        Glide.with(getContext())
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)  // Placeholder while loading
                .error(R.drawable.error)               // Error image if loading fails
                .into(imageView);
    }


    public class GalleryPagerAdapter extends PagerAdapter {

        Context _context;
        LayoutInflater _inflater;

        public GalleryPagerAdapter(Context context) {
            _context = context;
            _inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return _images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = _inflater.inflate(R.layout.pager_gallery_item, container, false);
            container.addView(itemView);

            // Get the border size to show around each image
            int borderSize = _thumbnails.getPaddingTop();

            // Get the size of the actual thumbnail image
            int thumbnailSize = ((FrameLayout.LayoutParams)
                    _pager.getLayoutParams()).bottomMargin - (borderSize*2);

            // Set the thumbnail layout parameters. Adjust as required
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(thumbnailSize, thumbnailSize);
            params.setMargins(0, 0, borderSize, 0);

            // You could also set like so to remove borders
            //ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
            //        ViewGroup.LayoutParams.WRAP_CONTENT,
            //        ViewGroup.LayoutParams.WRAP_CONTENT);

            final ImageView thumbView = new ImageView(_context);
            thumbView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            thumbView.setLayoutParams(params);
            thumbView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Thumbnail clicked");

                    // Set the pager position when thumbnail clicked
                    _pager.setCurrentItem(position);
                }
            });
            _thumbnails.addView(thumbView);

            final SubsamplingScaleImageView imageView =
                    (SubsamplingScaleImageView) itemView.findViewById(R.id.image);

            // Asynchronously load the image and set the thumbnail and pager view
            Glide.with(_context)
                    .load(_images.get(position))
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                            imageView.setImage(ImageSource.bitmap(bitmap));
                            thumbView.setImageBitmap(bitmap);
                        }
                    });

            imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(_context, GalleryActivity.class);
                intent.putStringArrayListExtra(GalleryActivity.EXTRA_NAME, _images);
                startActivity(intent);
            }
        });
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }
}


