package com.shikhar.instanyooz;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    // when use animate view function you must implement this interface
    public interface AnimateView {
        public void onAnimationEnd();
    }

    public static void animateView(Context mContext, int animResource, View view, final AnimateView instance) {
        Animation fade0 = AnimationUtils.loadAnimation(mContext, animResource);
        view.startAnimation(fade0);
        fade0.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                instance.onAnimationEnd();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    // convert date to format as 5 hours ago
    public static String manipulateDateFormat(String post_date){

        if(post_date == null)
            return "";       //if no date is returned by the API then set corresponding date view to empty

        SimpleDateFormat existingUTCFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date date = null;
        try {
            date = existingUTCFormat.parse(post_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {
            // Converting timestamp into x ago format
            CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                    Long.parseLong(String.valueOf(date.getTime())),
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
            return timeAgo + "";
        }else {
            return post_date;
        }
    }
}

