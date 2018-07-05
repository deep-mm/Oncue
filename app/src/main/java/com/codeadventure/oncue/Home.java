package com.codeadventure.oncue;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.codeadventure.oncue.database.offline.SharedData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Home extends Fragment {

    RecyclerView recyclerView;
    List<ReminderAdapter.ReminderItem> reminderList;
    TextView noreminder;
    ScrollView date;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_home, container, false);

        TextView date1 = (TextView) view.findViewById(R.id.date2);
        Calendar c = Calendar.getInstance();

        TextView month = (TextView) view.findViewById(R.id.month);

        SimpleDateFormat df = new SimpleDateFormat("dd");
        SimpleDateFormat df1 = new SimpleDateFormat("MMM, yyyy");
        String formattedDate = df.format(c.getTime());
        String formattedMonth = df1.format(c.getTime());
        date1.setText(formattedDate);
        month.setText(formattedMonth);
        date = (ScrollView) view.findViewById(R.id.date);

        recyclerView = (RecyclerView) view.findViewById(R.id.reminder_list);
        noreminder = (TextView) view.findViewById(R.id.noreminder);

        reminderList = new ArrayList<>();

        int i;
        ReminderAdapter.ReminderItem remind;
        try{
        SharedData sd = new SharedData();
        String image;

            for (i = sd.getReminderEntity().size()-1; i >=0 ; i--) {
                if(sd.getReminderEntity().get(i).getREMINDER_STATUS().equalsIgnoreCase("deleted"))
                    continue;

                if(sd.getReminderEntity().get(i).getREMINDER_TYPE().equalsIgnoreCase("friend")){
                    image = find_DP(sd.getReminderEntity().get(i).getREMINDER_FRIENDS());
                }
                else{
                    image = "http";
                }
                remind = new ReminderAdapter.ReminderItem(sd.getReminderEntity().get(i).getREMINDER_ID() + "", sd.getReminderEntity().get(i).getREMINDER_TITLE(), image);
                reminderList.add(remind);
            }
            if (reminderList.size() == 0)
                noreminder.setVisibility(View.VISIBLE);

            ReminderAdapter reminderAdapter = new ReminderAdapter(reminderList);
            recyclerView.setAdapter(reminderAdapter);

            return view;
        }
        catch (Exception e)
        {
            System.out.println("Exception Record : "+e);
        }
        return view;

    }
    public String find_DP(String fid){

        int i;
        SharedData sd = new SharedData();
        for(i=0;i<sd.getFriendEntity().size();i++){
            if(sd.getFriendEntity().get(i).getMAC_ID().equalsIgnoreCase(fid)){
                return sd.getFriendEntity().get(i).getFACEBOOK_ID();
            }
        }
        return null;
    }
}
