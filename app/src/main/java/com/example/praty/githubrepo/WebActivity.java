package com.example.praty.githubrepo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

public class WebActivity extends AppCompatActivity {

    ImageButton mShare;
    String mUrl=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mUrl=getIntent().getStringExtra("url");
        mShare=(ImageButton) findViewById(R.id.shareLink);
        final ProgressBar progressBar=(ProgressBar) findViewById(R.id.webProgress);
        progressBar.setVisibility(View.VISIBLE);
        WebView webView=(WebView) findViewById(R.id.news_web_view);

        webView.getSettings().setJavaScriptEnabled(true);

        //load the url of the article in a webView
        webView.loadUrl(mUrl);

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);    //stop displaying the progress bar when the page has finished loading

            }
        });

        //Intent to share the article link to other applications on the phone
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_SUBJECT, "Link of the repository");
                share.putExtra(Intent.EXTRA_TEXT, mUrl);
                startActivity(Intent.createChooser(share, "Share link:"));
            }
        });
    }
}
