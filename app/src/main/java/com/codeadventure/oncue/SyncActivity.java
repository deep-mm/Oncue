package com.codeadventure.oncue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.codeadventure.oncue.Entity.ReminderEntity;
import com.codeadventure.oncue.Listeners.GetUserRemindersListener;
import com.codeadventure.oncue.database.data.FacebookData;
import com.codeadventure.oncue.database.offline.SharedData;

import java.util.ArrayList;

public class SyncActivity extends AppCompatActivity {

    int a=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedData sd = new SharedData();
        FacebookData facebookData = new FacebookData();
        facebookData.fetchFriends(getApplicationContext());
        syncReminders(sd.getFacebookID());
        Intent intent = new Intent(this,Navigation.class);

       /* try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);
        startActivity(intent);
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
                    a=1;
                }
            }
        });
    }

}
