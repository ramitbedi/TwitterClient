package com.codepath.apps.restclienttemplate.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.RestApplication;
import com.codepath.apps.restclienttemplate.RestClient;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.TwitterMedia;
import com.codepath.apps.restclienttemplate.models.TwitterUser;
import com.squareup.picasso.Picasso;

import java.util.List;

;

public class TweetsArrayAdapter extends ArrayAdapter {
    Context parentContext;
    RestClient client;

    private static class ViewHolder {
        ImageView ivUserProfile;
        TextView tvUserName;
        TextView tvTweet;
        TextView tvTime;
        ImageView ivMedia;

    }

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, R.layout.tweet, tweets);
        parentContext = context;
        client = RestApplication.getRestClient();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tweet, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.ivUserProfile = (ImageView) convertView.findViewById(R.id.ivUserProfile);
            viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);

            viewHolder.tvTweet = (TextView) convertView.findViewById(R.id.tvTweet);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            viewHolder.ivMedia = (ImageView) convertView.findViewById(R.id.ivMedia);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Tweet tweet = (Tweet) getItem(position);
        TwitterUser user = tweet.getUser();
        TwitterMedia media = tweet.getMedia();


        viewHolder.ivUserProfile.setImageResource(0);

        viewHolder.ivUserProfile.setTag(user.getUserId());
        Picasso.with(getContext()).load(user.getProfile_image_url()).resize(52, 52).into(viewHolder.ivUserProfile);

        viewHolder.tvUserName.setTag(tweet.getTweet_id());
        viewHolder.tvUserName.setText(user.getName());

        viewHolder.tvTime.setText(tweet.getRelativeTimeCreated());
        viewHolder.tvTweet.setText(Html.fromHtml(tweet.getText()));

        viewHolder.ivMedia.setTag(tweet.getTweet_id());
        viewHolder.ivMedia.setImageResource(0); viewHolder.ivMedia.setImageDrawable(null);
        if (media != null) {
            Picasso.with(getContext()).load(media.getMedia_url()).into(viewHolder.ivMedia);
        }




        return convertView;
    }

    private void incrementTv(TextView view) {
        if (view == null) return;

        int count = 1;
        if (view.getText() != null && !view.getText().toString().isEmpty())
            count = Integer.valueOf(view.getText().toString()) + 1;
        view.setText(String.valueOf(count));
    }

    private void decrementTv(TextView view) {
        if (view == null) return;

        if (!view.getText().toString().isEmpty()) {
            int count = Integer.valueOf(view.getText().toString()) - 1;
            view.setText(count > 0 ? String.valueOf(count) : "");
        }
    }

    private void addAnimation(View v) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 2, 1, 2, v.getWidth() / 2.0F, v.getHeight() / 2.0F);
        scaleAnimation.setDuration(500);
        v.startAnimation(scaleAnimation);
    }





}
