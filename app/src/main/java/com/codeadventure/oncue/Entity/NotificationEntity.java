package com.codeadventure.oncue.Entity;

import java.io.Serializable;

/**
 * Created by Amey on 30-08-2017.
 */

public class NotificationEntity implements Serializable
{
    public String getNOTIFICATION_ID() {
        return NOTIFICATION_ID;
    }

    public void setNOTIFICATION_ID(String NOTIFICATION_ID) {
        this.NOTIFICATION_ID = NOTIFICATION_ID;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }

    String NOTIFICATION_ID;
    String TITLE;

    public NotificationEntity(String NOTIFICATION_ID, String TITLE, String CONTENT) {
        this.NOTIFICATION_ID = NOTIFICATION_ID;
        this.TITLE = TITLE;
        this.CONTENT = CONTENT;
    }

    String CONTENT;
}
