package com.codeadventure.oncue;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codeadventure.oncue.Entity.ReminderEntity;
import com.codeadventure.oncue.Listeners.GetUserRemindersListener;
import com.codeadventure.oncue.MainEntity.Reminder;
import com.codeadventure.oncue.database.data.FacebookData;
import com.codeadventure.oncue.database.offline.SharedData;

import java.util.ArrayList;

import static com.codeadventure.oncue.Login.isNetworkStatusAvialable;
import static com.facebook.FacebookSdk.getApplicationContext;


public class Setting extends Fragment {

    int flag = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_setting, container, false);
        SharedData sd = new SharedData();

        if(isNetworkStatusAvialable(getApplicationContext()) && sd.checkFlag1()) {
            sd.storeFlag1(false);
        }

        TextView acc = (TextView) v.findViewById(R.id.account);
        TextView help = (TextView) v.findViewById(R.id.helpin);
        TextView about = (TextView) v.findViewById(R.id.aboutin);
        TextView refer = (TextView) v.findViewById(R.id.refernearn);
        TextView setting = (TextView) v.findViewById(R.id.appsetting);
        TextView sync = (TextView) v.findViewById(R.id.syncText);
        final ProgressBar progressBar = (ProgressBar) v.findViewById(R.id.progressBar);

        acc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Account.class);
                startActivity(intent);

            }
        });

        help.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Login.class);
                i.putExtra("check","help");
                startActivity(i);

            }
        });

        about.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String url = "http://oncue.codeadventure.in/app/oncue.html";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

        refer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"No referral scheme currently available\nStay Tuned",Toast.LENGTH_LONG).show();

            }
        });

        setting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AppSetting.class);
                startActivity(intent);

            }
        });

        sync.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (isNetworkStatusAvialable(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(),"Now Syncing your Friends list\nPlease Wait",Toast.LENGTH_LONG).show();
                    FacebookData facebookData = new FacebookData();
                    facebookData.fetchFriends(getApplicationContext());
                    //syncReminders(sd.getFacebookID());
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"Sync Successful",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"Please Check your Internet Connection",Toast.LENGTH_LONG).show();

            }
        });

        return v;
    }

    public void syncReminders(String id){
        final SharedData sd = new SharedData();
        final com.codeadventure.oncue.MainEntity.Reminder reminder = new Reminder();
        reminder.setContext(getApplicationContext());
        reminder.getUserReminders(id, new GetUserRemindersListener() {
            @Override
            public void onCompleteTask(ArrayList<Reminder> list) {
                int i;
                ReminderEntity reminderEntity;
                for(i=0;i<list.size();i++){
                    reminderEntity = new ReminderEntity(list.get(i).getREMINDER_ID(),list.get(i).getMAC_ID(),list.get(i).getFACEBOOK_ID(),
                            list.get(i).getREMINDER_TITLE(),list.get(i).getREMINDER_DESCRIPTION(),list.get(i).getREMINDER_FRIENDS(),
                            list.get(i).getREMINDER_STATUS(),list.get(i).getREMINDER_TYPE(),list.get(i).getREMINDER_DATETIME());
                    sd.storeReminderEntity(reminderEntity);
                    flag = 1;
                }
            }
        });

    }

}
