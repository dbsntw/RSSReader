package com.demo1.ngtszwah.rssreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class RSSWebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rssweb);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the Url string passed by mainActivity to load the corresponding web page
        WebView webView = findViewById(R.id.rssWebView);
        webView.loadUrl(message);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
