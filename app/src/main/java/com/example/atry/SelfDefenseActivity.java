package com.example.atry;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class SelfDefenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_defense);

        WebView webViewSelfDefense = findViewById(R.id.webViewSelfDefense);
        webViewSelfDefense.setWebViewClient(new WebViewClient()); // Ensures links open within the WebView
        webViewSelfDefense.getSettings().setJavaScriptEnabled(true); // Enable JavaScript if necessary

        // Load the self-defense HTML file from assets
        webViewSelfDefense.loadUrl("file:///android_asset/self_defense.html");
    }
}
