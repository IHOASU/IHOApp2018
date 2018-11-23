package com.iho.asu.Pages;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.iho.asu.R;

/**
 * Created by Barathi on 5/26/2014.
 * Edited by Rachana Kashyap on 11/18/2018
 */
public class Lucy extends Fragment {

    public Activity mainActivity;
    public Lucy(){}
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(
                R.layout.fragment_lucy, container, false);
        WebView webView = (WebView)(v.findViewById(R.id.webView));
        WebSettings webSettings = webView.getSettings();
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/lucy.html");
        return v;
    }

    @Override
    public void onStart(){
        super.onStart();
    }
}
