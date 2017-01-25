# InstaNyooz <img src="app/src/main/res/mipmap-hdpi/ic_launcher.png" />
App to fetch top News Articles across all categories from 55 different sources.

# Features
- Read News Articles from 55 different News sources covering all categories(sports, science, politics, tech etc.)
- Read short description of the news WITHIN THE APP
- Tap the Headline of any article to visit parent news site and read full news
- share articles with your friends (on watsapp, messenger etc) by clicking on share button
- Do a vertical swipe to refresh the news feed
- News sorted according to the order they appeaer on the source's homepage
- Display time when an article was pubished(5hrs ago, 7hrs ago) and an image thumbnail belonging to that article

# Components Used
- Material Design (Toolbar, AppBarLayout, Coordinator Layout)
- Navigation Drawer
- RecyclerView and custom RecyclerView.Adapter<>
- Animation  (fade_in for splash activity)
- Shared Preferences (to save the last opened News source)
- Custom font (for splash activity and title on toolbar)
- Some famous 3rd party Libraries (see below)

# Libraries
- Retrofit2   (to make Network Calls and deserializing JSON Response to Java Objects)
- ButterKnife (for simply injecting the views findViewByID())
- Glide       (to load the image from URL inside JSON response)
- ExpandableTextView https://github.com/Manabu-GT/ExpandableTextView
- RecyclerView, CardView

Video Demo:
-----------------
https://www.youtube.com/watch?v=Eu3moiT2LhM


<b> Powered By News API (https://newsapi.org/) </b>
