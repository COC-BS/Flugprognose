package com.flycoc.flugprognose;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.flycoc.flugprognose.utils.LoadContentHelper;
import com.google.android.material.slider.Slider;
import com.ortiz.touchview.TouchImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LoadContentHelper contentHelper = new LoadContentHelper();

    ImageView imgViewFöhn;

    TextView textAllgLageMeteoSchw;
    TouchImageView imgViewAllgLage;

    TouchImageView imgViewVerlässlichkeit;
    Slider sliderVerlässlichkeit;

    TextView textWeterberichtMeteoSchw;

    ImageView imgViewSHV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Verlässlichkeit
        loadVerlässlichkeit();

        //Föhndiagramm
        imgViewFöhn = findViewById(R.id.imgViewFöhn);
        contentHelper.loadImageByURL(this,getString(R.string.urlFöhndiagramm),imgViewFöhn);

        //Allgemeine Lage
        loadAllgemeineLage();

        //Wetterbericht Textform
        textWeterberichtMeteoSchw = findViewById(R.id.textWeterberichtMeteoSchw);
        contentHelper.loadHTMLformatedTextByJSoup(this, textWeterberichtMeteoSchw, "https://www.meteoschweiz.admin.ch/home.html?tab=report", "textFCK", "<h3>Heute,");

        //SHV Daten, Höhenwind
        imgViewSHV = findViewById(R.id.imgViewSHV);
        contentHelper.loadImageByURLwithCookie(this, "https://www.meteo-shv.ch/inbox/data/c2e_ch_ctrl_uv2000_033.png", imgViewSHV);
    }

    private void loadVerlässlichkeit () {
        //Verlässlichkeit
        imgViewVerlässlichkeit = findViewById(R.id.imgViewVerlässlichkeit);
        contentHelper.loadImageByURL(this,getString(R.string.urlVerlässlichkeit),imgViewVerlässlichkeit);

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

    private void loadAllgemeineLage () {

        //Text Allgemeine Lage Meteo Schweiz
        textAllgLageMeteoSchw = findViewById(R.id.textAllgLageMeteoSchw);
        contentHelper.loadHTMLformatedTextByJSoup(this, textAllgLageMeteoSchw, getString(R.string.urlAllgLageMeteoSchw),"textFCK");

        String dateText = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        imgViewAllgLage = findViewById(R.id.imgViewAllgLage);

        String date = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());
        String urlAllgLage = "https://media.meteonews.net/isobaren/EURNAt_700x450_c1/isobaren_"+date+"_1200.png";

        contentHelper.loadImageByURL(this, urlAllgLage, imgViewAllgLage);


        //contentHelper.loadImageByJSoup(this,imgViewAllgLage,getString(R.string.urlAllgLageMeteoSchw), "blowup");
    }

    private void reloadImage(String url, ImageView imgView) {
        contentHelper.loadImageByURL(this,url,imgView);
    }

}