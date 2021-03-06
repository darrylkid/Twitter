package com.codepath.apps.restclienttemplate.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {
    List<Tweet> tweetList;
    Context context;


    public TweetsAdapter(Context context, List<Tweet> tweetList) {
        this.tweetList = tweetList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        // Get the data at the position.
        Tweet tweet = tweetList.get(position);

        // Bind the tweet with the view holder.
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweetList.size();
    }


    // Clean all elements of the recycler
    public void clear() {
        tweetList.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        tweetList.addAll(list);
        notifyDataSetChanged();
    }



    // Pass in context and list of tweets.

    // For each row, inflate a layout for a tweet.

    // Bind values based on the position of the element.

    // Define a View Holder
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProfileImage;
        ImageView ivTweetImage;
        TextView tvScreenName;
        TextView tvBody;
        TextView tvCreatedAt;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvBody = itemView.findViewById(R.id.tvBody);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
            ivTweetImage = itemView.findViewById(R.id.ivTweetImage);
        }

        public void bind(Tweet tweet) {
            // Bind the data into the viewholder.
            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            tvCreatedAt.setText(tweet.createdAt);
            Glide.with(context).load(tweet.user.profileImageUrl).into(ivProfileImage);

            if (tweet.tweetImageUrl.equals("")) {
                ivTweetImage.setVisibility(View.GONE);
            } else {
                Glide.with(context).load(tweet.tweetImageUrl).into(ivTweetImage);
                ivTweetImage.setVisibility(View.VISIBLE);
            }



        }


    }
}
