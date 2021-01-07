package com.flycoc.flugprognose;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.flycoc.flugprognose.utils.LoadImageHelper;
import com.google.android.material.slider.Slider;

public class MainActivity extends AppCompatActivity {

    LoadImageHelper imageHelper = new LoadImageHelper();

    ImageView imgViewVerlässlichkeit;
    ImageView imgViewFöhn;

    Slider sliderVerlässlichkeit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgViewVerlässlichkeit = (ImageView) findViewById(R.id.imgViewVerlässlichkeit);
        imgViewFöhn = (ImageView) findViewById(R.id.imgViewFöhn);

        imageHelper.loadImage(this,getString(R.string.urlVerlässlichkeit),imgViewVerlässlichkeit);
        imageHelper.loadImage(this,getString(R.string.urlFöhndiagramm),imgViewFöhn);

        sliderVerlässlichkeit = (Slider) findViewById(R.id.sliderVerlässlichkeit);

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