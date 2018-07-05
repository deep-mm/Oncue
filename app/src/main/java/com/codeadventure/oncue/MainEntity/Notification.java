package com.codeadventure.oncue.MainEntity;

import com.codeadventure.oncue.Entity.NotificationEntity;

/**
 * Created by Amey on 29-08-2017.
 */

public class Notification
{
    public Notification(String NOTIFICATION_ID, String TITLE, String CONTENT)
    {
        this.NOTIFICATION_ID = NOTIFICATION_ID;
        this.TITLE = TITLE;
        this.CONTENT = CONTENT;
    }

    public Notification(NotificationEntity notificationEntity)
    {
        this.NOTIFICATION_ID = notificationEntity.getNOTIFICATION_ID();
        this.TITLE = notificationEntity.getTITLE();
        this.CONTENT = notificationEntity.getCONTENT();
    }

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
    String CONTENT;
}
