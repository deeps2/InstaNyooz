package com.shikhar.instanyooz;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity implements Util.AnimateView {

    @BindView(R.id.activity_splash)
    LinearLayout linearLayout;
    @BindView(R.id.app_name)
    TextView appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        // change font of app_name text view
                //Util.changeViewTypeFace(this, "Righteous-Regular.ttf", appName);
        Typeface font = Typeface.createFromAsset(this.getAssets(), "Righteous-Regular.ttf");
        appName.setTypeface(font);

        // animate container of text views :)
        Util.animateView(this, R.anim.fade_in_enter, linearLayout, this);
    }

    @Override
    public void onAnimationEnd() {
        // do some thing
        startActivity(new Intent(SplashActivity.this, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }
}

/* FLAG_ACTIVITY_CLEAR_TASK will cause any existing task that would be associated with the activity to be
cleared before the activity is started. This way, when you load that FLAG_ACTIVITY_NEW_TASK, and you hit
the back button, you won't end up back at a login or sign up screen. That'd be a little awkward for our
users if they were already logged in and hit it by accident.

Think of it as a stack of papers. Log in on top, main activity on the bottom. When we call FLAG_ACTIVITY_CLEAR_TASK,
we remove the log in paper. Now we're looking at the main activity paper. But if we turn the page back, we don't see the log in paper! :)
*/