package com.codeadventure.oncue.Listeners;

import com.codeadventure.oncue.MainEntity.Reminder;

import java.util.ArrayList;

/**
 * Created by Deep on 9/5/2017.
 */

public interface GetUserRemindersListener
{
    void onCompleteTask(ArrayList<Reminder> list);
}
