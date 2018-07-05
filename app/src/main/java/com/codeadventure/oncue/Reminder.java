package com.codeadventure.oncue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.codeadventure.oncue.Entity.ReminderEntity;
import com.codeadventure.oncue.database.offline.SharedData;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;

public class Reminder extends AppCompatActivity {

    String id;
    String type;
    int found_i;
    ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        Intent intent = getIntent();
        id = intent.getExtras().get("id").toString();
        int i;

        TextView title = (TextView) findViewById(R.id.title);
        //TextView title1 = (TextView) findViewById(R.id.title1);
        ImageButton delete = (ImageButton) findViewById(R.id.delete);
        ImageButton edit = (ImageButton) findViewById(R.id.edit);
        TextView set = (TextView) findViewById(R.id.set);
        TextView body = (TextView) findViewById(R.id.body);
        picture = (ImageView) findViewById(R.id.picture);

        final SharedData sd = new SharedData();

        try {
                    title.setText(sd.getReminderEntity(id).getREMINDER_TITLE());
                    //title1.setText(sd.getReminderEntity(id).getREMINDER_TITLE());
                    body.setText(sd.getReminderEntity(id).getREMINDER_DESCRIPTION());
                    if (sd.getReminderEntity(id).getREMINDER_TYPE().equalsIgnoreCase("friend")) {

                        String fid = findFid(sd.getReminderEntity(id).getREMINDER_FRIENDS());
                        File file = new File(android.os.Environment.getExternalStorageDirectory().toString()
                                +"/" + "OnCue" + "/" + fid +".jpg");
                        displayProfilePic(file);

                        String name = find(sd.getReminderEntity(id).getREMINDER_FRIENDS());
                        set.setText(name);
                    }
                    else {
                        String stringBuffer = sd.getReminderEntity(id).getREMINDER_DATETIME();
                        String date1 = findMonth(stringBuffer.substring(5,7));
                        String date2 = stringBuffer.substring(8,10);
                        StringBuffer time = new StringBuffer(stringBuffer.substring(11,16));
                        set.setText(date2+", "+date1+"\t\t"+time);
                    }


            delete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    ReminderEntity reminderEntity = sd.getReminderEntity(id);
                    reminderEntity.setREMINDER_STATUS("deleted");
                    sd.deleteReminderEntity(id);
                    //sd.storeReminderEntity(reminderEntity);
                    Intent intent = new Intent(getApplicationContext(), Navigation.class);
                    startActivity(intent);
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getApplicationContext(), AddReminder.class);
                    intent.putExtra("rem_id", id);
                    intent.putExtra("type", "edit");
                    startActivity(intent);

                }
            });
        }
        catch (Exception e)
        {
            System.out.println("Exception Record : "+e);
        }

    }

    public String find(String fid){

        int i;
        SharedData sd = new SharedData();
        for(i=0;i<sd.getFriendEntity().size();i++){
            System.out.println("sssss" + sd.getFriendEntity().get(i).getMAC_ID());
            if(sd.getFriendEntity().get(i).getMAC_ID().equalsIgnoreCase(fid)){
                System.out.println("sssss" + sd.getFriendEntity().get(i).getFNAME());
                return sd.getFriendEntity().get(i).getFNAME()+" "+sd.getFriendEntity().get(i).getLNAME();
            }
        }
        return null;
    }

    public String findFid(String fid){

        int i;
        SharedData sd = new SharedData();
        for(i=0;i<sd.getFriendEntity().size();i++){
            System.out.println("sssss" + sd.getFriendEntity().get(i).getMAC_ID());
            if(sd.getFriendEntity().get(i).getMAC_ID().equalsIgnoreCase(fid)){
                System.out.println("sssss" + sd.getFriendEntity().get(i).getFNAME());
                return sd.getFriendEntity().get(i).getFACEBOOK_ID();
            }
        }
        return null;
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),Navigation.class);
        startActivity(intent);
    }


}
