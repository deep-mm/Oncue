package com.codeadventure.oncue.Entity;

import java.io.Serializable;

/**
 * Created by Amey on 30-08-2017.
 */

public class FriendEntity implements Serializable
{
    String FACEBOOK_ID, MAC_ID, FNAME, LNAME, DP;
    String EMAIL;
    String GENDER;
    String DOB;

    public FriendEntity(String FACEBOOK_ID, String MAC_ID, String FNAME, String LNAME, String DP, String EMAIL, String GENDER, String DOB) {
        this.FACEBOOK_ID = FACEBOOK_ID;
        this.MAC_ID = MAC_ID;
        this.FNAME = FNAME;
        this.LNAME = LNAME;
        this.DP = DP;
        this.EMAIL = EMAIL;
        this.GENDER = GENDER;
        this.DOB = DOB;
    }



    public String getFACEBOOK_ID() {
        return FACEBOOK_ID;
    }

    public void setFACEBOOK_ID(String FACEBOOK_ID) {
        this.FACEBOOK_ID = FACEBOOK_ID;
    }

    public String getMAC_ID() {
        return MAC_ID;
    }

    public void setMAC_ID(String MAC_ID) {
        this.MAC_ID = MAC_ID;
    }

    public String getFNAME() {
        return FNAME;
    }

    public void setFNAME(String FNAME) {
        this.FNAME = FNAME;
    }

    public String getLNAME() {
        return LNAME;
    }

    public void setLNAME(String LNAME) {
        this.LNAME = LNAME;
    }

    public String getDP() {
        return DP;
    }

    public void setDP(String DP) {
        this.DP = DP;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getGENDER() {
        return GENDER;
    }

    public void setGENDER(String GENDER) {
        this.GENDER = GENDER;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }
}
