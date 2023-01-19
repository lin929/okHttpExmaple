package com.example.httptest;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpGet extends AsyncTask<String,String,String> {
    @Override
    protected String doInBackground(String... strings) {

        // url
        String url = "https://ipaddress.my/";
        // レスポンスデータ
        String strData = "";

        Proxy proxyTest = new Proxy(Proxy.Type.HTTP,new InetSocketAddress("99.99.99.99", 666));

        // OkHttpのインスタンス生成
        OkHttpClient.Builder builder = new OkHttpClient.Builder().proxy(proxyTest);
        OkHttpClient client = builder.build();

        // urlを含めてリクエストをビルド
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try {
            // リクエスト実行
            Response response = client.newCall(request).execute();
            // レスポンスのbodyからデータ取得
            strData = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strData;
    }

    @Override
    protected void onPostExecute(String str) {
        Log.d("Debug","this is onPostExecute");
        Log.d("Debug",str);
    }
}