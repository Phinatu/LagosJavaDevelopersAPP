package com.example.android.lagosjavadevelopersapp;

import android.content.Intent;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Admin on 22/08/2017.
 */

public class DetailActivity extends AppCompatActivity {

    TextView mUsername;
    ImageView mProfilePic;
    TextView mProfileUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        mUsername = (TextView) findViewById(R.id.user_name);
        mProfilePic = (ImageView) findViewById(R.id.user_image);
        mProfileUrl = (TextView) findViewById(R.id.git_url);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
       String profilePicUrl = intent.getStringExtra("profilePic");
        final String profileUrl = intent.getStringExtra("profile");

        mUsername.setText(username);
        mProfileUrl.setText(profileUrl);
        Picasso.with(this).load(profilePicUrl).into(mProfilePic);

        mProfileUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = ShareCompat.IntentBuilder.from(DetailActivity.this)
                        .setType("text/plain")
                        .setText("Check out this awesome developer @\"" + username + "," + profileUrl)
                        .getIntent();
                startActivity(shareIntent);
            }
        });

    }
}
