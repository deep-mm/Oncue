package com.codeadventure.oncue.Entity;

import java.io.Serializable;

/**
 * Created by Amey on 30-08-2017.
 */

public class ReminderEntity implements Serializable
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

    public ReminderEntity(String REMINDER_ID, String MAC_ID, String FACEBOOK_ID, String REMINDER_TITLE, String REMINDER_DESCRIPTION, String REMINDER_FRIENDS, String REMINDER_STATUS, String REMINDER_TYPE, String REMINDER_DATETIME) {
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

    String REMINDER_ID, MAC_ID, FACEBOOK_ID, REMINDER_TITLE, REMINDER_DESCRIPTION, REMINDER_FRIENDS, REMINDER_STATUS,REMINDER_TYPE,REMINDER_DATETIME;

}
