package com.codeadventure.oncue.database;

import com.codeadventure.oncue.Entity.ReminderEntity;
import com.codeadventure.oncue.Listeners.DeleteUserListener;
import com.codeadventure.oncue.database.offline.SharedData;
import com.codeadventure.oncue.MainEntity.*;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Amey on 29-08-2017.
 */

public class Synchronize
{
    public void synchronizeUser()
    {
        final String FACEBOOK_ID = SharedData.getFacebookID();
        final String MAC_ID = SharedData.getMacID();
        final String FNAME = SharedData.getFName();
        final String LNAME = SharedData.getLname();
        final String DP = SharedData.getDp().toString();
        final String EMAIL = SharedData.getEmail();
        final String GENDER = SharedData.getGender();
        final String DOB = SharedData.getDob();
        System.out.println("nnnnnn" + FACEBOOK_ID+" "+MAC_ID+" "+FNAME+" "+LNAME+" "+DP+" "+EMAIL+" "+GENDER+" "+DOB+" ");

        final User user = new User();
        user.setContext(getApplicationContext());
        try {
            user.deleteUser(FACEBOOK_ID, new DeleteUserListener() {
                @Override
                public void onCompleteTask(String result) {
                    user.setUserInfo(FACEBOOK_ID,MAC_ID,FNAME,LNAME,DP,EMAIL,GENDER,DOB);
                }
            });
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void synchronizeReminder() {
        ArrayList<ReminderEntity> reminderArrayList = SharedData.getReminderEntity();
            for (int i = 0; i < reminderArrayList.size(); i++) {
                //if(reminderArrayList.get(i).getREMINDER_ID() == REMINDER_ID)
                    Reminder reminder = new Reminder(reminderArrayList.get(i));
                    reminder.setContext(getApplicationContext());
                    System.out.println("FAILURE Now Deleting");
                    reminder.deleteReminder(reminderArrayList.get(i).getREMINDER_ID());
                if(!(reminderArrayList.get(i).getREMINDER_STATUS().equalsIgnoreCase("deleted")))
                    reminder.setUserReminder(reminder.getREMINDER_ID(), reminder.getMAC_ID(), reminder.getFACEBOOK_ID(), reminder.getREMINDER_TITLE(), reminder.getREMINDER_DESCRIPTION(), reminder.getREMINDER_FRIENDS(), reminder.getREMINDER_STATUS(), reminder.getREMINDER_TYPE(), reminder.getREMINDER_DATETIME());

                else
                    SharedData.deleteReminderEntity(reminderArrayList.get(i).getREMINDER_ID());
            }

    }
}
