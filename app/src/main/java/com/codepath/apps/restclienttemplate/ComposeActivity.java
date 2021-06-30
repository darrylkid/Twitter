package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ComposeActivity extends AppCompatActivity {

    final static int MAX_TWEET_LENGTH = 140;
    EditText etCompose;
    Button btnTweet;

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
                int tweetLength = etCompose.getText().toString().length();
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
                // tell the Twitter API to create a new tweet.
                Toast.makeText(ComposeActivity.this, "Tweet Submitted", Toast.LENGTH_LONG).show();

            }
        });

    }
}