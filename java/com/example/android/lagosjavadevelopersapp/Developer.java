package com.example.android.lagosjavadevelopersapp;

import android.media.Image;

import static android.R.attr.name;


/**
 * Created by Admin on 22/08/2017.
 */

public class Developer {
    private String mName;
    private String mProfileUrl;
    private String mProfilePic;

    public Developer(String mName, String mProfileUrl,String mProfilePic) {
        this.mName = mName;
        this.mProfileUrl = mProfileUrl;
        this.mProfilePic = mProfilePic;
    }

    public void setmName(String mName) {

        this.mName = mName;
    }

    public String getmName() {

        return mName;
    }


    public void setmProfileUrl(String mProfileUrl) {

        this.mProfileUrl = mProfileUrl;
    }

    public String getmProfileUrl() {


        return mProfileUrl;
    }


    public void setmProfilePic(String mProfilePic) {

        this.mProfilePic = mProfilePic;
    }

    public  String getmProfilePic() {

        return mProfilePic;
    }
}