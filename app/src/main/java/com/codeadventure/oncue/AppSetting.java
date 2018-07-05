package com.codeadventure.oncue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;

import com.codeadventure.oncue.database.offline.SharedData;

public class AppSetting extends AppCompatActivity {

    static boolean check = true;
    Switch s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_setting);

        try {
            s = (Switch) findViewById(R.id.notification);
            final SharedData sd = new SharedData();
            if (sd.checkFlag())
                s.setChecked(true);

            else
                s.setChecked(false);

            ImageButton done = (ImageButton) findViewById(R.id.done);

            done.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (s.isChecked()) {
                        check = true;
                        s.setChecked(true);
                        sd.storeFlag(true);
                    } else {
                        check = false;
                        s.setChecked(false);
                        sd.storeFlag(false);
                    }
                    Intent intent = new Intent(getApplicationContext(), Navigation.class);
                    startActivity(intent);

                }
            });
        }
        catch (Exception e)
        {
            System.out.println("Exception Record : "+e);
        }
    }
}