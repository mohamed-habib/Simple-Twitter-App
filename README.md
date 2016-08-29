# Simple-Twitter-App that show the list of followers, their profile and last 10 tweets.

I used <a href = https://get.fabric.io/ > Fabric </a> for handling user Login.<br>
I used <a href = https://github.com/mttkay/signpost > SignPost </a>  Lib. for signing the request before calling it.<br>
I used  <a href =https://api.twitter.com/1.1/followers/ids.json> this </a> API to get the followers Ids, then <a href= https://api.twitter.com/1.1/users/lookup.json >this</a> API to get their profile data <br>
I used <a href =https://api.twitter.com/1.1/statuses/user_timeline.json> this </a> API to get the last 10 tweets <br>
I used <a href =https://github.com/square/picasso/> Picasso </a> for downloading and caching images <br>
I used <a href =https://github.com/Raizlabs/DBFlow/> DBFlow </a> to cache followers requests for offline access <br>
I followed Material Design Guidelines, as i used card views and SwipeToRefreshLayout and CollapsingToolbarLayout .... etc<br>

Before running this app, make sure to get the Consumer Key and Consumer Secret after registering your app  <a href = https://apps.twitter.com/> here </a> and add them to AppConstClass.java.
and follow <a href=https://get.fabric.io/android > Fabric For Android </a> guide to add Fabric to Android Studio and use it to add Login with Twitter.

Download the App. APK <a href =https://github.com/mohamed-habib/Simple-Twitter-App/blob/master/Simple%20Twitter%20App.apk?raw=true > here </a>
