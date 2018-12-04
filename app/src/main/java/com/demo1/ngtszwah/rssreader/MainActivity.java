package com.demo1.ngtszwah.rssreader;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.demo1.ngtszwah.rssreader.adapters.FeedItemRecyclerViewAdapter;
import com.demo1.ngtszwah.rssreader.interfaces.FetchFeedDelegate;
import com.demo1.ngtszwah.rssreader.interfaces.OnItemClickListener;
import com.demo1.ngtszwah.rssreader.models.Feed;
import com.demo1.ngtszwah.rssreader.models.FeedItem;
import com.demo1.ngtszwah.rssreader.network.FetchFeedStreamTask;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements FetchFeedDelegate, SwipeRefreshLayout.OnRefreshListener {

    public static final String EXTRA_MESSAGE = "com.demo1.ngtszwah.MESSAGE";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        try {
            mSwipeRefreshLayout.setRefreshing(true);
            url = new URL("https://www.wsj.com/xml/rss/3_7085.xml");
            FetchFeedStreamTask.FetchFeedByURL(this, url);
        }
        catch(MalformedURLException e){

        }
    }

    // Tinting the refresh icon to white
    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        getMenuInflater().inflate( R.menu.menu, menu );
        Drawable drawable = menu.findItem(R.id.menu_refresh).getIcon();

        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, ContextCompat.getColor(this,R.color.white));
        menu.findItem(R.id.menu_refresh).setIcon(drawable);
        return true;
    }

    // Method required by the FetchFeedDelegate as a callback handler
    @Override
    public void fetchFeedFinished(Feed feed) {

        mAdapter = new FeedItemRecyclerViewAdapter(feed,new OnItemClickListener() {
            @Override public void onItemClick(FeedItem item) {
                Log.d("clicked", (item.getTitle()));
                Intent intent = new Intent(getApplicationContext(), RSSWebActivity.class);
                intent.putExtra(EXTRA_MESSAGE, item.getLink());
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        if (feed.getTitle() != null)
            getSupportActionBar().setTitle("RSSReader - " + feed.getTitle());
        else
            getSupportActionBar().setTitle("RSSReader");

        mSwipeRefreshLayout.setRefreshing(false);

    }

    // Refresh handler for the SwipeToRefresh gesture, also reused by the refresh action button on the action bar
    @Override
    public void onRefresh() {

        mSwipeRefreshLayout.setRefreshing(true);
        FetchFeedStreamTask.FetchFeedByURL(this, url);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_refresh:

                mSwipeRefreshLayout.setRefreshing(true);

                onRefresh();

                return true;
        }

        return super.onOptionsItemSelected(item);

    }
}
