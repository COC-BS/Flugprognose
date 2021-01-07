package com.flycoc.flugprognose.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//Helferklasse um Bilder aus dem Internet zu laden
public class LoadImageHelper {

    public void loadImage (Activity activity, String url, ImageView imgView) {

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

}
