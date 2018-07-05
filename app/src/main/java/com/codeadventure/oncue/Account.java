package com.codeadventure.oncue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codeadventure.oncue.database.offline.SharedData;
import com.facebook.login.LoginManager;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;

import java.io.File;


public class Account extends AppCompatActivity {

    Button log_out;
    TextView fname_set, lname_set, gender_set, dob_set, email_set;
    ImageView profile1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        fname_set = (TextView) findViewById(R.id.fname_set);
        lname_set = (TextView) findViewById(R.id.lname_set);
        log_out = (Button) findViewById(R.id.log_out);
        gender_set = (TextView) findViewById(R.id.gender_set);
        email_set = (TextView) findViewById(R.id.email_set);
        //dob_set = (TextView) findViewById(R.id.dob_set);
        profile1 = (ImageView) findViewById(R.id.profile_image);
        log_out = (Button) findViewById(R.id.log_out);


        try {
            fname_set.setText(SharedData.getFName());
            lname_set.setText(SharedData.getLname());
            File file = new File(android.os.Environment.getExternalStorageDirectory().toString()
                    +"/" + "OnCue" + "/" +SharedData.getFacebookID()+".jpg");
            displayProfilePic(file);
            gender_set.setText(SharedData.getGender().toUpperCase());
            email_set.setText(SharedData.getEmail());
            //dob_set.setText(SharedData.getDob());
        }
        catch (Exception e)
        {
            System.out.println("Exception Record : "+e);
        }
    }

    private void displayProfilePic(File file) {
        com.squareup.picasso.Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(40)
                .oval(false)
                .build();
        Picasso.with(Account.this)
                .load(file)
                .transform(transformation)
                .into(profile1);
    }

    public void logout(View view) {

                    LoginManager.getInstance().logOut();
                    startActivity(new Intent(getApplicationContext(), Login.class));
    }



}
