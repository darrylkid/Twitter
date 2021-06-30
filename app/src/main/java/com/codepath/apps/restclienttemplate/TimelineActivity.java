package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.TweetsAdapter;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class TimelineActivity extends AppCompatActivity {

    public static final String TAG = "TimelineActivity";
    TwitterClient client;
    RecyclerView rvTweets;
    List<Tweet> tweetList;
    TweetsAdapter tweetsAdapter;
    Button btLogout;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        Toast.makeText(this, "Compose Icon Clicked", Toast.LENGTH_LONG).show();

        // Go to a new activity when the compose menu item is selected.
        if (itemId == R.id.compose) {
            Intent intent = new Intent(this, ComposeActivity.class);
            startActivity(intent);
        }
        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        // Initialize the client and GET the data from the API
        // with the help of the JsonHTTPHandler to respond onSuccess
        // and onFailure
        /**
         * NOTE to self: implement the above comment.
         */
        client = TwitterApp.getRestClient(this);

        // Find the RecyclerView.
        rvTweets = findViewById(R.id.rvTweets);

        // Logout button on click listener.
        btLogout = findViewById(R.id.btLogout);
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.clearAccessToken();
                finish();
            }
        });

        btLogout.setText("LOG OUT");

        // Initialize the tweets and TweetAdapter.
        tweetList = new ArrayList<>();
        tweetsAdapter = new TweetsAdapter(this, tweetList);
        // Set up the RecyclerView: Layout Manager and the adapter.
        rvTweets.setLayoutManager(new LinearLayoutManager(this));
        rvTweets.setAdapter(tweetsAdapter);

        populateHomeTimeline();


    }

    private void populateHomeTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "onFailure: " + response);
            }

            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i(TAG, "onSuccess");

                JSONArray jsonArray = json.jsonArray;
                try {
                    tweetList.addAll(Tweet.fromJsonArray(jsonArray));
                    tweetsAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.i(TAG, "JSON Exception", e);
                }
            }
        });
    }


}