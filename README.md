# RSSReader

![Table of a demo feed](https://holland.pk/uptow/i4/27e2317ad14c0ebf6a2cbb37c5ac769e.jpg)
![Webview loading the link of a single entry](https://holland.pk/uptow/i4/487d0362aa317cab28ff59130d67e4e5.jpg)

A simple Android RSS Reader app that downloads and parses any online RSS XML file asynchronously and display all feeds contained in a table. (A feed is hardcoded into the project for demo purpose)

# Getting Started

1. The handler that is responsible for receiving the RSS Feed data has to implement the FetchFeedDelegate interface.
```
public class MainActivity extends AppCompatActivity implements FetchFeedDelegate
```
2. Write your feed data handler code by implementing the callback function defined by the interface.

```
@Override
    public void fetchFeedFinished(Feed feed) {
      //access your feed data here
    }
    
```
3. Fetch the online feed by calling the static method with the delegate handler and the feed URL as parameters.
```
FetchFeedStreamTask.FetchFeedByURL(this, new URL("https://www.wsj.com/xml/rss/3_7085.xml"));
```
# Lightweight Feed & FeedItem data model

```
public class Feed {

    String title;
    String link;
    String description;
    String pubDate;
    ArrayList<FeedItem> items;
    ...
}
    
public class FeedItem {

    String title;
    String description;
    String link;
    String author;
    String imageUrl;
    String pubDate;
    String dateString;
    ...
}
    
```
# Other demo features and functionality
* Displaying the feed with a RecyclerView (with a neat & reusable card view)
* Swipe-to-refresh
* Displaying the feed item's link with a WebView page

# Other possibilities
* Porting the RecyclerView to a fragment would allow the embedding of the table in a more complexed UI (Reasonable for tablet UI, less so for phone)


