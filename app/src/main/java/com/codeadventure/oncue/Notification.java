package com.codeadventure.oncue;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.codeadventure.oncue.Entity.NotificationEntity;
import com.codeadventure.oncue.database.offline.SharedData;

import java.util.ArrayList;
import java.util.List;


public class Notification extends Fragment {

    View view;
    RecyclerView recyclerView;
    NotificationAdapter reminderAdapter;
    List<NotificationAdapter.NotificationItems> notificationItemsList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_notification, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.notification_list);
        final TextView noreminder = (TextView) view.findViewById(R.id.noreminder);

        notificationItemsList = new ArrayList<>();
        final SharedData sd = new SharedData();

        int i;
        try {
            ArrayList<NotificationEntity> notificationEntities = sd.getNotificationEntity();
            for (i = notificationEntities.size() - 1; i > 0; i--) {

                NotificationAdapter.NotificationItems remind = new NotificationAdapter.NotificationItems(notificationEntities.get(i).getNOTIFICATION_ID() + "",
                        notificationEntities.get(i).getTITLE(), notificationEntities.get(i).getCONTENT(), false);
                notificationItemsList.add(remind);
            }

            Button clear = (Button) view.findViewById(R.id.clear);
            clear.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    notificationItemsList.clear();
                    SharedData sd = new SharedData();
                    int i;
                    while (sd.getNotificationEntity().size() > 0) {
                        sd.deleteNotificationEntity(sd.getNotificationEntity().get(0).getNOTIFICATION_ID());
                    }
                    reminderAdapter = new NotificationAdapter(notificationItemsList);
                    recyclerView.setAdapter(reminderAdapter);
                    if (notificationItemsList.size() == 0) {
                        noreminder.setVisibility(View.VISIBLE);
                    }
                    Toast.makeText(getContext(), "Cleared", Toast.LENGTH_LONG).show();

                }
            });
        }
        catch (Exception e)
        {
            System.out.println("Exception Record : "+e);
        }



        if (notificationItemsList.size() == 0) {
            // show message when the list is empty
            noreminder.setVisibility(View.VISIBLE);
        }

        // initialize the list view adapter
        reminderAdapter = new NotificationAdapter(notificationItemsList);
        recyclerView.setAdapter(reminderAdapter);

        return view;

    }


}