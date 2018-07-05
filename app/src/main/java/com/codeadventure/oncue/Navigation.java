package com.codeadventure.oncue;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.AccessToken;

public class Navigation extends AppCompatActivity
{
    AccessToken accessToken;
    static int m = 0;
    int a = 1;
    private static final String TAG = "Navigation";
    Menu menu;
    BluetoothAdapter mBluetoothAdapter;
    CustomPagerAdapter c,c1,c2;
    Typeface typeface;
    BottomNavigationView bottomNavigationView;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        accessToken = AccessToken.getCurrentAccessToken();

        AssetManager am = getApplicationContext().getAssets();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        c = new CustomPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(c);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_home);

        checkBTPermissions();

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(!mBluetoothAdapter.isEnabled()) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
            startActivity(discoverableIntent);

            IntentFilter intentFilter = new IntentFilter(mBluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
            registerReceiver(mBroadcastReceiver2, intentFilter);
        }


        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        menu = bottomNavigationView.getMenu();

            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                public void onPageScrollStateChanged(int state) {
                }

                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                public void onPageSelected(int position) {

                    switch (position) {
                        case 0:
                            set_values(R.id.action_home, "Home");
                            break;
                        case 1:
                            set_values(R.id.action_friends, "Friends");
                            break;
                        case 2:
                            set_values(R.id.action_addreminder, "Reminder");
                            break;
                        case 3:
                            set_values(R.id.action_notifications, "Notifications");
                            break;
                        case 4:
                            set_values(R.id.action_setting, "Settings");
                            break;
                    }
                }
            });


            bottomNavigationView.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                            switch (item.getItemId()) {
                                case R.id.action_setting:
                                    BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
                                    set_values(R.id.action_setting, "Settings");
                                    viewPager.setCurrentItem(4);
                                    break;

                                case R.id.action_notifications:
                                    BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
                                    set_values(R.id.action_notifications, "Notifications");
                                    viewPager.setCurrentItem(3);
                                    break;
                                case R.id.action_home:
                                    BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
                                    set_values(R.id.action_home, "Home");
                                    viewPager.setCurrentItem(0);
                                    break;

                                case R.id.action_friends:
                                    BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
                                    set_values(R.id.action_friends, "Friends");
                                    viewPager.setCurrentItem(1);
                                    break;

                                case R.id.action_addreminder:
                                    Intent intent = new Intent(getApplicationContext(),AddReminder.class);
                                    startActivity(intent);
                                    break;

                            }
                            return false;
                        }
                    });

        Intent intent = getIntent();
        if (intent.hasExtra("position")) {
            viewPager.setCurrentItem(1);
            set_values(R.id.action_friends, "Friends");
            a=0;
        }
    }

    public void set_values(int id,String s)
    {
        menu.findItem(R.id.action_friends).setTitle("");
        menu.findItem(R.id.action_home).setTitle("");
        menu.findItem(R.id.action_addreminder).setTitle("");
        menu.findItem(R.id.action_notifications).setTitle("");
        menu.findItem(R.id.action_setting).setTitle("");

        menu.findItem(id).setTitle(s);
    }

    @Override
    public void onBackPressed() {

        if(viewPager.getCurrentItem()==0){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else if(viewPager.getCurrentItem()==1){
            if(a==1) {
                viewPager.setCurrentItem(0);
            }

            else{
                Intent intent = new Intent(getApplicationContext(),AddReminder.class);
                startActivity(intent);
            }
        }
        else
            viewPager.setCurrentItem(0);
    }

    public void checkBTPermissions() {

        if ( Build.VERSION.SDK_INT >= 23 ) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) ) {
                    Log.i(TAG, "Waiting user's response...");

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

    private final BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (action.equals(mBluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, mBluetoothAdapter.ERROR);

                switch(state){
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG, "onReceive: STATE OFF");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG, "mBroadcastReceiver1: STATE TURNING OFF");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG, "mBroadcastReceiver1: STATE ON");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG, "mBroadcastReceiver1: STATE TURNING ON");
                        break;
                }
            }
        }
    };

    /**
     * Broadcast Receiver for changes made to bluetooth states such as:
     * 1) Discoverability mode on/off or expire.
     */
    private final BroadcastReceiver mBroadcastReceiver2 = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {

                int mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);

                switch (mode) {
                    //Device is in Discoverable Mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Enabled.");
                        break;
                    //Device not in discoverable mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Able to receive connections.");
                        break;
                    case BluetoothAdapter.SCAN_MODE_NONE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Not able to receive connections.");
                        break;
                    case BluetoothAdapter.STATE_CONNECTING:
                        Log.d(TAG, "mBroadcastReceiver2: Connecting....");
                        break;
                    case BluetoothAdapter.STATE_CONNECTED:
                        Log.d(TAG, "mBroadcastReceiver2: Connected.");
                        break;
                }

            }
        }
    };



}

