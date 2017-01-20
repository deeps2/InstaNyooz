package com.shikhar.instanyooz;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.drawer_layout)DrawerLayout mDrawer;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.subtitle) TextView toolbarSubtitle;
    @BindView(R.id.title) TextView toolbarTitle;
    @BindView(R.id.noData)LinearLayout noData;
    @BindView(R.id.no_Internet)LinearLayout noNet;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.empty_view) View emptyView;
    @BindView(R.id.refresh) ImageView refresh;
    @BindView(R.id.swipeRefresh) SwipeRefreshLayout mSwipeRefresh;

    @BindView(R.id.recyclerView)RecyclerView recyclerView;
                                ArticlesAdapter adapter;

    String url = "https://newsapi.org/v1/";
    String apiKey = "455e3b21f82f42aabfb438d4204d6ceb";
    String retrofitUrlSourceName;
    List<Article> listOfArticles = new ArrayList<>();

    // Make sure to be using android.support.v7.app.ActionBarDrawerToggle version.
    // The android.support.v4.app.ActionBarDrawerToggle has been deprecated.
    private ActionBarDrawerToggle drawerToggle;

    boolean flag = true; //flag to hide progress bar when swipe refresh is triggered

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
        String restoredRetrofitUrlSourceName = sharedPref.getString("URL_SOURCE_NAME", null);
        navigationItemAction(restoredSubTitle, restoredRetrofitUrlSourceName);
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.bbc_news) {
            navigationItemAction(getString(R.string.bbc_news_name),getString(R.string.bbc_news_source));
        } else if (id == R.id.bbc_sport) {
            navigationItemAction(getString(R.string.bbc_sport_name),getString(R.string.bbc_sport_source));
        } else if (id == R.id.cnn) {
            navigationItemAction(getString(R.string.cnn_name),getString(R.string.cnn_source));
        } else if (id == R.id.google) {
            navigationItemAction(getString(R.string.google_name),getString(R.string.google_source));
        } else if (id == R.id.national_geographic) {
            navigationItemAction(getString(R.string.national_geographic_name),getString(R.string.national_geographic_source));
        } else if (id == R.id.sky_news) {
            navigationItemAction(getString(R.string.sky_news_name),getString(R.string.sky_news_source));
        } else if (id == R.id.reddit) {
            navigationItemAction(getString(R.string.reddit_name),getString(R.string.reddit_source));
        } else if (id == R.id.cnbc) {
            navigationItemAction(getString(R.string.cnbc_name),getString(R.string.cnbc_source));
        } else if (id == R.id.entertainment) {
            navigationItemAction(getString(R.string.entertainment_name),getString(R.string.entertainment_source));
        } else if (id == R.id.new_scientist) {
            navigationItemAction(getString(R.string.new_scientist_name),getString(R.string.new_scientist_source));
        } else if (id == R.id.techcrunch) {
            navigationItemAction(getString(R.string.techcrunch_name),getString(R.string.techcrunch_source));
        }

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);

        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    private void navigationItemAction(String sourceName, String urlSourceName) {

          if(sourceName == null){
              toolbarSubtitle.setText(getString(R.string.bbc_news_name));
              retrofitUrlSourceName = getString(R.string.bbc_news_source);
          }
          else{
              toolbarSubtitle.setText(sourceName);

              SharedPreferences.Editor sharedPref = this.getPreferences(Context.MODE_PRIVATE).edit();
              sharedPref.putString("SUBTITLE", sourceName);
              sharedPref.putString("URL_SOURCE_NAME", urlSourceName);
              sharedPref.commit();

              retrofitUrlSourceName = urlSourceName;
          }

          emptyView.setVisibility(View.VISIBLE);
          if(flag)
            progressBar.setVisibility(View.VISIBLE);
          noData.setVisibility(View.INVISIBLE);
          noNet.setVisibility(View.INVISIBLE);

         //check network connection
        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected)
            getRetrofitArray(retrofitUrlSourceName, apiKey);

        else {
            emptyView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            noData.setVisibility(View.INVISIBLE);
            noNet.setVisibility(View.VISIBLE);
            mSwipeRefresh.setRefreshing(false);
            flag = true; //restore flag to old state(i.e. true)
        }
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.
        // ActionBarDrawToggle() does not require it and will not render the hamburger icon without it.
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

        //set layoutmanager for recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ArticlesAdapter(listOfArticles, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        //set onRefreshListener on SwipeRefreshLayout
        mSwipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

        //The refresh indicator starting and resting position is always positioned near the top of the refreshing content.
        // This position is a consistent location, but can be adjusted in either direction based on whether or not there is a toolbar or actionbar present.
        mSwipeRefresh.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        24,
                        getResources().getDisplayMetrics()));
        mSwipeRefresh.setOnRefreshListener(this);

        //when refresh icon below No Data Found message is clicked
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Setup drawer view
                setupDrawerContent(navigationView);
            }
        });
        // Setup drawer view
        setupDrawerContent(navigationView);
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

    @Override //swipe refresh layout
    public void onRefresh() {
        flag = false;
        setupDrawerContent(navigationView);
    }

    public interface NewsArrayInterface {

       // https://newsapi.org/v1/articles?source=time&apiKey=<key>
        @GET("articles")
        Call<NewsResponse> getArticles(@Query("source") String newsSource, @Query("apiKey") String apiKey);

    }

    void getRetrofitArray(String newsSource, String apiKey) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewsArrayInterface serviceRequest = retrofit.create(NewsArrayInterface.class);

        Call<NewsResponse> call = serviceRequest.getArticles(newsSource, apiKey);

        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {

                int statusCode = response.code();

                if(statusCode != 200) {
                    emptyView.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    noData.setVisibility(View.VISIBLE);
                    noNet.setVisibility(View.INVISIBLE);
                    return;
                }

                listOfArticles = response.body().getArticles();
                adapter.setDataAdapter(listOfArticles);
                adapter.notifyDataSetChanged();

                progressBar.setVisibility(View.INVISIBLE);
                emptyView.setVisibility(View.INVISIBLE);
                noData.setVisibility(View.INVISIBLE);
                noNet.setVisibility(View.INVISIBLE);
                mSwipeRefresh.setRefreshing(false);
                flag = true; //restore flag to old state(i.e. true)
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }

        });
    }

    @Override
    public void onBackPressed() {  //when back pressed check drawer is open or not
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}


