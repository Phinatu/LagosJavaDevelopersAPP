package com.example.android.lagosjavadevelopersapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<String>, DeveloperAdapter.ListItemClickListener{

    private RecyclerView mDevRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private DeveloperAdapter mDeveloperAdapter;
    private List<Developer> mDeveloperList = new ArrayList<>();
    private ProgressBar mDeveloperProgressBar;
    private TextView mErrorMessage;

    private static final int DEVELOPER_LOADER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDevRecyclerView = (RecyclerView) findViewById(R.id.dev_recycler_view);
        mDeveloperProgressBar = (ProgressBar) findViewById(R.id.dev_progress_bar);
        mErrorMessage = (TextView) findViewById(R.id.error_message);
        mGridLayoutManager  = new GridLayoutManager(getApplicationContext(), 2);
        mDevRecyclerView.setLayoutManager(mGridLayoutManager);
        LoaderManager loaderManager = getLoaderManager();
        mDevRecyclerView.setHasFixedSize(true);


        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {

// Get a reference to the LoaderManager, in order to interact with loaders.


            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(DEVELOPER_LOADER, null, MainActivity.this).forceLoad();
        } else {

            // Otherwise, display error
            showErrorMessage();
        }
    }

    private void showErrorMessage() {
        mDeveloperProgressBar.setVisibility(View.INVISIBLE);

        mErrorMessage.setVisibility(View.VISIBLE);
    }

    private void showDeveloperData() {
        mDeveloperProgressBar.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.INVISIBLE);
        mDevRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public android.content.Loader<String> onCreateLoader(int id, Bundle args) {
        mDeveloperProgressBar.setVisibility(View.VISIBLE);
        mDevRecyclerView.setVisibility(View.INVISIBLE);
        return new android.content.AsyncTaskLoader<String>(this) {

            @Override
            public String loadInBackground() {
                URL url = NetworkUtils.buildUrl();
                try {
                    String jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
                    return jsonResponse;
                } catch (Exception e) {
                    e.printStackTrace();

                }
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(android.content.Loader<String> loader, String data) {
        if (data == null) {
            showErrorMessage();
        } else {

            showDeveloperData();
            responseFromJSon(data);
            mDeveloperAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onLoaderReset(android.content.Loader<String> loader) {
        loader.forceLoad();
    }


    public void responseFromJSon(String jsonResponse) {
        mDeveloperProgressBar.setVisibility(View.INVISIBLE);
        mDevRecyclerView.setVisibility(View.VISIBLE);
        try {
            mDeveloperAdapter = new DeveloperAdapter(NetworkUtils.extractFeaturesFromJson(jsonResponse), MainActivity.this);
            mDevRecyclerView.setAdapter(mDeveloperAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDevRecyclerView.setAdapter(null);
    }

    @Override
    public void onListItemClick(Developer clickedItem) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("username", clickedItem.getmName());
        bundle.putString("profilePic", clickedItem.getmProfilePic());
        bundle.putString("profile", clickedItem.getmProfileUrl());
        intent.putExtras(bundle);
        startActivity(intent);

    }
}

