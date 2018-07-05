package com.codeadventure.oncue.MainEntity;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codeadventure.oncue.Entity.ReminderEntity;
import com.codeadventure.oncue.Listeners.GetUserRemindersListener;
import com.codeadventure.oncue.database.permissionfiles.HttpsTrustManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amey on 29-08-2017.
 */

public class Reminder
{
    public String getREMINDER_ID() {
        return REMINDER_ID;
    }

    public void setREMINDER_ID(String REMINDER_ID) {
        this.REMINDER_ID = REMINDER_ID;
    }

    public String getMAC_ID() {
        return MAC_ID;
    }

    public void setMAC_ID(String MAC_ID) {
        this.MAC_ID = MAC_ID;
    }

    public String getFACEBOOK_ID() {
        return FACEBOOK_ID;
    }

    public void setFACEBOOK_ID(String FACEBOOK_ID) {
        this.FACEBOOK_ID = FACEBOOK_ID;
    }

    public String getREMINDER_TITLE() {
        return REMINDER_TITLE;
    }

    public void setREMINDER_TITLE(String REMINDER_TITLE) {
        this.REMINDER_TITLE = REMINDER_TITLE;
    }

    public String getREMINDER_DESCRIPTION() {
        return REMINDER_DESCRIPTION;
    }

    public void setREMINDER_DESCRIPTION(String REMINDER_DESCRIPTION) {
        this.REMINDER_DESCRIPTION = REMINDER_DESCRIPTION;
    }

    public String getREMINDER_FRIENDS() {
        return REMINDER_FRIENDS;
    }

    public void setREMINDER_FRIENDS(String REMINDER_FRIENDS) {
        this.REMINDER_FRIENDS = REMINDER_FRIENDS;
    }

    public String getREMINDER_STATUS() {
        return REMINDER_STATUS;
    }

    public void setREMINDER_STATUS(String REMINDER_STATUS) {
        this.REMINDER_STATUS = REMINDER_STATUS;
    }

    public String getREMINDER_TYPE() {
        return REMINDER_TYPE;
    }

    public void setREMINDER_TYPE(String REMINDER_TYPE) {
        this.REMINDER_TYPE = REMINDER_TYPE;
    }

    public String getREMINDER_DATETIME() {
        return REMINDER_DATETIME;
    }

    public void setREMINDER_DATETIME(String REMINDER_DATETIME) {
        this.REMINDER_DATETIME = REMINDER_DATETIME;
    }

