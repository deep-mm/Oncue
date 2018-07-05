package com.codeadventure.oncue;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.codeadventure.oncue.Entity.NotificationEntity;
import com.codeadventure.oncue.Entity.ReminderEntity;
import com.codeadventure.oncue.database.offline.SharedData;
import com.codeadventure.oncue.utilities.DateTime;

import java.util.ArrayList;

import static com.codeadventure.oncue.database.offline.SharedData.getReminderEntity;

public class MyService extends Service {
    private static final String TAG = "MyService";
    public Context context = this;
    static int a =0 ;

    public Handler handler = null;
    public static Runnable runnable = null;

    View view;
    static BluetoothAdapter mBluetoothAdapter;
    public static SharedPreferences sharedPreferences;
    Button btnEnableDisable_Discoverable;
    public static ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();
    ListView lvNewDevices;

    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        sharedPreferences = this.getSharedPreferences("com.codeadventure.oncue",MODE_PRIVATE);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                btnDiscover(view);
                check_reminders();
                handler.postDelayed(runnable, 5000);
            }
        };

        handler.postDelayed(runnable, 5000);
        btnDiscover(view);
        return START_STICKY;
    }

    public void check_reminders()
    {
        int i, j;
        SharedData sd = new SharedData();
        String fid;
        String name;
        ReminderNotification rm = new ReminderNotification();
        try {
            for (j = 0; j < sd.getReminderEntity().size(); j++) {

                if (sd.getReminderEntity().get(j).getREMINDER_TYPE().equalsIgnoreCase("friend")) {


                    for (i = 0; i < mBTDevices.size(); i++) {
                        if ((mBTDevices.get(i).getAddress().equalsIgnoreCase(getReminderEntity().get(j).getREMINDER_FRIENDS()))
                                && (sd.getReminderEntity().get(j).getREMINDER_STATUS().equalsIgnoreCase("active"))) {

                            System.out.println("testing.. in friend " + sd.getReminderEntity().get(j).getREMINDER_DATETIME());
                            fid = sd.getReminderEntity().get(j).getREMINDER_FRIENDS();
                            name = find(fid);
                            String facebook_id = findFid(fid);

                            ReminderEntity reminderEntity = sd.getReminderEntity().get(j);
                            reminderEntity.setREMINDER_STATUS("reminded");

                            sd.deleteReminderEntity(sd.getReminderEntity().get(j).getREMINDER_ID());
                            sd.storeReminderEntity(reminderEntity);
                            NotificationEntity notificationEntity = new NotificationEntity(new DateTime().getDateString(), reminderEntity.getREMINDER_TITLE(), "Your Friend " + name + " is near you");
                            sd.storeNotificationEntity(notificationEntity);

                            if (sd.checkFlag()) {
                                rm = new ReminderNotification();
                                rm.notify(context, reminderEntity.getREMINDER_TITLE(), 1, "Your Friend " + name + " is near you", true, reminderEntity.getREMINDER_DESCRIPTION(), reminderEntity.getREMINDER_ID(),facebook_id);
                            }


                        }

                    }
                }
                else if ((getReminderEntity().get(j).getREMINDER_TYPE().equalsIgnoreCase("date")) &&
                        (sd.getReminderEntity().get(j).getREMINDER_STATUS().equalsIgnoreCase("active"))) {

                    if (sd.getReminderEntity().get(j).getREMINDER_DATETIME().substring(0, 16).equalsIgnoreCase(
                            new DateTime().getDateString().substring(0, 16))) {

                        ReminderEntity reminderEntity = sd.getReminderEntity().get(j);
                        reminderEntity.setREMINDER_STATUS("reminded");

                        sd.deleteReminderEntity(sd.getReminderEntity().get(j).getREMINDER_ID());
                        sd.storeReminderEntity(reminderEntity);
                        NotificationEntity notificationEntity = new NotificationEntity(new DateTime().getDateString(), reminderEntity.getREMINDER_TITLE(), "Its Time!");
                        sd.storeNotificationEntity(notificationEntity);

                        if (sd.checkFlag()) {
                            rm = new ReminderNotification();
                            rm.notify(context, reminderEntity.getREMINDER_TITLE(), 1, "Its Time!", false, reminderEntity.getREMINDER_DESCRIPTION(), reminderEntity.getREMINDER_ID()," ");

                        }

                    }

                }
                else
                    continue;
            }

        } catch (Exception e) {
            System.out.println("Exception Record : " + e);
        }
        mBTDevices.clear();
    }

    public static class mBroadcastReceiver3 extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            final String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mBTDevices.add(device);

            }
        }
    };


    public String find(String fid){

        int i;
        SharedData sd = new SharedData();
        for(i=0;i<sd.getFriendEntity().size();i++){
            if(sd.getFriendEntity().get(i).getMAC_ID().equalsIgnoreCase(fid)){
                return sd.getFriendEntity().get(i).getFNAME();
            }
        }
        return null;
    }

    public String findFid(String fid){

        int i;
        SharedData sd = new SharedData();
        for(i=0;i<sd.getFriendEntity().size();i++){
            if(sd.getFriendEntity().get(i).getMAC_ID().equalsIgnoreCase(fid)){
                return sd.getFriendEntity().get(i).getFACEBOOK_ID();
            }
        }
        return null;
    }


    public void btnDiscover(View view) {

        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();

            mBluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            mBroadcastReceiver3 mBroadcastReceiver = new mBroadcastReceiver3();
            registerReceiver(mBroadcastReceiver, discoverDevicesIntent);
        }
        else {

            mBluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            mBroadcastReceiver3 mBroadcastReceiver = new mBroadcastReceiver3();
            registerReceiver(mBroadcastReceiver, discoverDevicesIntent);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
