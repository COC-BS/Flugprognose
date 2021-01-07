package com.flycoc.flugprognose;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.flycoc.flugprognose.utils.LoadImageHelper;
import com.google.android.material.slider.Slider;
import com.ortiz.touchview.TouchImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LoadImageHelper imageHelper = new LoadImageHelper();

    TouchImageView imgViewVerlässlichkeit;
    ImageView imgViewFöhn;

    TextView textAllgLageDatum;
    TouchImageView imgViewAllgLage;

    Slider sliderVerlässlichkeit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgViewAllgLage = findViewById(R.id.imgViewAllgLage);

        imgViewVerlässlichkeit = findViewById(R.id.imgViewVerlässlichkeit);
        imageHelper.loadImage(this,getString(R.string.urlVerlässlichkeit),imgViewVerlässlichkeit);

        imgViewFöhn = findViewById(R.id.imgViewFöhn);
        imageHelper.loadImage(this,getString(R.string.urlFöhndiagramm),imgViewFöhn);

        textAllgLageDatum = findViewById(R.id.textAllgLageDatum);
        String dateText = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        textAllgLageDatum.setText(dateText);
        imgViewAllgLage = (TouchImageView) findViewById(R.id.imgViewAllgLage);
        String date = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());
        String urlAllgLage = "https://media.meteonews.net/isobaren/EURNAt_700x450_c1/isobaren_"+date+"_1200.png";
        imageHelper.loadImage(this, urlAllgLage, imgViewAllgLage);



        sliderVerlässlichkeit = findViewById(R.id.sliderVerlässlichkeit);

        sliderVerlässlichkeit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    String hours = String.valueOf((int)sliderVerlässlichkeit.getValue());
                    String url = "https://www.wetterzentrale.de/maps/GFSSPAGEU18_" + hours + "_1.png";
                    Log.i("+++++++++++++","Load image from URL: "+url);

                    reloadImage(url,imgViewVerlässlichkeit);
                }
                return false;
            }
        });
    }

    private void reloadImage(String url, ImageView imgView) {
        imageHelper.loadImage(this,url,imgView);
    }

}