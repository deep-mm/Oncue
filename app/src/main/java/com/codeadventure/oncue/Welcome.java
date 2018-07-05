package com.codeadventure.oncue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.facebook.AccessToken;

public class Welcome extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Intent intent1 = new Intent(this, MyService.class);
        startService(intent1);
        AccessToken accessToken;
        accessToken = AccessToken.getCurrentAccessToken();

        if (accessToken == null) {
            setTheme(R.style.AppTheme);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Exception Record : " + e);
            }

            super.onCreate(savedInstanceState);
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }

        else{
            setTheme(R.style.AppTheme);
            try {

            }
            catch (Exception e) {
                System.out.println("Exception Record : " + e);
            }

            int a=0;
            Intent i = getIntent();
            if(i.hasExtra("type"))
                a = 1;

            try {
                if(a==0)
                    Thread.sleep(1500);

                if(a==1)
                    Toast.makeText(getApplicationContext(),"Please wait while we sync your friendlist for you",Toast.LENGTH_LONG).show();

            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Exception Record : " + e);
            }

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_welcome);
            final Intent intent = new Intent(getApplicationContext(), Navigation.class);
            startActivity(intent);
        }
    }
}
