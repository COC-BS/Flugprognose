package com.flycoc.flugprognose.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//Helferklasse um Bilder aus dem Internet zu laden
public class LoadContentHelper {

    public void loadImageByURL (Activity activity, String url, ImageView imgView) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder().url(url).method("GET", null).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    Bitmap bitmap;
                    InputStream inputStream = response.body().byteStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imgView.setImageBitmap(bitmap);
                        }
                    });
                }
            }
        });


    }

    public void loadTextByJSoup(Activity activity, TextView textView, String url, String webClassName) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                String webText;

                try {
                    Document doc = Jsoup.connect(url).get();
                    Log.i("Connect with page:", doc.title());

                    Elements textDiv = doc.getElementsByClass(webClassName);
                    webText = textDiv.text();

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(webText);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void loadImageByJSoup (Activity activity, ImageView imageView, String url, String webClassName) {
        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {

                Bitmap bitmap;

                try {
                    Document doc = Jsoup.connect(url).get();
                    Log.i("Connect with page:", doc.title());

                    Elements elements = doc.getElementsByClass(webClassName);


                    InputStream inputStream = (InputStream) elements.stream();
                    bitmap = BitmapFactory.decodeStream(inputStream);


                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bitmap);
                        }
                    });

                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
