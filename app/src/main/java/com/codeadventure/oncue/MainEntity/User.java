package com.codeadventure.oncue.MainEntity;

/**
 * Created by Amey on 29-08-2017.
 */

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codeadventure.oncue.Entity.FriendEntity;
import com.codeadventure.oncue.Listeners.DeleteUserListener;
import com.codeadventure.oncue.R;
import com.codeadventure.oncue.database.permissionfiles.HttpsTrustManager;
import com.codeadventure.oncue.utilities.SaveImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import static com.codeadventure.oncue.database.offline.SharedData.storeFriendEntity;

public class User
{
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

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void check()
    {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");


// From https://www.washington.edu/itconnect/security/ca/load-der.crt
            InputStream cert = context.getResources().openRawResource(R.raw.my_cert);
            Certificate ca= null;
            try {
                ca = cf.generateCertificate(cert);
                System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
                cert.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

// Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

// Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

// Create an SSLContext that uses our TrustManager
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, tmf.getTrustManagers(), null);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    String FACEBOOK_ID, MAC_ID, FNAME, LNAME, DP;
    String EMAIL;
    String GENDER;
    String DOB;

    Context context;

    public static final String urlSetUserInfo = "https://oncue.codeadventure.in/php/SetUserInfo.php";
    public static final String urlGetUserInfo = "https://oncue.codeadventure.in/php/GetUserInfo.php";
    public static final String urlDeleteUser = "https://oncue.codeadventure.in/php/DeleteUser.php";

    public User()
    {
    }

    User(Context context)
    {
        this.context = context;
    }

    public String toString()
    {
        return FACEBOOK_ID + " " + MAC_ID + " " + FNAME + " " + LNAME + " " + DP + " " + EMAIL + " " + GENDER + " " + DOB;
    }

    User(String FACEBOOK_ID, String MAC_ID, String FNAME, String LNAME, String DP,String EMAIL,String GENDER,String DOB) //Taking DP as string for now, could not understand much
    {
        this.FACEBOOK_ID=FACEBOOK_ID;
        this.MAC_ID=MAC_ID;
        this.FNAME=FNAME;
        this.LNAME=LNAME;
        this.DP=DP;
        this.EMAIL = EMAIL;
        this.GENDER = GENDER;
        this.DOB = DOB;
        // eg 1091827365168390 17647902746339038752 153996228909865389737 ...
    }


    //integrated with php and mysql
    public void userInfoExtractor(String response)
    {
        try
        {
            JSONArray jsonArray = new JSONArray(response);
            int n = jsonArray.length();
            for(int i=0;i<n;i++) // should run only once as individual user's records are being accessed
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                this.FACEBOOK_ID=jsonObject.getString("FACEBOOK_ID");
                this.MAC_ID=jsonObject.getString("MAC_ID");
                this.FNAME=jsonObject.getString("FNAME");
                this.LNAME=jsonObject.getString("LNAME");
                this.DP=jsonObject.getString("DP");
                this.DOB = jsonObject.getString("DOB");
                this.GENDER = jsonObject.getString("GENDER");
                this.EMAIL = jsonObject.getString("EMAIL");
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        FriendEntity friendEntity= new FriendEntity(this.FACEBOOK_ID,this.MAC_ID,this.FNAME,
                this.LNAME,this.DP,this.EMAIL,this.GENDER,this.DOB);
        SaveImage s = new SaveImage(getContext());
        s.saveMyImage(this.DP,this.FACEBOOK_ID);
        if(!(this.FACEBOOK_ID == null))
            storeFriendEntity(friendEntity);
    }

    //integrated with php and mysql
    public void getUserInfo(String FACEBOOK_ID)
    {
        final String fbid = FACEBOOK_ID;
        //HttpsTrustManager.allowAllSSL();

        check();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetUserInfo, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                userInfoExtractor(response);
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                System.out.println(error.toString());
            }
        }){// verify this part as have to do both posting facebook id and getting records but user.GET in StringRequest
            @Override
            protected Map<String,String> getParams()
            {
                Map<String,String> params = new HashMap<String,String>();
                params.put("FACEBOOK_ID",fbid);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    //integrated with php and mysql
    public void setUserInfo(String FACEBOOK_ID, String MAC_ID, String FNAME, String LNAME, String DP,String EMAIL,String GENDER,String DOB)
    {

        final String fbid = FACEBOOK_ID;
        final String macid = MAC_ID;
        final String fname = FNAME;
        final String lname = LNAME;
        final String dp = DP;
        final String email = EMAIL;
        final String gender = GENDER;
        final String dob = DOB;

        //HttpsTrustManager.allowAllSSL(); // permissions

        check();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlSetUserInfo, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                System.out.println(error.toString());
            }
        }){
            @Override
            protected Map<String,String> getParams()
            {
                Map<String,String> params = new HashMap<String,String>();
                params.put("FACEBOOK_ID",fbid);
                params.put("MAC_ID",macid);
                params.put("FNAME",fname);
                params.put("LNAME",lname);
                params.put("DP",dp);
                params.put("EMAIL",email);
                params.put("GENDER",gender);
                params.put("DOB",dob);
                return params;
            }
        };

        System.out.println(context.toString());
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    //integrated with php and mysql added listener
    public  void deleteUser(String FACEBOOK_ID, final DeleteUserListener listener)
    {
        //HttpsTrustManager.allowAllSSL();

        check();

        final String fbid = FACEBOOK_ID;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDeleteUser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                //System.out.println(response);
                //Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                listener.onCompleteTask(response);
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                System.out.println(error.toString());
                listener.onCompleteTask(error.toString());
            }
        }){// verify this part as have to do both posting facebook id and getting records but user.GET in StringRequest
            @Override
            protected Map<String,String> getParams()
            {
                Map<String,String> params = new HashMap<String,String>();
                params.put("FACEBOOK_ID",fbid);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }



}
