package com.codeadventure.oncue.database.data;

import android.content.Context;
import android.os.Bundle;

import com.codeadventure.oncue.Entity.FriendEntity;
import com.codeadventure.oncue.MainEntity.User;
import com.codeadventure.oncue.database.offline.SharedData;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.codeadventure.oncue.database.offline.SharedData.deleteFriendEntity;

/**
 * Created by Amey on 29-08-2017.
 */

public class FacebookData
{
    AccessToken accessToken;
    Profile profile_curr;
    ProfileTracker mProfileTracker;
    FriendEntity friendEntity = null;

    public void getFacebookAccountData() {
        accessToken = AccessToken.getCurrentAccessToken();

        final SharedData sd = new SharedData();

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,email,gender,birthday,last_name");

        GraphRequest request = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "me",
                parameters,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        if (response.getError() != null) {
                            // display error message
                            return;
                        }
                        JSONObject jsonResponse = response.getJSONObject();
                        try {

                            for (int i = 0; i < jsonResponse.length(); i++) {
                                String fname = jsonResponse.getString("first_name");
                                String lname = jsonResponse.getString("last_name");
                                String email = jsonResponse.getString("email");
                                //String birthday = jsonResponse.getString("birthday"); // 01/31/1980 format
                                String gender = jsonResponse.getString("gender");

                                System.out.println("bbbbb " + email);

                                /*sd.storeFName(fname);
                                sd.storeLname(lname);*/
                                sd.storeEmail(email);
                                sd.storeDob("null");
                                sd.storeGender(gender);
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println("bbbbb "+e);
                            System.out.println("Error");
                        }

                    }
                }
        );
        request.executeAsync();
    }

    public void fetchFriends(final Context context) {

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,picture");
        parameters.putInt("limit", 100);
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "me/friends",
                parameters,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        if (response.getError() != null) {
                            // display error message
                            System.out.println(response.getError());
                            return;
                        }

                        JSONObject jsonResponse = response.getJSONObject();
                        System.out.println("OUT");
                        deleteFriendEntity();
                        try {
                            JSONArray jsonData = jsonResponse.getJSONArray("data");
                            System.out.println(jsonData.length());

                            for (int i=0; i<jsonData.length(); i++) {
                                System.out.println("IN");
                                JSONObject jsonUser = jsonData.getJSONObject(i);
                                String id = jsonUser.getString("id");
                                String name = jsonUser.getString("name");
                                String image = jsonUser.getJSONObject("picture").getJSONObject("data").getString("url");

                                System.out.println(id);
                                User user = new User();
                                user.setContext(context);
                                user.getUserInfo(id);
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println("Error");
                        }

                    }
                }
        ).executeAsync();
    }

}

