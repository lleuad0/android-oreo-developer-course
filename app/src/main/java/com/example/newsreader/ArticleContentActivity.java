package com.example.newsreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.mbms.DownloadStatusListener;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class ArticleContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_content);

        Intent intent = getIntent();
        WebView webView = findViewById(R.id.webView);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(intent.getStringExtra("url"));

    }

}
