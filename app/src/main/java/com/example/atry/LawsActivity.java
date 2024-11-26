package com.example.atry;

import android.os.Bundle;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;

public class LawsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laws);

        WebView webView = findViewById(R.id.webView_laws);
        webView.loadUrl("file:///android_asset/index" +
                ".html"); // Load content from the assets folder
    }
}
