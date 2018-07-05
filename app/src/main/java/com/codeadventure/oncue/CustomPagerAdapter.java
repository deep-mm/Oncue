package com.codeadventure.oncue;

/**
 * Created by Deep on 8/9/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CustomPagerAdapter extends FragmentPagerAdapter {


    public CustomPagerAdapter(FragmentManager fm) {

        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Home h =new Home();
                return h;
            case 1:
                Friends f = new Friends();
                return f;
            case 2:
                /*AddReminder ar = new AddReminder();
                return ar;*/
            case 3:
                Notification n = new Notification();
                return n;
            case 4:
                Setting s = new Setting();
                return s;
        }
        return null;
    }


    @Override
    public int getCount() {
        return 5;
    }


}
