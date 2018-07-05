package com.codeadventure.oncue;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codeadventure.oncue.Entity.ReminderEntity;
import com.codeadventure.oncue.Listeners.GetUserRemindersListener;
import com.codeadventure.oncue.database.Synchronize;
import com.codeadventure.oncue.database.data.FacebookData;
import com.codeadventure.oncue.database.offline.SharedData;
import com.codeadventure.oncue.utilities.SaveImage;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.ArrayList;
import java.util.Arrays;

public class Login extends FragmentActivity {

    CallbackManager callbackManager;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private ProgressBar progressBar;
    private Button login_button;
    private Button btnNext;
    String check="login";
    ProfileTracker mProfileTracker;
    LoginButton authButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkPermissions();
        Intent i = getIntent();
        if(i.hasExtra("check")) {
            check = i.getStringExtra("check");
        }
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnNext = (Button) findViewById(R.id.btn_next);
        authButton = (LoginButton) findViewById(R.id.login_button);
        authButton.setEnabled(true);
        authButton.setReadPermissions(Arrays.asList("user_friends","email","user_birthday"));

        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3,
                R.layout.welcome_slide4,
                };

        addBottomDots(0);
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int current = getItem(+1);
                if (current < layouts.length) {
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        try {
                            final SharedData sd = new SharedData();
                            authButton.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.VISIBLE);
                            FacebookData facebookData = new FacebookData();
                            facebookData.getFacebookAccountData();
                            facebookData.fetchFriends(getApplicationContext());

                            mProfileTracker = new ProfileTracker() {
                                @Override
                                protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                                    Log.v("facebook - profile", profile2.getFirstName());
                                    mProfileTracker.stopTracking();

                                    AccessToken accessToken = AccessToken.getCurrentAccessToken();
                                    profile = Profile.getCurrentProfile();//Or use the profile2 variable
                                    Uri profilePicUri = profile.getProfilePictureUri(200, 200);
                                    sd.storeDp(profilePicUri);
                                    sd.storeFacebookID(profile.getId());
                                    sd.storeFName(profile.getFirstName());
                                    sd.storeLname(profile.getLastName());
                                    SaveImage s1 = new SaveImage(getApplicationContext());
                                    s1.saveMyImage(sd.getDp().toString(),sd.getFacebookID());
                                    checkBTPermissions();
                                    String macAddress = android.provider.Settings.Secure.getString(getApplicationContext().getContentResolver(), "bluetooth_address");
                                    SharedData.storeMacID(macAddress);
                                    Synchronize s = new Synchronize();
                                    s.synchronizeUser();
                                    //syncReminders(sd.getFacebookID());
                                }
                            };
                            mProfileTracker.startTracking();
                            Intent intent = new Intent(getApplicationContext(), Welcome.class);
                            intent.putExtra("type","login");
                            startActivity(intent);
                            finish();
                        }
                        catch (Exception e)
                        {
                            System.out.println("Exception Record : "+e);
                        }
                    }

                    @Override
                    public void onCancel() {
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                    }
                });

    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {

        startActivity(new Intent(getApplicationContext(), Account.class));
        finish();
    }


    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            if (position == layouts.length - 1) {

                btnNext.setVisibility(View.GONE);
                if(check.equalsIgnoreCase("help")) {
                }
                else {
                    authButton.setVisibility(View.VISIBLE);
                }

            } else {

                btnNext.setVisibility(View.VISIBLE);
                authButton.setVisibility(View.INVISIBLE);

            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public static boolean isNetworkStatusAvialable (Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if(netInfos != null)
            {
                return netInfos.isConnected();
            }
        }
        return false;
    }

    public void checkPermissions()
    {
        if(Build.VERSION.SDK_INT > 21)
        {
            if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        }
    }

    public void checkBTPermissions() {

        if ( Build.VERSION.SDK_INT >= 23 ) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) ) {
                    //Log.i(TAG, "Waiting user's response...");

                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                            },
                            100); // 100 will be used in "onRequestPermissionsResult" about "requestCode".
                }
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (check.equalsIgnoreCase("help")) {
            Intent intent = new Intent(getApplicationContext(),Navigation.class);
            startActivity(intent);

        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    public void syncReminders(String id){
        final SharedData sd = new SharedData();
        final com.codeadventure.oncue.MainEntity.Reminder reminder = new com.codeadventure.oncue.MainEntity.Reminder();
        reminder.setContext(getApplicationContext());
        reminder.getUserReminders(id, new GetUserRemindersListener() {
            @Override
            public void onCompleteTask(ArrayList<com.codeadventure.oncue.MainEntity.Reminder> list) {
                int i;
                ReminderEntity reminderEntity;
                for(i=0;i<list.size();i++){
                    reminderEntity = new ReminderEntity(list.get(i).getREMINDER_ID(),list.get(i).getMAC_ID(),list.get(i).getFACEBOOK_ID(),
                            list.get(i).getREMINDER_TITLE(),list.get(i).getREMINDER_DESCRIPTION(),list.get(i).getREMINDER_FRIENDS(),
                            list.get(i).getREMINDER_STATUS(),list.get(i).getREMINDER_TYPE(),list.get(i).getREMINDER_DATETIME());
                    sd.storeReminderEntity(reminderEntity);
                }
            }
        });
            }
}
