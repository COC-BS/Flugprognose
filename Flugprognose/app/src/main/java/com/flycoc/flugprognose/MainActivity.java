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

    ImageView imgViewSHV10m;
    ImageView imgViewSHV1500m;
    ImageView imgViewSHV2000m;
    ImageView imgViewSHV3000m;
    ImageView imgViewSHV4000m;

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
        imgViewSHV10m = findViewById(R.id.imgViewSHV10m);
        imgViewSHV1500m = findViewById(R.id.imgViewSHV1500m);
        imgViewSHV2000m = findViewById(R.id.imgViewSHV2000m);
        imgViewSHV3000m = findViewById(R.id.imgViewSHV3000m);
        imgViewSHV4000m = findViewById(R.id.imgViewSHV4000m);
        String cookieSHV = "PHPSESSID=2hmi7pckmorq0eb48t6acbh113";
        contentHelper.loadImageByURLwithCookie(this, "https://www.meteo-shv.ch/inbox/data/c2e_ch_ctrl_uv10m_024.png", imgViewSHV10m, cookieSHV);
        contentHelper.loadImageByURLwithCookie(this, "https://www.meteo-shv.ch/inbox/data/c2e_ch_ctrl_uv850_024.png", imgViewSHV1500m, cookieSHV);
        contentHelper.loadImageByURLwithCookie(this, "https://www.meteo-shv.ch/inbox/data/c2e_ch_ctrl_uv2000_024.png", imgViewSHV2000m, cookieSHV);
        contentHelper.loadImageByURLwithCookie(this, "https://www.meteo-shv.ch/inbox/data/c2e_ch_ctrl_uv3000_024.png", imgViewSHV3000m, cookieSHV);
        contentHelper.loadImageByURLwithCookie(this, "https://www.meteo-shv.ch/inbox/data/c2e_ch_ctrl_uv4000_024.png", imgViewSHV4000m, cookieSHV);
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