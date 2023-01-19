package com.example.httptest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    String mLogTag = "httpTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Thread t1 = new Thread(new Runnable() {
            public void run() {

                Log.d(mLogTag,"entered thread ");
                String httpsProxy = System.getProperty("https.proxyHost");
                String httpsPort = System.getProperty("https.proxyPort");
                Log.d(mLogTag,"entered thread proxy port = " + httpsProxy +" " + httpsPort);
                //System.setProperty("https.proxyHost", "10.0.0.0");
                //System.setProperty("https.proxyPort", "6666");
                //System.setProperty("http.proxyHost", "10.0.0.0");
                //setProperty("http.proxyPort", "6666");

                ProxySelector ps = ProxySelector.getDefault();
                Proxy proxy;

                if (ps != null) {
                    List<Proxy> proxies = ps.select(URI.create("https://www.google.com"));
                    if (proxies != null){
                        proxy = proxies.get(0);
                        URL url = null;
                        try {
                            url = new URL("https://www.google.com");
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        HttpURLConnection con  = null;
                        try {
                            con = (HttpURLConnection)url.openConnection(proxy);
                            Log.d(mLogTag,"entered thread line 44");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        con.setConnectTimeout(100000);
                        try {
                            con.connect();
                            Log.d(mLogTag,"entered thread line 51");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            int  state = con.getResponseCode();
                            Log.d(mLogTag,"return lin state = " + state);
                            Log.d(mLogTag,"return lin state = " + con.getHeaderField(10));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }});
        t1.start();


        // GETボタンとPOSTボタン取得
        Button getButton = findViewById(R.id.getButton);
        Button postButton = findViewById(R.id.postButton);

        // GETボタンがタップされた時
        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpGet getTask = new OkHttpGet();
                getTask.execute();
            }
        });

        // POSTボタンが押された時
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpPost postTask = new OkHttpPost();
                postTask.execute();
            }

        });
    }
}