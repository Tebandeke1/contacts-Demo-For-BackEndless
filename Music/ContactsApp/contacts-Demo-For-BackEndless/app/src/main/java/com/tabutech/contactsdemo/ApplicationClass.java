package com.tabutech.contactsdemo;

import android.app.Application;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

import java.util.List;

public class ApplicationClass  extends Application {

    public static final String APPLICATION_ID = "FD28CE8D-83F6-AFA6-FFDA-8E085ACE7300";
    public static final String API_KEY = "16137A22-E863-48A8-98AB-3960F5E9E179";
    public static final String SERVER_URL = "https://api.backendless.com";


    public static BackendlessUser user;
    public static List<Contact> contacts;
    @Override
    public void onCreate() {
        super.onCreate();

        Backendless.setUrl(SERVER_URL);
        Backendless.initApp(getApplicationContext(),APPLICATION_ID,API_KEY);

    }


}
