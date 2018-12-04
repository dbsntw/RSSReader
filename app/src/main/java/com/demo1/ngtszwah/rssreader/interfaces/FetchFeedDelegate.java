package com.demo1.ngtszwah.rssreader.interfaces;

import com.demo1.ngtszwah.rssreader.models.Feed;

import java.io.InputStream;

public interface FetchFeedDelegate {
    void fetchFeedFinished(Feed feed);
}