    public Reminder()
    {

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Reminder> getReminderObjectList() {
        return reminderObjectList;
    }

    public void setReminderObjectList(ArrayList<Reminder> reminderObjectList) {
        this.reminderObjectList = reminderObjectList;
    }

    public Reminder(ReminderEntity reminderEntity)
    {
        this.REMINDER_ID = reminderEntity.getREMINDER_ID();
        this.MAC_ID = reminderEntity.getMAC_ID();
        this.FACEBOOK_ID = reminderEntity.getFACEBOOK_ID();
        this.REMINDER_TITLE = reminderEntity.getREMINDER_TITLE();
        this.REMINDER_DESCRIPTION = reminderEntity.getREMINDER_DESCRIPTION();
        this.REMINDER_FRIENDS = reminderEntity.getREMINDER_FRIENDS();
        this.REMINDER_STATUS = reminderEntity.getREMINDER_STATUS();
        this.REMINDER_TYPE = reminderEntity.getREMINDER_TYPE();
        this.REMINDER_DATETIME = reminderEntity.getREMINDER_DATETIME();
    }

    public Reminder(String REMINDER_ID, String MAC_ID, String FACEBOOK_ID, String REMINDER_TITLE, String REMINDER_DESCRIPTION, String REMINDER_FRIENDS, String REMINDER_STATUS, String REMINDER_TYPE, String REMINDER_DATETIME) {
        this.REMINDER_ID = REMINDER_ID;
        this.MAC_ID = MAC_ID;
        this.FACEBOOK_ID = FACEBOOK_ID;
        this.REMINDER_TITLE = REMINDER_TITLE;
        this.REMINDER_DESCRIPTION = REMINDER_DESCRIPTION;
        this.REMINDER_FRIENDS = REMINDER_FRIENDS;
        this.REMINDER_STATUS = REMINDER_STATUS;
        this.REMINDER_TYPE = REMINDER_TYPE;
        this.REMINDER_DATETIME = REMINDER_DATETIME;
    }

    String REMINDER_ID, MAC_ID, FACEBOOK_ID, REMINDER_TITLE, REMINDER_DESCRIPTION, REMINDER_FRIENDS, REMINDER_STATUS,REMINDER_TYPE,REMINDER_DATETIME;

    Context context;
    ArrayList<Reminder> reminderObjectList;
    Reminder reminderObject;

    @Override
    public String toString() {
        return "Reminder{" +
                "REMINDER_ID='" + REMINDER_ID + '\'' +
                ", MAC_ID='" + MAC_ID + '\'' +
                ", FACEBOOK_ID='" + FACEBOOK_ID + '\'' +
                ", REMINDER_TITLE='" + REMINDER_TITLE + '\'' +
                ", REMINDER_DESCRIPTION='" + REMINDER_DESCRIPTION + '\'' +
                ", REMINDER_FRIENDS='" + REMINDER_FRIENDS + '\'' +
                ", REMINDER_STATUS='" + REMINDER_STATUS + '\'' +
                ", REMINDER_TYPE='" + REMINDER_TYPE + '\'' +
                ", REMINDER_DATETIME='" + REMINDER_DATETIME + '\'' +
                '}';
    }

    public static final String urlGetUserReminders = "https://oncue.codeadventure.in/php/GetUserReminders.php";
    public static final String urlSetUserReminder = "https://oncue.codeadventure.in/php/SetUserReminder.php";
    public static final String urlGetReminder = "https://oncue.codeadventure.in/php/GetReminder.php";
    public static final String urlDeleteReminder = "https://oncue.codeadventure.in/php/DeleteReminder.php";

    Reminder(Context context)
    {
        this.context=context;
        reminderObjectList =null;
        reminderObject=null;
    }

    Reminder(String REMINDER_ID, String MAC_ID, String FACEBOOK_ID, String REMINDER_TITLE, String REMINDER_DESCRIPTION, String REMINDER_FRIENDS, String REMINDER_STATUS)
    {
        this.REMINDER_ID=REMINDER_ID;
        this.MAC_ID=MAC_ID;
        this.FACEBOOK_ID=FACEBOOK_ID;
        this.REMINDER_TITLE=REMINDER_TITLE;
        this.REMINDER_DESCRIPTION=REMINDER_DESCRIPTION;
        this.REMINDER_FRIENDS=REMINDER_FRIENDS;
        this.REMINDER_STATUS=REMINDER_STATUS;
        this.reminderObjectList=null;
    }

    //debugged  //integrated with php and mysql
    public  void reminderObjectArrayExtractor(String response)
    {
        int n=0;
        try
        {
            JSONArray jsonArray = new JSONArray(response);
            n = jsonArray.length();
            reminderObjectList = new ArrayList<Reminder>();

            for(int i=0;i<n;i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                reminderObjectList.add(new Reminder(jsonObject.getString("REMINDER_ID"), jsonObject.getString("MAC_ID"), jsonObject.getString("FACEBOOK_ID"), jsonObject.getString("REMINDER_TITLE"), jsonObject.getString("REMINDER_DESCRIPTION"), jsonObject.getString("REMINDER_FRIENDS"), jsonObject.getString("REMINDER_STATUS"), jsonObject.getString("REMINDER_TYPE"), jsonObject.getString("REMINDER_DATETIME")));
            }
            for(int i=0;i<n;i++)
            {
                //Toast.makeText(context,reminderObjectArray[i].toString(),Toast.LENGTH_SHORT).show();
                System.out.println(reminderObjectList.get(i));
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

    }

    //debugged  //integrated with php and mysql
    public  void reminderObjectExtractor(String response)
    {

        try
        {
            JSONArray jsonArray = new JSONArray(response);
            int n = jsonArray.length();
            for(int i=0;i<n;i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                REMINDER_ID = jsonObject.getString("REMINDER_ID");
                MAC_ID = jsonObject.getString("MAC_ID");
                FACEBOOK_ID = jsonObject.getString("FACEBOOK_ID");
                REMINDER_TITLE = jsonObject.getString("REMINDER_TITLE");
                REMINDER_DESCRIPTION = jsonObject.getString("REMINDER_DESCRIPTION");
                REMINDER_FRIENDS = jsonObject.getString("REMINDER_FRIENDS");
                REMINDER_STATUS = jsonObject.getString("REMINDER_STATUS");
                REMINDER_TYPE = jsonObject.getString("REMINDER_TYPE");
                REMINDER_DATETIME = jsonObject.getString("REMINDER_DATETIME");
            }
            //Toast.makeText(context,this.toString(),Toast.LENGTH_LONG).show();

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

    }

    //debugged  //integrated with php and mysql
    public void getUserReminders(String FACEBOOK_ID, final GetUserRemindersListener listener)
    {
        HttpsTrustManager.allowAllSSL();
        final String fbid=FACEBOOK_ID;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetUserReminders, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                System.out.println(response);
                reminderObjectArrayExtractor(response);
                listener.onCompleteTask(reminderObjectList);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                System.out.println(error.toString());
            }
        }){// verify this part as have to do both posting facebook id and getting records but user.GET in StringRequest
            @Override
            protected Map<String, String> getParams()
            {
                Map<String,String> params = new HashMap<String,String>();
                params.put("FACEBOOK_ID",fbid);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    //debugged  //integrated with php and mysql
    public void setUserReminder(String REMINDER_ID, String MAC_ID, String FACEBOOK_ID, String REMINDER_TITLE, String REMINDER_DESCRIPTION, String REMINDER_FRIENDS, String REMINDER_STATUS,String REMINDER_TYPE,String REMINDER_DATETIME )
    {
        HttpsTrustManager.allowAllSSL(); // permissions
        final String reminder_id = REMINDER_ID, mac_id=MAC_ID, facebook_id=FACEBOOK_ID, reminder_title=REMINDER_TITLE, reminder_description=REMINDER_DESCRIPTION, reminder_friends=REMINDER_FRIENDS, reminder_status=REMINDER_STATUS;
        final String reminder_type = REMINDER_TYPE;
        final String reminder_datetime = REMINDER_DATETIME;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlSetUserReminder, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                System.out.println(response); // replace this with return 1 or something for success
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                System.out.println(error.toString());  // replace this with return 0 or something for failure
            }
        }){
            @Override
            protected Map<String,String> getParams()
            {
                Map<String,String> params = new HashMap<String,String>();
                params.put("REMINDER_ID",reminder_id);
                params.put("MAC_ID",mac_id);
                params.put("FACEBOOK_ID",facebook_id);
                params.put("REMINDER_TITLE",reminder_title);
                params.put("REMINDER_DESCRIPTION",reminder_description);
                params.put("REMINDER_FRIENDS",reminder_friends);
                params.put("REMINDER_STATUS",reminder_status);
                params.put("REMINDER_TYPE",reminder_type);
                params.put("REMINDER_DATETIME",reminder_datetime);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    //debugged  //integrated with php and mysql
    public void getReminder(String REMINDER_ID)
    {
        HttpsTrustManager.allowAllSSL();

        final String rid = REMINDER_ID;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetReminder, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                System.out.println(response);
                reminderObjectExtractor(response);
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                System.out.println(error.toString());
            }
        }){// verify this part as have to do both posting facebook id and getting records but user.GET in StringRequest
            @Override
            protected Map<String,String> getParams()
            {
                Map<String,String> params = new HashMap<String,String>();
                params.put("REMINDER_ID",rid);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    //debugged  //integrated with php and mysql
    public void deleteReminder(String REMINDER_ID)
    {
        HttpsTrustManager.allowAllSSL();
        final String rid=REMINDER_ID;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDeleteReminder, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                System.out.println(response);
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                System.out.println(error.toString());
            }
        }){// verify this part as have to do both posting facebook id and getting records but user.GET in StringRequest
            @Override
            protected Map<String,String> getParams()
            {
                Map<String,String> params = new HashMap<String,String>();
                params.put("REMINDER_ID",rid);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
