# RSSReader

A simple Android RSS Reader app that downloads and parses any online RSS XML file asynchronously and display all feeds contained in a table. (A feed is hardcoded into the project for demo purpose)

# Getting Started

1. The handler that is responsible for receiving the RSS Feed data has to implement the FetchFeedDelegate interface.
```
public class MainActivity extends AppCompatActivity implements FetchFeedDelegate
```
2. Write your feed data handler code in the callback function of the interface.

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


