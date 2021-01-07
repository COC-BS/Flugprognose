package com.flycoc.flugprognose;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.flycoc.flugprognose.utils.LoadImageHelper;

public class MainActivity extends AppCompatActivity {

    LoadImageHelper imageHelper = new LoadImageHelper();

    ImageView imgViewVerlässlichkeit;
    ImageView imgViewFöhn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgViewVerlässlichkeit = (ImageView) findViewById(R.id.imgViewVerlässlichkeit);
        imgViewFöhn = (ImageView) findViewById(R.id.imgViewFöhn);

        imageHelper.loadImage(this,getString(R.string.urlVerlässlichkeit),imgViewVerlässlichkeit);
        imageHelper.loadImage(this,getString(R.string.urlFöhndiagramm),imgViewFöhn);


    }

}