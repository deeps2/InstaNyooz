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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
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


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.drawer_layout)DrawerLayout mDrawer;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.subtitle) TextView toolbarSubtitle;
    @BindView(R.id.title) TextView toolbarTitle;

    @BindView(R.id.recyclerView)RecyclerView recyclerView;
                                ArticlesAdapter adapter;

    String url = "https://newsapi.org/v1/";
    String apiKey = "455e3b21f82f42aabfb438d4204d6ceb";
    String retrofitUrlSourceName;
    List<Article> listOfArticles = new ArrayList<>();

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

          getRetrofitArray(retrofitUrlSourceName, apiKey);

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

        //set layoutmanager for recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ArticlesAdapter(listOfArticles);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true); //TODO

        // Setup drawer view
        setupDrawerContent(navigationView);




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

               // NewsResponse jsonResponse = response.body();
              //  listOfArticles = new ArrayList<>(Arrays.asList(jsonResponse.getArticles()));
               // adapter = new DataAdapter(data);
               // recyclerView.setAdapter(adapter);

          //      if(listOfArticles != null){
            //        listOfArticles.clear();
            //        adapter.notifyDataSetChanged();
            //    }
                listOfArticles = response.body().getArticles();
                //ArticlesAdapter adapter2 = new ArticlesAdapter(listOfArticles);
                //recyclerView.setAdapter(adapter2 );
        //        adapter = new ArticlesAdapter(listOfArticles);

                        adapter.setDataAdapter(listOfArticles);

                adapter.notifyDataSetChanged();
                //adapter2.notifyDataSetChanged();

                int q=0;
                int qq=2;
                //adapter.notifyDataSetChanged();


                //adapter.notifyDataSetChanged();
      //1          adapter = new ArticlesAdapter(listOfArticles);
      // 2         recyclerView.setAdapter(adapter);   //TODO
             //   adapter.notifyDataSetChanged();  //TODO
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
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
//where to put code for notify data set changed...run app leave for sometime and see kee data change hua kya or i think when u do swipe then i have to make retrofit call and dataswap change...or maybe
//dataswap change call not necesssary as in retrofit boy new adapter is being created...or see newsapp...
//i think it will be used when no internet case or no news case...
//by debugger simulate no news case and no net case

//theek karna notifydatasetchange etc vagereh...first do a commit then modify
//move adapter to 1st position when refreshed and when new item is selected
//animation for thumbnails