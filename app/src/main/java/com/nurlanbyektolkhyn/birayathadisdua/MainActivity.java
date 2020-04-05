package com.nurlanbyektolkhyn.birayathadisdua;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {


    LinearLayout lmbtn,img;
    View getHome,getKun;
    AdapterView ad_home;

    ViewStub vs_home,vs_kun;
    ImageView logo;

    Button mbtn,back_btn,share_btn;
    SharedPreferences.Editor editorm;
    SharedPreferences veriler;

    Animation animeh,anim_close,anim_2close,anim_open,anim_2open;


    int pageNumber=0;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aktivi();
        Home();
        getKun=vs_kun.inflate();



    }

    public void aktivi(){
        lmbtn=findViewById(R.id.lmbtn);
        vs_home=findViewById(R.id.vs_home);
        vs_kun=findViewById(R.id.vs_kun);
        mbtn=findViewById(R.id.mbtn);
        back_btn=findViewById(R.id.back_btn);
        share_btn=findViewById(R.id.share_btn);
        logo=findViewById(R.id.logo);
        img=findViewById(R.id.img);

        animeh= AnimationUtils.loadAnimation(this, R.anim.animeh);
        anim_2close= AnimationUtils.loadAnimation(this, R.anim.anim_2close);
        anim_2open= AnimationUtils.loadAnimation(this, R.anim.anim_2open);
        anim_close= AnimationUtils.loadAnimation(this, R.anim.anim_close);
        anim_open= AnimationUtils.loadAnimation(this, R.anim.anim_open);


    }

    public void Home() {
      getHomeKurulum();

    }

    public void Kun(){
        getHome.startAnimation(anim_close);
        getHome.setVisibility(View.GONE);
        Kunkurulum();
    }

    public void Kunkurulum(){
        pageNumber=2;
        back_btn=findViewById(R.id.back_btn);
        share_btn=findViewById(R.id.share_btn);

        img=findViewById(R.id.img);

        getKun.setVisibility(View.VISIBLE);

       // fontDegistir(this.getKun);


        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageNumber=1;
                getKun.startAnimation(anim_close);
                getKun.setVisibility(View.GONE);
                getHome.startAnimation(anim_open);
                getHome.setVisibility(View.VISIBLE);


            }
        });

    }

    private void getHomeKurulum() {
        pageNumber=1;
        getHome = vs_home.inflate();



        lmbtn=findViewById(R.id.lmbtn);
        vs_home=findViewById(R.id.vs_home);
        vs_kun=findViewById(R.id.vs_kun);
        mbtn=findViewById(R.id.mbtn);
        logo=findViewById(R.id.logo);
        img=findViewById(R.id.img);
        animeh= AnimationUtils.loadAnimation(this, R.anim.animeh);




        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity.this.Kun();



            }
        });

    }


    public void onBackPressed() {
        if (pageNumber == 2) {
            pageNumber = 1;
            getKun.startAnimation(this.anim_close);
            getKun.setVisibility(View.GONE);
            getHome.startAnimation(this.anim_open);
            getHome.setVisibility(View.VISIBLE);
        } else{

        }
    }
}
