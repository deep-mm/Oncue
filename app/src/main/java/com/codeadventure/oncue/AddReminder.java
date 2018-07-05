package com.codeadventure.oncue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codeadventure.oncue.Entity.FriendEntity;
import com.codeadventure.oncue.Entity.ReminderEntity;
import com.codeadventure.oncue.database.offline.SharedData;
import com.codeadventure.oncue.utilities.DateTime;
import com.codeadventure.oncue.utilities.DateTimePicker;
import com.codeadventure.oncue.utilities.SimpleDateTimePicker;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.codeadventure.oncue.database.offline.SharedData.getReminderEntity;

public class AddReminder extends AppCompatActivity implements DateTimePicker.OnDateTimeSetListener
{
    View view;
    static String rem_id;
    static int a=0;
    String type=" ",date1,fb_id;
    EditText titlemain,description;
    TextView respectTo;
    HorizontalScrollView horizontalScrollView;
    ImageView picture;
    String type1="nothing";
    static String Title=" ",Description=" ";
    ImageButton time,location,friend;
    Button confirm;
    RecyclerView recyclerView;
    List<AddReminderAdapter.ReminderItem> friendList;


    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_reminder);

        final SharedData sd = new SharedData();


            titlemain = (EditText) findViewById(R.id.titlemain);
            description = (EditText) findViewById(R.id.description_edit);
            time = (ImageButton) findViewById(R.id.time);
            location = (ImageButton) findViewById(R.id.location);
            friend = (ImageButton) findViewById(R.id.friend);
            confirm = (Button) findViewById(R.id.confirm);
            respectTo = (TextView) findViewById(R.id.respectTo);
        picture = (ImageView) findViewById(R.id.picture);
        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.friendview);

        recyclerView = (RecyclerView) findViewById(R.id.recentfriends);
        friendList = new ArrayList<>();

        int i;
        for(i= 0; i<sd.getRecentFriends().size(); i++) {
            System.out.println("xcvbn in 1st"+sd.getRecentFriends().size());
            String fid = sd.getRecentFriends().get(i);
            FriendEntity friendEntity = sd.getFriendEntity(fid);
            System.out.println("xcvbn "+friendEntity.getFNAME());
                AddReminderAdapter.ReminderItem remind = new AddReminderAdapter.ReminderItem(friendEntity.getFACEBOOK_ID(), friendEntity.getFNAME() + " " + friendEntity.getLNAME(), friendEntity.getDP());
                friendList.add(remind);

        }
        if(sd.getRecentFriends().size()<10){
            for(i=0;i<sd.getFriendEntity().size() && i<10-sd.getRecentFriends().size();i++) {

                FriendEntity friendEntity = sd.getFriendEntity().get(i);
                if (check(friendList, sd.getFriendEntity().get(i).getFACEBOOK_ID())) {
                    AddReminderAdapter.ReminderItem remind = new AddReminderAdapter.ReminderItem(friendEntity.getFACEBOOK_ID(), friendEntity.getFNAME() + " " + friendEntity.getLNAME(), friendEntity.getDP());
                    friendList.add(remind);

                }
            }
        }
        Collections.reverse(friendList);

        AddReminderAdapter reminderAdapter = new AddReminderAdapter(friendList);
        recyclerView.setAdapter(reminderAdapter);

        try {
            Intent intent = getIntent();
            if (intent.hasExtra("type"))
                type = intent.getExtras().getString("type");

            if (type.equalsIgnoreCase("edit")) {

                a = 1;
                rem_id = intent.getExtras().getString("rem_id");
                titlemain.setText(getReminderEntity(rem_id).getREMINDER_TITLE());
                description.setText(getReminderEntity(rem_id).getREMINDER_DESCRIPTION());
                String details;
                if (getReminderEntity(rem_id).getREMINDER_TYPE().equalsIgnoreCase("friend")) {

                    fb_id = get_fid(getReminderEntity(rem_id).getREMINDER_FRIENDS());
                    details = getFriendName(getReminderEntity(rem_id).getREMINDER_FRIENDS());
                    respectTo.setText("Friend: " + details);
                    type1 = "friend";
                } else {

                    details = getReminderEntity(rem_id).getREMINDER_DATETIME();
                    respectTo.setText("Date: " + details.substring(0, 10) + "\nTime:" + details.substring(11, 16));
                    type1 = "date";
                }
                respectTo.setVisibility(View.VISIBLE);
                Title = titlemain.getText().toString();
                Description = description.getText().toString();

            }

            if (type.equalsIgnoreCase("friend")) {

                fb_id = intent.getExtras().getString("fb_id");
                String name = intent.getExtras().getString("name");
                respectTo.setText(name);
                respectTo.setVisibility(View.VISIBLE);
                type1 = "friend";
                titlemain.setText(Title);
                description.setText(Description);
                location.setVisibility(View.GONE);
                time.setVisibility(View.GONE);
                friend.setVisibility(View.GONE);
                friend.setBackgroundColor(getResources().getColor(R.color.bar));
            }



            time.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    setdatetime(view);
                    type1 = "date";
                    horizontalScrollView.setVisibility(View.INVISIBLE);
                    picture.setVisibility(View.VISIBLE);
                    picture.setImageResource(R.drawable.ic_action_time);
                    location.setVisibility(View.GONE);
                    friend.setVisibility(View.GONE);
                    time.setVisibility(View.GONE);
                    time.setBackgroundColor(getResources().getColor(R.color.bar));

                }
            });

            picture.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if(type1.equalsIgnoreCase("date")) {
                        setdatetime(view);
                        type1 = "date";
                        horizontalScrollView.setVisibility(View.INVISIBLE);
                        picture.setVisibility(View.VISIBLE);
                        picture.setImageResource(R.drawable.ic_action_time);
                        location.setVisibility(View.GONE);
                        friend.setVisibility(View.GONE);
                        time.setVisibility(View.GONE);
                        time.setBackgroundColor(getResources().getColor(R.color.bar));
                    }
                    else{
                        Intent in = new Intent(getApplicationContext(), Navigation.class);
                        in.putExtra("position", 1);
                        Title = titlemain.getText().toString();
                        Description = description.getText().toString();
                        startActivity(in);
                        location.setVisibility(View.GONE);
                        time.setVisibility(View.GONE);
                        friend.setVisibility(View.GONE);
                        friend.setBackgroundColor(getResources().getColor(R.color.bar));
                    }

                }
            });


            /*location.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "Currently this feature is unavailable", Toast.LENGTH_LONG).show();

                }
            });*/

            friend.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    Intent in = new Intent(getApplicationContext(), Navigation.class);
                    in.putExtra("position", 1);
                    Title = titlemain.getText().toString();
                    Description = description.getText().toString();
                    startActivity(in);
                    location.setVisibility(View.GONE);
                    time.setVisibility(View.GONE);
                    friend.setVisibility(View.GONE);
                    friend.setBackgroundColor(getResources().getColor(R.color.bar));
                }
            });


            confirm.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    ReminderEntity reminderEntity = null;
                    Title = titlemain.getText().toString();
                    Description = description.getText().toString();

                    if (type1.equalsIgnoreCase("date")) {

                        if(date1==null){
                            Toast.makeText(getApplicationContext(),"Please select and confirm date and time",Toast.LENGTH_SHORT).show();
                            time.setBackgroundColor(getResources().getColor(R.color.text));
                        }
                        else {
                            reminderEntity = new ReminderEntity(new DateTime().getDateString(), sd.getMacID(), sd.getFacebookID(),
                                    Title, Description, "null", "active", type1, date1);

                            if (a == 1)
                                sd.deleteReminderEntity(rem_id);

                            sd.storeReminderEntity(reminderEntity);
                            a = 0;
                            Title = " ";
                            Description = " ";
                            Intent intent = new Intent(getApplicationContext(), Navigation.class);
                            startActivity(intent);
                        }
                    } else if (type1.equalsIgnoreCase("friend")) {

                        String mac = findMac(fb_id);
                        reminderEntity = new ReminderEntity(new DateTime().getDateString(), sd.getMacID(), sd.getFacebookID(),
                                Title, Description, mac, "active", type1, "null");

                        if (a == 1)
                            sd.deleteReminderEntity(rem_id);

                        sd.storeReminderEntity(reminderEntity);
                        sd.storeRecentFriends(fb_id);
                        a = 0;
                        Title=" ";
                        Description = " ";
                        Intent intent = new Intent(getApplicationContext(), Navigation.class);
                        startActivity(intent);
                    } else
                        Toast.makeText(getApplicationContext(), "Please select at least one of the following,\n Time or Friend", Toast.LENGTH_SHORT).show();

                }
            });
        }
        catch (Exception e)
        {
            System.out.println("Exception Record : "+e);
        }
    }

    public boolean check(List<AddReminderAdapter.ReminderItem> list,String id){
        int i;
        for(i=0;i<list.size();i++){
            if(list.get(i).id.equalsIgnoreCase(id))
                return false;
        }
        return true;
    }

    @Override
    public void DateTimeSet(Date date)
    {
        DateTime mDateTime = new DateTime(date);
        String DATE_TIME = mDateTime.getDateString();
        date1 = DATE_TIME;
        type1 = "date";
        String date3 = findMonth(date1.substring(5,7));
        String date2 = date1.substring(8,10);
        String time = date1.substring(11,16);
        respectTo.setText(date2+", "+date3+"\t\t"+time);
        respectTo.setVisibility(View.VISIBLE);
    }

    public String findMonth(String date){
        switch(date)
        {
            case "01":return "January";
            case "02":return "February";
            case "03":return "March";
            case "04":return "April";
            case "05":return "May";
            case "06":return "June";
            case "07":return "July";
            case "08":return "August";
            case "09":return "September";
            case "10":return "October";
            case "11":return "November";
            case "12":return "December";

        }
        return "null";
    }


    public void setdatetime(View view)
    {
        SimpleDateTimePicker.make(
                "Set Date & Time Title",
                new Date(),
                this,
                getSupportFragmentManager()
        ).show();
    }

    public String findMac(String fbid){
        SharedData sd = new SharedData();
        int i;
        for(i=0;i<sd.getFriendEntity().size();i++){

            if(sd.getFriendEntity().get(i).getFACEBOOK_ID().equalsIgnoreCase(fbid))
                return sd.getFriendEntity().get(i).getMAC_ID();
        }
        return null;
    }

    public String get_fid(String macid)
    {
        int i;
        SharedData sd = new SharedData();
        for(i=0;i<sd.getFriendEntity().size();i++){

            if(sd.getFriendEntity().get(i).getMAC_ID().equalsIgnoreCase(macid))
                return sd.getFriendEntity().get(i).getFACEBOOK_ID();

        }
        return null;
    }

    public String getFriendName(String macid)
    {
        int i;
        SharedData sd = new SharedData();
        for(i=0;i<sd.getFriendEntity().size();i++){

            if(sd.getFriendEntity().get(i).getMAC_ID().equalsIgnoreCase(macid))
                return sd.getFriendEntity().get(i).getFNAME()+" "+sd.getFriendEntity().get(i).getLNAME();

        }
        return null;
    }

    @Override
    public void onBackPressed() {
        Title=" ";
        Description = " ";
        Intent intent = new Intent(getApplicationContext(),Navigation.class);
        startActivity(intent);
    }

    private void displayProfilePic(File file) {

        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(40)
                .oval(false)
                .build();
        Picasso.with(picture.getContext())
                .load(file)
                .placeholder(R.drawable.circle)
                .transform(transformation)
                .into(picture);

    }

    @Override
    public void onResume(){
        super.onResume();
        if(type1.equalsIgnoreCase("friend") || type1.equalsIgnoreCase("date")){
            horizontalScrollView.setVisibility(View.INVISIBLE);
            picture.setVisibility(View.VISIBLE);
            if(type1.equalsIgnoreCase("friend")){
                File file = new File(android.os.Environment.getExternalStorageDirectory().toString()
                        +"/" + "OnCue" + "/" + fb_id+".jpg");
                displayProfilePic(file);

            }
        }


    }


}
