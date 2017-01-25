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

# ScreenShots

<img src="https://firebasestorage.googleapis.com/v0/b/delhi06-31a81.appspot.com/o/1ibu07.gif?alt=media&token=302d95b2-349a-493b-bb0d-22ee2e97de14">&nbsp;&nbsp;
<img src="https://firebasestorage.googleapis.com/v0/b/delhi06-31a81.appspot.com/o/news2.jpg?alt=media&token=eed4e243-00a0-46fa-a83a-002163e48f33" width=280/>&nbsp;&nbsp;
<img src="https://firebasestorage.googleapis.com/v0/b/delhi06-31a81.appspot.com/o/news3.jpg?alt=media&token=1efd6cce-de3f-4251-b2bf-da6ceafb75d9" width=280/></br></br></br></br>
<img src="https://firebasestorage.googleapis.com/v0/b/delhi06-31a81.appspot.com/o/news4.5.jpg?alt=media&token=aac96d32-46a6-409e-9ed8-9a7cecd739ec" width=280/>&nbsp;&nbsp; 
<img src="https://firebasestorage.googleapis.com/v0/b/delhi06-31a81.appspot.com/o/news4.jpg?alt=media&token=3cc79c54-7fa6-402a-8e6d-64936e68b055" width=280/>&nbsp;&nbsp;
<img src="https://firebasestorage.googleapis.com/v0/b/delhi06-31a81.appspot.com/o/news5.jpg?alt=media&token=605e9035-b557-4485-b6aa-a6d2dc3b3d4e" width=280/>

Video Demo:
-----------------
https://www.youtube.com/watch?v=Eu3moiT2LhM

<b> Powered By News API (https://newsapi.org/) </b>
