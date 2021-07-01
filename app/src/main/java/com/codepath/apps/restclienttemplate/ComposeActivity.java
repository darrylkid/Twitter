package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class ComposeActivity extends AppCompatActivity {

    final static String TAG = "ComposeActivity";
    final static int MAX_TWEET_LENGTH = 280;
    EditText etCompose;
    Button btnTweet;
    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        etCompose = findViewById(R.id.etCompose);
        btnTweet = findViewById(R.id.btnTweet);

        // Set up an onClickListener for the tweet button.
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // We want to make sure the tweet is not too short or too long.
                String tweetContent = etCompose.getText().toString();
                int tweetLength = tweetContent.length();
                String errorMsg;


                if (tweetLength == 0) {
                    errorMsg = "Sorry, your tweet cannot be empty";
                    Toast.makeText(ComposeActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                    return;
                }
                if (tweetLength > ComposeActivity.MAX_TWEET_LENGTH) {
                    errorMsg = "Sorry, your tweet is too large. It must be less than "
                                + ComposeActivity.MAX_TWEET_LENGTH + " characters";
                    Toast.makeText(ComposeActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                    return;
                }

                // When the tweet is the correct length and the tweet button is clicked,
                // tell the Twitter API to create a new tweet
                client = TwitterApp.getRestClient(ComposeActivity.this);
                client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        // Let the user know via Toast that their tweet has been submitted.
                        // Go back to the TimelineActivity page.
                        Log.i(TAG, "onSuccess to publish tweet");
                        try {
                            Tweet tweet = Tweet.fromJson(json.jsonObject);
                            // Package data into an intent
                            Intent intent = new Intent();
                            intent.putExtra("tweet", Parcels.wrap(tweet));

                            // Set the result code.
                            setResult(RESULT_OK, intent);

                            // Close the Compose Activity and pass the data to the parent activity.
                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.i(TAG, "onFailure to publish tweet", throwable);
                    }
                });

            }
        });

    }
}