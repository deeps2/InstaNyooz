package com.shikhar.instanyooz;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.drawer_layout)DrawerLayout mDrawer;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.subtitle) TextView toolbarSubtitle;
    @BindView(R.id.title) TextView toolbarTitle;

    // Make sure to be using android.support.v7.app.ActionBarDrawerToggle version.
    // The android.support.v4.app.ActionBarDrawerToggle has been deprecated.
    private ActionBarDrawerToggle drawerToggle;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))
                return true;

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                }
        );

        //read share preference and call
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String restoredSubTitle = sharedPref.getString("SUBTITLE", null);
        navigationItemAction(restoredSubTitle);
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.bbc_news) {
            navigationItemAction(getString(R.string.bbc_news_name));
        } else if (id == R.id.bbc_sport) {
            navigationItemAction(getString(R.string.bbc_sport_name));
        } else if (id == R.id.cnn) {
            navigationItemAction(getString(R.string.cnn_name));
        } else if (id == R.id.google) {
            navigationItemAction(getString(R.string.google_name));
        } else if (id == R.id.national_geographic) {
            navigationItemAction(getString(R.string.national_geographic_name));
        } else if (id == R.id.sky_news) {
            navigationItemAction(getString(R.string.sky_news_name));
        } else if (id == R.id.reddit) {
            navigationItemAction(getString(R.string.reddit_name));
        } else if (id == R.id.cnbc) {
            navigationItemAction(getString(R.string.cnbc_name));
        } else if (id == R.id.entertainment) {
            navigationItemAction(getString(R.string.entertainment_name));
        } else if (id == R.id.new_scientist) {
            navigationItemAction(getString(R.string.new_scientist_name));
        } else if (id == R.id.techcrunch) {
            navigationItemAction(getString(R.string.techcrunch_name));
        }

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);

        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    private void navigationItemAction(String sourceName) {

          if(sourceName == null){
              toolbarSubtitle.setText(getString(R.string.bbc_news_name));
          }
          else{
              toolbarSubtitle.setText(sourceName);
              SharedPreferences.Editor sharedPref = this.getPreferences(Context.MODE_PRIVATE).edit();
              sharedPref.putString("SUBTITLE", sourceName);
              sharedPref.commit();
          }

        // load data :)
    //    onRefresh();

     //   getLoaderManager().restartLoader(0, null, this);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Set a Toolbar to replace the ActionBar.
        setSupportActionBar(toolbar);
        //hide Title so that I can populate my own Title and Subtitle in TextViews corresponding to them
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Typeface font = Typeface.createFromAsset(this.getAssets(), "Righteous-Regular.ttf");
        toolbarTitle.setText(getString(R.string.app_name));
        toolbarTitle.setTypeface(font);

        // Tie DrawerLayout events to the ActionBarToggle
        drawerToggle = setupDrawerToggle();
        mDrawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();  //IMPORTANT: without this hamburger icon will not come

        // Setup drawer view
        setupDrawerContent(navigationView);

        /*
        // sample code snippet to set the text content on the ExpandableTextView
        ExpandableTextView expTv1 = (ExpandableTextView)findViewById(R.id.expand_text_view);

// IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTv1.setText("hello. a quick brown fox jumps over a lazy dog. hello. a quick brown fox jumps over a lazy dog. hello. a quick brown fox jumps over a lazy dog. hello. a quick brown fox jumps over a lazy dog. hello. a quick brown fox jumps over a lazy dog. hello. a quick brown fox jumps over a lazy dog. hello. a quick brown fox jumps over a lazy dog. hello. a quick brown fox jumps over a lazy dog. hello. a quick brown fox jumps over a lazy dog. hello. a quick brown fox jumps over a lazy dog. hello. a quick brown fox jumps over a lazy dog.");
    */}


    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE 1: Make sure to override the method with only a single `Bundle` argument
    // Note 2: Make sure you implement the correct `onPostCreate(Bundle savedInstanceState)` method.
    // There are 2 signatures and only `onPostCreate(Bundle state)` shows the hamburger icon.
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }
}


// IMPLEMENT SIDE DRAWER(done). ..BUTTERKNIFE...
// IMPLEMENT NO NET CONNECTION....IF TIME THEN ONLY NO NEWS FOUND
//no internet ke liye photo and text..put it in framelayout also
//swipe refresh layout...SEE ALL LAYOUTS MUSTAFA KA...KAUN KAUN SA USE KAROO MAI BHEE