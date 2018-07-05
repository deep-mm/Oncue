package com.codeadventure.oncue.database.offline;

import android.net.Uri;

import com.codeadventure.oncue.Entity.FriendEntity;
import com.codeadventure.oncue.Entity.NotificationEntity;
import com.codeadventure.oncue.Entity.ReminderEntity;
import com.codeadventure.oncue.MyService;
import com.codeadventure.oncue.database.converter.ObjectSerializer;
import com.codeadventure.oncue.utilities.DateTime;

import java.net.URI;
import java.util.ArrayList;

public class SharedData
{
    public static boolean checkFlag(){
        return MyService.sharedPreferences.getBoolean("notificationflag",true);
    }

    public static boolean checkFlag1(){
        return MyService.sharedPreferences.getBoolean("syncflag",true);
    }


    public static void storeFlag(boolean flag){
        MyService.sharedPreferences.edit().putBoolean("notificationflag",flag).apply();
    }

    public static void storeFlag1(boolean flag){
        MyService.sharedPreferences.edit().putBoolean("syncflag",flag).apply();
    }

    public static void deleteReminderEntity(String REMINDER_ID)
    {
        ArrayList<String> reminderStrings = new ArrayList<String>();
        ArrayList<ReminderEntity> reminderEntities = getReminderEntity();
        for(int i=0;i<reminderEntities.size();i++)
        {
            if(reminderEntities.get(i).getREMINDER_ID().equalsIgnoreCase(REMINDER_ID))
            {
                reminderEntities.remove(i);
            }
        }
        try
        {
            for(int i=0;i<reminderEntities.size();i++)
            {
                String reminder = ObjectSerializer.serialize(reminderEntities.get(i));
                reminderStrings.add(reminder);
            }
            MyService.sharedPreferences.edit().putString("newreminderlist",ObjectSerializer.serialize(reminderStrings)).apply();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    public static void deleteNotificationEntity(String NOTIFICATION_ID)
    {
        ArrayList<String> newlist = new ArrayList<String>();
        ArrayList<NotificationEntity> list = getNotificationEntity();
        for(int i=0;i<list.size();i++)
        {
            if(list.get(i).getNOTIFICATION_ID().equalsIgnoreCase(NOTIFICATION_ID))
            {
                list.remove(i);
            }
        }
        try
        {
            for(int i=0;i<list.size();i++)
            {
                String notificationString = ObjectSerializer.serialize(list.get(i));
                newlist.add(notificationString);
            }

            MyService.sharedPreferences.edit().putString("newnotificationlist",ObjectSerializer.serialize(newlist)).apply();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static ReminderEntity getReminderEntity(String reminderId){
        ArrayList<ReminderEntity> list = getReminderEntity();
        for(ReminderEntity entity:list){
            if(entity.getREMINDER_ID().equalsIgnoreCase(reminderId))
                return entity;
        }

        return null;
    }

    public static FriendEntity getFriendEntity(String friendId){
        ArrayList<FriendEntity> list = getFriendEntity();
        for(FriendEntity entity:list){
            if(entity.getFACEBOOK_ID().equalsIgnoreCase(friendId))
                return entity;
        }

        return null;
    }
    public static void storeFacebookID(String FACEBOOK_ID)
    {
        MyService.sharedPreferences.edit().putString("FACEBOOK_ID",FACEBOOK_ID).apply();
    }

    public static String getFacebookID()                       //if there doesnt exists any id, null would be returned.
    {
        String FACEBOOK_ID = MyService.sharedPreferences.getString("FACEBOOK_ID","");
        return FACEBOOK_ID;
    }

    public static void storeFName(String FNAME)
    {
        MyService.sharedPreferences.edit().putString("FNAME",FNAME).apply();
    }

    public static String getFName()                       //if there doesnt exists any id, null would be returned.
    {
        String FNAME = MyService.sharedPreferences.getString("FNAME","");
        return FNAME;
    }

    public static void storeLname(String LNAME)
    {
        MyService.sharedPreferences.edit().putString("LNAME",LNAME).apply();
    }

    public static String getLname()                       //if there doesnt exists any id, null would be returned.
    {
        String LNAME = MyService.sharedPreferences.getString("LNAME","");
        return LNAME;
    }

    public static void storeMacID(String MAC_ID)
    {
        MyService.sharedPreferences.edit().putString("MAC_ID",MAC_ID).apply();
    }

    public static String getMacID()                       //if there doesnt exists any id, null would be returned.
    {
        String MAC_ID = MyService.sharedPreferences.getString("MAC_ID","");
        return MAC_ID;
    }


    public static void storeReminderEntity(ReminderEntity reminderEntity)
    {
        try
        {
            String reminderString = ObjectSerializer.serialize(reminderEntity);
            ArrayList<String> reminderlist = (ArrayList<String>) ObjectSerializer.deserialize(MyService.sharedPreferences.getString("newreminderlist",ObjectSerializer.serialize(new ArrayList<String>())));
            reminderlist.add(reminderString);
            MyService.sharedPreferences.edit().putString("newreminderlist",ObjectSerializer.serialize(reminderlist)).apply();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static ArrayList<ReminderEntity> getReminderEntity()
    {
        try
        {
            ArrayList<String> reminderlist = (ArrayList<String>) ObjectSerializer.deserialize(MyService.sharedPreferences.getString("newreminderlist",ObjectSerializer.serialize(new ArrayList<String>())));
            ArrayList<ReminderEntity> reminderArrayList = new ArrayList<ReminderEntity>();
            for(int i=0;i<reminderlist.size();i++)
            {
                reminderArrayList.add((ReminderEntity) ObjectSerializer.deserialize(reminderlist.get(i)));
            }
            return reminderArrayList;
        }
        catch(Exception e)
        {
            System.out.println("mmmmmm "+e);
            e.printStackTrace();
        }
        return null;
    }

    public static void storeNotificationEntity(NotificationEntity notificationEntity)
    {
        try
        {
            String notificationString = ObjectSerializer.serialize( notificationEntity);
            ArrayList<String> notificationlist = (ArrayList<String>) ObjectSerializer.deserialize(MyService.sharedPreferences.getString("newnotificationlist",ObjectSerializer.serialize(new ArrayList<String>())));
            notificationlist.add(notificationString);
            MyService.sharedPreferences.edit().putString("newnotificationlist",ObjectSerializer.serialize(notificationlist)).apply();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static ArrayList<NotificationEntity> getNotificationEntity()
    {
        try
        {
            ArrayList<String> notificationlist = (ArrayList<String>) ObjectSerializer.deserialize(MyService.sharedPreferences.getString("newnotificationlist",ObjectSerializer.serialize(new ArrayList<String>())));
            ArrayList<NotificationEntity> notificationArrayList = new ArrayList<NotificationEntity>();
            for(int i=0;i<notificationlist.size();i++)
            {
                notificationArrayList.add((NotificationEntity) ObjectSerializer.deserialize(notificationlist.get(i)));
            }
            return notificationArrayList;
        }
        catch(Exception e)
        {
            System.out.println("mmmmmm "+e);
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteFriendEntity()
    {
        try
        {
            MyService.sharedPreferences.edit().putString("friendlist",ObjectSerializer.serialize(new ArrayList<>())).apply();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void storeFriendEntity(FriendEntity friendEntity)            //online
    {
        try
        {
            String stringFriend = ObjectSerializer.serialize(friendEntity);
            ArrayList<String> friendlist = (ArrayList<String>) ObjectSerializer.deserialize(MyService.sharedPreferences.getString("friendlist",ObjectSerializer.serialize(new ArrayList<String>())));
            friendlist.add(stringFriend);
            MyService.sharedPreferences.edit().putString("friendlist",ObjectSerializer.serialize(friendlist)).apply();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static ArrayList<FriendEntity> getFriendEntity()
    {
        try
        {
            ArrayList<String> friendlist = (ArrayList<String>) ObjectSerializer.deserialize(MyService.sharedPreferences.getString("friendlist",ObjectSerializer.serialize(new ArrayList<String>())));
            ArrayList<FriendEntity> friendEntityArrayList = new ArrayList<FriendEntity>();
            for(int i=0;i<friendlist.size();i++)
            {
                friendEntityArrayList.add((FriendEntity) ObjectSerializer.deserialize(friendlist.get(i)));
            }
            return friendEntityArrayList;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }

    public static void storeDateTime(String DATE_TIME)
    {
        MyService.sharedPreferences.edit().putString("DATE_TIME",DATE_TIME).apply();
    }

    public static DateTime getDateTime()                       //if there doesnt exists any id, null would be returned.
    {
        String DATE_TIME = MyService.sharedPreferences.getString("DATE_TIME","");
        DateTime dateTime = new DateTime(DATE_TIME);
        return dateTime;
    }

    public static void storeEmail(String EMAIL)
    {
        MyService.sharedPreferences.edit().putString("EMAIL",EMAIL).apply();
    }

    public static String getEmail()                       //if there doesnt exists any id, null would be returned.
    {
        String EMAIL = MyService.sharedPreferences.getString("EMAIL","");
        return EMAIL;
    }

    public static void storeGender(String GENDER)
    {
        MyService.sharedPreferences.edit().putString("GENDER",GENDER).apply();
    }

    public static String getGender()                       //if there doesnt exists any id, null would be returned.
    {
        String GENDER = MyService.sharedPreferences.getString("GENDER","");
        return GENDER;
    }

    public static void storeDob(String DOB)
    {
        MyService.sharedPreferences.edit().putString("DOB",DOB).apply();
    }

    public static String getDob()                       //if there doesnt exists any id, null would be returned.
    {
        String DOB = MyService.sharedPreferences.getString("DOB","");
        return DOB;
    }

    public static void storeDp(Uri uri)
    {
        try
        {
            String stringUri = uri.toString();
            URI stringURI = new URI(stringUri);
            MyService.sharedPreferences.edit().putString("DP",stringURI.toString()).apply();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static Uri getDp()
    {
        try
        {
            URI stringURI = new URI(MyService.sharedPreferences.getString("DP",""));
            Uri stringUri = Uri.parse(stringURI.toString());
            return stringUri;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void storeRecentFriends(String FACEBOOK_ID)
    {
        ArrayList<String> recentFriendList = null;
        try
        {
            recentFriendList = (ArrayList<String>) ObjectSerializer.deserialize(MyService.sharedPreferences.getString("recentfriendlist",ObjectSerializer.serialize(new ArrayList<String>())));
            int i;
            for(i=0;i<recentFriendList.size();i++){
                if(recentFriendList.get(i).equalsIgnoreCase(FACEBOOK_ID))
                    return;
            }
            if(recentFriendList.size() > 10)
            {
                recentFriendList.add(0,FACEBOOK_ID);
                recentFriendList.remove(10);
            }
            else
            {
                recentFriendList.add(FACEBOOK_ID);
            }
            MyService.sharedPreferences.edit().putString("recentfriendlist",ObjectSerializer.serialize(recentFriendList)).apply();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getRecentFriends()
    {
        ArrayList<String> recentFriendList = null;
        try
        {
            recentFriendList = (ArrayList<String>)ObjectSerializer.deserialize(MyService.sharedPreferences.getString("recentfriendlist",ObjectSerializer.serialize(new ArrayList<String>())));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return recentFriendList;
    }


}
