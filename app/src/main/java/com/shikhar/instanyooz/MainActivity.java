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
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.drawer_layout)DrawerLayout mDrawer;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.subtitle) TextView toolbarSubtitle;
    @BindView(R.id.title) TextView toolbarTitle;
                          String url = "https://newsapi.org/v1/";
                          String apikey = "455e3b21f82f42aabfb438d4204d6ceb";
                          String retrofitSourceName;

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
              retrofitSourceName = getString(R.string.bbc_news_name);
          }
          else{
              toolbarSubtitle.setText(sourceName);
              SharedPreferences.Editor sharedPref = this.getPreferences(Context.MODE_PRIVATE).edit();
              sharedPref.putString("SUBTITLE", sourceName);
              retrofitSourceName = sourceName;
              sharedPref.commit();
          }

        // load data :)
    //    TODO: onRefresh();

     //   TODO: getLoaderManager().restartLoader(0, null, this);
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

        getRetrofitArray(retrofitSourceName, apikey);

        /*
        // sample code snippet to set the text content on the ExpandableTextView
        ExpandableTextView expTv1 = (ExpandableTextView)findViewById(R.id.expand_text_view);

// IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTv1.setText("hello. a quick brown fox jumps over a lazy dog. hello. a quick brown fox jumps over a lazy dog. hello. a quick brown fox jumps over a lazy dog. hello. a quick brown fox jumps over a lazy dog. hello. a quick brown fox jumps over a lazy dog. hello. a quick brown fox jumps over a lazy dog. hello. a quick brown fox jumps over a lazy dog. hello. a quick brown fox jumps over a lazy dog. hello. a quick brown fox jumps over a lazy dog. hello. a quick brown fox jumps over a lazy dog. hello. a quick brown fox jumps over a lazy dog.");
    */
    }

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

    public interface RetrofitArrayAPI {

       // https://newsapi.org/v1/articles?source=time&apiKey=<key>
        @GET("articles")
        Call<List<Article>> getArticles(@Query("source") String newsSource, @Query("key") String apikey);

    }

    void getRetrofitArray(String newsSource, String apiKey) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitArrayAPI service = retrofit.create(RetrofitArrayAPI.class);

        Call<List<Article>> call = service.getArticles(newsSource, apikey);

        call.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                try {

                    List<Article> articles = response.body();

                    for (int i = 0; i < articles.size(); i++) {

                      /*  if (i == 0) {
                            text_id_1.setText("StudentId  :  " + StudentData.get(i).getStudentId());
                            text_name_1.setText("StudentName  :  " + StudentData.get(i).getStudentName());
                            text_marks_1.setText("StudentMarks  : " + StudentData.get(i).getStudentMarks());
                        } else if (i == 1) {
                            text_id_2.setText("StudentId  :  " + StudentData.get(i).getStudentId());
                            text_name_2.setText("StudentName  :  " + StudentData.get(i).getStudentName());
                            text_marks_2.setText("StudentMarks  : " + StudentData.get(i).getStudentMarks());
                        }*/
                    }


                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }

        });
    }
}

//TODO:
// IMPLEMENT SIDE DRAWER(done). ..BUTTERKNIFE...

// IMPLEMENT NO NET CONNECTION....no internet ke liye photo and text..put it in framelayout also
// IF TIME THEN ONLY NO NEWS FOUND
//swipe refresh layout...SEE ALL LAYOUTS MUSTAFA KA...KAUN KAUN SA USE KAROO MAI BHEE
//....check every json key if its null and if not then its key.empty""
// handle for all key:value pairs and value is null, default me kya image,
        //kaun author(paper name default ho jayga), default image(newspaper ka logo) like that

//orientation change pe remain data (keyboard|hidden in manifest)
//loading spinner on start add this to frame layout as well
//how to open link inside app me chrome plugin like?? -- webview i think