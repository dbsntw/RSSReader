package com.demo1.ngtszwah.rssreader.network;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import com.demo1.ngtszwah.rssreader.interfaces.FetchFeedDelegate;
import com.demo1.ngtszwah.rssreader.models.Feed;
import com.demo1.ngtszwah.rssreader.models.FeedItem;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FetchFeedStreamTask extends AsyncTask<Void, Void, Feed>
{
    private FetchFeedDelegate delegate;
    private URL url;

    private static final String TAG = "FetchFeedStreamTask";

    // static function to provide a more convenient interface to fetch the feed so that caller class does not have to handle the required flow of AsyncTask
    public static void FetchFeedByURL(FetchFeedDelegate delegate, URL url)
    {
        FetchFeedStreamTask task = new FetchFeedStreamTask(delegate,url);
        task.execute();
    }

    public FetchFeedStreamTask(FetchFeedDelegate delegate, URL url) {
        this.delegate = delegate;
        this.url = url;
    }

    // getting an InputStream from the RSS url and parse the stream as an XML document
    @Override
    protected Feed doInBackground(Void... voids) {
        Feed feed = new Feed();

        try {

            InputStream inputStream = url.openConnection().getInputStream();
            boolean isItem = false;
            String title = null, link = null, description = null, imageUrl = null, pubDate = null;


            try {
                XmlPullParser xmlPullParser = Xml.newPullParser();
                xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                xmlPullParser.setInput(inputStream, null);

                while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
                    int eventType = xmlPullParser.getEventType();

                    String name = xmlPullParser.getName();
                    if(name == null)
                        continue;

                    if(eventType == XmlPullParser.END_TAG) {
                        if(name.equalsIgnoreCase("item")) {

                                FeedItem item = new FeedItem();
                                item.setTitle(title);
                                item.setLink(link);
                                item.setDescription(description);
                                item.setPubDate(pubDate);
                                DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
                                Date date = formatter.parse(pubDate);
                                int hourDifference = (int)((new Date().getTime() - date.getTime())/(1000*60*60));
                                if (hourDifference <= 23)
                                    item.setDateString(Integer.toString((int)((new Date().getTime() - date.getTime())/(1000*60*60)))+"h");
                                else
                                    item.setDateString(Integer.toString((int)((new Date().getTime() - date.getTime())/(1000*60*60*24)))+"d");

                                if (imageUrl.startsWith("http://"))
                                {
                                    imageUrl = imageUrl.substring(0, 4) + "s" + imageUrl.substring(4, imageUrl.length());
                                }
                                item.setImageUrl(imageUrl);
                                feed.getItems().add(item);
                                title = null;
                                link = null;
                                description = null;
                                imageUrl = null;
                                pubDate = null;
                                isItem = false;

                        }
                        continue;
                    }

                    if (eventType == XmlPullParser.START_TAG) {
                        if(name.equalsIgnoreCase("item")) {
                            isItem = true;
                            continue;
                        }
                        if (name.equalsIgnoreCase("media:content"))
                        {
                            imageUrl = xmlPullParser.getAttributeValue(null, "url");
                        }
                    }


                    String result = "";
                    int temp = xmlPullParser.next();
                    if (temp == XmlPullParser.TEXT) {
                        result = xmlPullParser.getText();
                        xmlPullParser.nextTag();
                    }

                    if (name.equalsIgnoreCase("title")) {
                        title = result;
                        if (!isItem)
                            feed.setTitle(title);
                    } else if (name.equalsIgnoreCase("link")) {
                        link = result;
                    } else if (name.equalsIgnoreCase("description")) {
                        description = result;
                    }else if (name.equalsIgnoreCase("pubDate")) {
                        pubDate = result;
                    }


                }

                return feed;
            } catch (Exception e){

            }finally {
                inputStream.close();
            }
            return feed;
        } catch (IOException e) {
            Log.e(TAG, "Error", e);
        } 
        return null;
    }

    // AsyncTask callback which is bridged to a custom callback function for easier use
    @Override
    protected void onPostExecute(Feed feed) {
        super.onPostExecute(feed);
        if (delegate != null)
            delegate.fetchFeedFinished(feed);
    }
}
