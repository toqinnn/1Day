package com.nurlanbyektolkhyn.birayathadisdua;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.TimePicker;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import android.graphics.Typeface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.io.InputStream;
import java.io.IOException;
import android.widget.TextView;
import org.w3c.dom.Element;
import android.widget.Toast;
import android.net.Uri;
import android.content.ActivityNotFoundException;

public class MainActivity extends AppCompatActivity {
    LinearLayout lmbtn, ln_kun,ln_settings, lmbtn2,ln_favori,lmbtn3;
    View getHome, getKun, getFavori,getSettings;
    ViewStub vs_home, vs_kun, vs_favori,vs_settings;
    ImageView logo, back_btn,back_btn2,btn_settings_back;
    Button mbtn,mbtn3, btn_ayat_share,btn_hadis_share,btn_dua_share,share_btn, mbtn2,btn_saat_change,btn_habarlandru_kuii;
    SharedPreferences veriler;
    Animation anim_close,anim_open;
    int pageNumber = 0;
    int bugun = 1;
    AlarmManager alarmManager;
    public InterstitialAd gecisReklam;
    private AdView mAdView;
    AdView ad_anasayfa,ad_ayarlar,adView,ad_kun;
    String Key_Ayet_content =  "title";
    String Key_Ayet_source = "source";
    String Key_Hadis_content =  "hadis";
    String Key_Hadis_source = "kaynak";
    String Key_Dua_content =  "pray";
    String Key_Dua_source = "cite";
    String Key_id = "id";
    String Key_item = "dict";
    String[] dizi_ayet = new String[3];
    String[] dizi_dua = new String[3];
    String[] dizi_hadis = new String[3];
    TextView tv_ayet,tv_hadis,tv_dua;
    TextView tv_ayet_source, tv_hadis_source,tv_dua_source, et_habarlandru_uahyty;;
    boolean habarlandru_kuii = true;
    boolean ic_durumu = true;
    boolean alarm_cancel = false;
    int habarlandru_minut = 0;
    int habarlandru_sagat = 11;
    Intent alarmIntent;
    PendingIntent pendingIntent;
    Editor editorum;
    private void requestNewInterstitial() {

        this.gecisReklam.loadAd(new Builder().build());
    }

    private void fontChange(View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    fontChange(vg.getChildAt(i));
                }
            } else if (v instanceof TextView) {
                ((TextView) v).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/2034.ttf"));
            }
        } catch (Exception e) {
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aktivi();
        View decorView3 = getWindow().getDecorView();
        bugun = Calendar.getInstance(TimeZone.getDefault()).get(Calendar.DAY_OF_YEAR);
        fontChange(decorView3);
        this.veriler = getSharedPreferences("veriler",MODE_PRIVATE);
        this.editorum = this.veriler.edit();
        this.gecisReklam = new InterstitialAd(this);
        this.gecisReklam.setAdUnitId("ca-app-pub-8111598628620916/1158359499");
        this.gecisReklam.setAdListener(new AdListener() {
            public void onAdLoaded() {
                try {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            if (MainActivity.this.gecisReklam.isLoaded()) {
                                MainActivity.this.gecisReklam.show();
                            }
                        }
                    }, 10000);
                } catch (Exception e) {
                }
            }

            public void onAdFailedToLoad(int errorCode) {
            }

            public void onAdClosed() {
            }
        });

        requestNewInterstitial();
        this.veriler = getSharedPreferences("veriler", 0);
        this.editorum = this.veriler.edit();
        if (this.veriler.getInt("open_kod", 0) == 0) {
            setAlarm(Calendar.HOUR_OF_DAY, Calendar.MINUTE);
            this.editorum.putInt("open_kod", 1);
            this.editorum.commit();
        }
        getKun=vs_kun.inflate();
        getFavori=vs_favori.inflate();
        getSettings=vs_settings.inflate();
        Home();
    }

    public void aktivi() {
        lmbtn = findViewById(R.id.lmbtn);
        lmbtn2 = findViewById(R.id.lmbtn2);
        lmbtn3=findViewById(R.id.lmbtn3);
        vs_home = findViewById(R.id.vs_home);
        vs_kun = findViewById(R.id.vs_kun);
        vs_favori = findViewById(R.id.vs_favori);
        vs_settings=findViewById(R.id.vs_settings);
        mbtn = findViewById(R.id.mbtn);
        mbtn2 = findViewById(R.id.mbtn2);
        mbtn3=findViewById(R.id.mbtn3);
        back_btn = findViewById(R.id.back_btn);
        back_btn2 = findViewById(R.id.back_btn2);
        logo = findViewById(R.id.logo);
        ln_kun = findViewById(R.id.ln_kun);
        ln_favori=findViewById(R.id.ln_favori);
        anim_close = AnimationUtils.loadAnimation(this, R.anim.anim_close);
        anim_open = AnimationUtils.loadAnimation(this, R.anim.anim_open);
    }

    public void Home() {
        getHomeOrnatu();
    }
       public void Kun() {
        getHome.startAnimation(anim_close);
        getHome.setVisibility(View.GONE);
        KunOrnatu();
    }

    public void FavoriPage() {
        getHome.startAnimation(anim_close);
        getHome.setVisibility(View.GONE);
        FavoriOrnatu();
    }
    public void SettingsPage() {
        getHome.startAnimation(anim_close);
        getHome.setVisibility(View.GONE);
        SettingsOrnatu();
    }


    private void getHomeOrnatu() {
        pageNumber = 1;
        getHome = vs_home.inflate();
        fontChange(getHome);
        ad_anasayfa=(AdView)findViewById(R.id.ad_anasayfa);
        ad_anasayfa.loadAd(new Builder().build());
        tv_ayet=findViewById(R.id.tv_ayet);
        tv_ayet_source =findViewById(R.id.tv_ayet_source);
        tv_hadis=findViewById(R.id.tv_hadis);
        tv_hadis_source =findViewById(R.id.tv_hadis_source);
        lmbtn = findViewById(R.id.lmbtn);
        lmbtn2 = findViewById(R.id.lmbtn2);
        lmbtn3 = findViewById(R.id.lmbtn3);
        vs_home = findViewById(R.id.vs_home);
        vs_kun = findViewById(R.id.vs_kun);
        mbtn = findViewById(R.id.mbtn);
        mbtn2=findViewById(R.id.mbtn2);
        mbtn3=findViewById(R.id.mbtn3);
        logo = findViewById(R.id.logo);
        ln_kun = findViewById(R.id.ln_kun);
        ln_favori=findViewById(R.id.ln_favori);
        logo.animate().translationX(10).setDuration(2000).setStartDelay(800);
        lmbtn.animate().translationX(0).setDuration(2000).setStartDelay(800);
        lmbtn2.animate().translationX(0).setDuration(2000).setStartDelay(800);
        lmbtn3.animate().translationX(0).setDuration(2000).setStartDelay(800);
        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.Kun();
            }
        });
        mbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.FavoriPage();
            }
        });
        mbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.SettingsPage();
            }
        });
    }

    private void FavoriOrnatu() {
        back_btn2 = findViewById(R.id.back_btn2);
        ln_favori=findViewById(R.id.ln_favori);
        lmbtn2 = findViewById(R.id.lmbtn2);
        Button btn_hakkinda_site = findViewById(R.id.btn_hakkinda_site);
        Button btn_hakkinda_digeruy = findViewById(R.id.btn_hakkinda_digeruy);
        Button btn_hakkinda_email = findViewById(R.id.btn_hakkinda_email);
        getFavori.setVisibility(View.VISIBLE);
        ln_favori.animate().translationX(0).setDuration(1500).setStartDelay(0);
        fontChange(this.getFavori);
        back_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageNumber = 1;
                getFavori.startAnimation(anim_close);
                getFavori.setVisibility(View.GONE);
                getHome.startAnimation(anim_open);
                getHome.setVisibility(View.VISIBLE);
            }
        });
          btn_hakkinda_email.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.startActivity(Intent.createChooser(new Intent("android.intent.action.SENDTO", Uri.fromParts("mailto", "tolkhynnn@gmail.com", null)), null));
            }
        });
        btn_hakkinda_site.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myWebLink = new Intent("android.intent.action.VIEW");
                myWebLink.setData(Uri.parse("http://www.tolkhynnn.kz"));
                MainActivity.this.startActivity(myWebLink);
            }
        });
        btn_hakkinda_digeruy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://developer?id=Tolkhyn+Nurlanbyek")));
                } catch (ActivityNotFoundException e) {
                    MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/developer?id=Tolkhyn+Nurlanbyek")));
                }
            }
        });
    }
    public void KunOrnatu() {
        pageNumber=2;
    try {pageNumber = 2;
        back_btn = findViewById(R.id.back_btn);
        ln_kun = findViewById(R.id.ln_kun);
        tv_ayet =findViewById(R.id.tv_ayet);
        tv_ayet_source =findViewById(R.id.tv_ayet_source);
        tv_hadis =findViewById(R.id.tv_hadis);
        tv_hadis_source =findViewById(R.id.tv_hadis_source);
        tv_dua =findViewById(R.id.tv_dua);
        tv_dua_source =findViewById(R.id.tv_dua_source);
        btn_ayat_share=findViewById(R.id.btn_ayat_share);
        btn_hadis_share=findViewById(R.id.btn_hadis_share);
        btn_dua_share=findViewById(R.id.btn_dua_share);
        ln_kun.animate().translationX(0).setDuration(1500).setStartDelay(0);
        dayAyat();
        dayHadis();
        dayDua();
        getKun.setVisibility(View.VISIBLE);
        fontChange(this.getKun);
        btn_ayat_share.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent sharingIntent = new Intent("android.intent.action.SEND");
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra("android.intent.extra.TEXT", "Бір Аят\n" + MainActivity.this.dizi_ayet[1] + "\n" + MainActivity.this.dizi_ayet[2] + "\nЕгер сіз де жүктегіңіз келсе Google Play: https://goo.gl/LXQkol ");
                MainActivity.this.startActivity(Intent.createChooser(sharingIntent, " Бір Аят бөлісу"));

            }
        });
        btn_hadis_share.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent sharingIntent = new Intent("android.intent.action.SEND");
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra("android.intent.extra.TEXT", "Бір Хадис\n" + MainActivity.this.dizi_ayet[1] + "\n" + MainActivity.this.dizi_ayet[2] + "\nЕгер сіз де жүктегіңіз келсе Google Play: https://goo.gl/LXQkol ");
                MainActivity.this.startActivity(Intent.createChooser(sharingIntent, "Бір Хадис бөлісу"));
            }
        });
        btn_dua_share.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent sharingIntent = new Intent("android.intent.action.SEND");
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra("android.intent.extra.TEXT", "Бір Дұға\n" + MainActivity.this.dizi_ayet[1] + "\n" + MainActivity.this.dizi_ayet[2] + "\nЕгер сіз де жүктегіңіз келсе Google Play: https://goo.gl/LXQkol ");
                MainActivity.this.startActivity(Intent.createChooser(sharingIntent, "Бір Дұға бөлісу"));
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageNumber = 1;
                getKun.startAnimation(anim_close);
                getKun.setVisibility(View.GONE);
                getHome.startAnimation(anim_open);
                getHome.setVisibility(View.VISIBLE);
            }
        });

    } catch (Exception e) {
                Toast toast = Toast.makeText(this, "Жабыб қайта ашыңыз", Toast.LENGTH_SHORT);
                View view = toast.getView();
                view.setBackgroundResource(R.color.common_google_signin_btn_text_dark);
                toast.setView(view);
                requestNewInterstitial();
                toast.show();
       }

    }

    public void SettingsOrnatu() {
        pageNumber = 3;
        fontChange(this.getSettings);
        getSettings.setVisibility(View.VISIBLE);
        btn_settings_back=findViewById(R.id.btn_settings_back);
        btn_saat_change=findViewById(R.id.btn_saat_change);
        ln_settings=findViewById(R.id.ln_settings);
        mAdView = findViewById(R.id.adview);
        ln_settings.animate().translationX(0).setDuration(1500).setStartDelay(0);
        mAdView.loadAd(new Builder().build());
        habarlandru_sagat = this.veriler.getInt("habarlandru_sagat", 11);
        habarlandru_minut = this.veriler.getInt("habarlandru_minut", 0);
        et_habarlandru_uahyty =findViewById(R.id.et_habarlandru_uahyty);
        if (this.habarlandru_sagat < 10 && this.habarlandru_minut < 10) {
            this.et_habarlandru_uahyty.setText("0" + this.habarlandru_sagat + " : 0" + this.habarlandru_minut);
        } else if (this.habarlandru_sagat < 10 && this.habarlandru_minut > 9) {
            this.et_habarlandru_uahyty.setText("0" + this.habarlandru_sagat + " : " + this.habarlandru_minut);
        } else if (this.habarlandru_sagat <= 9 || this.habarlandru_minut >= 10) {
            this.et_habarlandru_uahyty.setText(this.habarlandru_sagat + " : " + this.habarlandru_minut);
        } else {
            this.et_habarlandru_uahyty.setText(this.habarlandru_sagat + " : 0" + this.habarlandru_minut);
        }
        btn_settings_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getSettings.startAnimation(anim_close);
                getSettings.setVisibility(View.GONE);
                getHome.startAnimation(anim_open);
                getHome.setVisibility(View.VISIBLE);
            }
        });
        habarlandru_kuii = veriler.getBoolean("habarlandru_kuii", true);
        btn_habarlandru_kuii = (Button) findViewById(R.id.btn_habarlandru_kuii);
        if (habarlandru_kuii) {
            btn_habarlandru_kuii.setText("Хабарландыру қосулы");
            btn_habarlandru_kuii.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.habar_acik, 0);
        } else {
            btn_habarlandru_kuii.setText("Хабарландыру өшірулі");
            btn_habarlandru_kuii.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.habar_kapali,  0);
        }
        ic_durumu = habarlandru_kuii;
        btn_habarlandru_kuii.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (MainActivity.this.ic_durumu) {
                    MainActivity.this.btn_habarlandru_kuii.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.habar_kapali, 0);
                    MainActivity.this.btn_habarlandru_kuii.setText("Хабарландыру өшірулі");
                    MainActivity.this.ic_durumu = false;
                    try {
                        MainActivity.this.alarmManager.cancel(MainActivity.this.pendingIntent);
                    } catch (Exception e) {
                    }
                } else {
                    MainActivity.this.btn_habarlandru_kuii.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.habar_acik, 0);
                    MainActivity.this.btn_habarlandru_kuii.setText("Хабарландыру қосулы");
                    MainActivity.this.ic_durumu = true;
                    try {
                        MainActivity.this.setAlarm(MainActivity.this.veriler.getInt("habarlandru_sagat", 11), MainActivity.this.veriler.getInt("habarlandru_sagat", 0));
                    } catch (Exception e2) {
                    }
                }
                MainActivity.this.editorum.putBoolean("habarlandru_kuii", MainActivity.this.ic_durumu);
                MainActivity.this.editorum.commit();
            }
        });
        btn_saat_change.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.alarmKur();
            }
        });
        ((Button) findViewById(R.id.btn_settings_share)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent sharingIntent = new Intent("android.intent.action.SEND");
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra("android.intent.extra.TEXT", "1 Аят Хадис Дұға мобилді қосымшасы Google Play:(https://goo.gl/LXQkol)");
                MainActivity.this.startActivity(Intent.createChooser(sharingIntent, "Осыны пайдаланыңыз."));
            }
        });
    }

    public void setAlarm(int saat, int dakika) {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmIntent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 1, this.alarmIntent, 0);
        Calendar alarmStartTime = Calendar.getInstance();
        alarmStartTime.set(Calendar.HOUR_OF_DAY, saat);
        alarmStartTime.set(Calendar.MINUTE, dakika);
        alarmStartTime.set(Calendar.SECOND, 0);
        alarmStartTime.set(Calendar.MILLISECOND, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), this.pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), TimeUnit.HOURS.toMillis(24), this.pendingIntent);
        try {
            this.btn_habarlandru_kuii.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.habar_acik, 0);
            this.btn_habarlandru_kuii.setText("Хабарландыру қосулы");
            this.ic_durumu = true;
            this.editorum.putBoolean("habarlandru_kuii", this.ic_durumu);
            this.editorum.commit();
        } catch (Exception e) {
        }
        Toast toast = Toast.makeText(this, "Уақытты осы сағатға тандадыңыз: " + saat + ":" + dakika,Toast.LENGTH_SHORT);

        toast.show();
    }

    public void alarmKur() {
        this.alarm_cancel = false;
        Calendar c = Calendar.getInstance();
        TimePickerDialog tpd = new TimePickerDialog(this, R.style.DialogTheme, new OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (!MainActivity.this.alarm_cancel) {
                    MainActivity.this.editorum.putInt("habarlandru_sagat", hourOfDay);
                    MainActivity.this.editorum.putInt("habarlandru_minut", minute);
                    MainActivity.this.editorum.commit();
                    MainActivity.this.setAlarm(hourOfDay, minute);
                    if (hourOfDay < 10 && minute < 10) {
                        MainActivity.this.et_habarlandru_uahyty.setText("0" + hourOfDay + " : 0" + minute);
                    } else if (hourOfDay < 10 && minute > 9) {
                        MainActivity.this.et_habarlandru_uahyty.setText("0" + hourOfDay + " : " + minute);
                    } else if (hourOfDay <= 9 || minute >= 10) {
                        MainActivity.this.et_habarlandru_uahyty.setText(new StringBuilder(String.valueOf(hourOfDay)).append(" : ").append(minute).toString());
                    } else {
                        MainActivity.this.et_habarlandru_uahyty.setText(new StringBuilder(String.valueOf(hourOfDay)).append(" : 0").append(minute).toString());
                    }
                }
            }
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
        tpd.setTitle("Уақытты тандаңыз");
        tpd.setButton(-1, "Қою", tpd);
        tpd.setButton(-2, "Артқа", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == -2) {
                    MainActivity.this.alarm_cancel = true;
                }
            }
        });
        tpd.show();
    }

    private void dayAyat() {
        try{
            XMLParser parser = new XMLParser();
            Element e = (Element) parser.getDomElement(loadJSONFromAsset("a.xml")).getElementsByTagName(Key_item).item(bugun);
            dizi_ayet[0] = parser.getValue(e,Key_id);
            dizi_ayet[1] = parser.getValue(e,Key_Ayet_content);
            dizi_ayet[2] = parser.getValue(e,Key_Ayet_source);
            tv_ayet.setText(dizi_ayet[1]);
            tv_ayet_source.setText(dizi_ayet[2]);
        }catch (Exception e2){

        }
    }
    private void dayHadis() {
        try{
            XMLParser parser = new XMLParser();
            Element e = (Element) parser.getDomElement(loadJSONFromAsset("hs.xml")).getElementsByTagName(this.Key_item).item(this.bugun);
            this.dizi_hadis[0] = parser.getValue(e, this.Key_id);
            this.dizi_hadis[1] = parser.getValue(e, this.Key_Hadis_content);
            this.dizi_hadis[2] = parser.getValue(e, this.Key_Hadis_source);
            this.tv_hadis.setText(dizi_hadis[1]);
            this.tv_hadis_source.setText(dizi_hadis[2]);
        }catch (Exception e2){

        }
    }

    private void dayDua() {
        try{
            XMLParser parser = new XMLParser();
            Element e = (Element) parser.getDomElement(loadJSONFromAsset("da.xml")).getElementsByTagName(this.Key_item).item(this.bugun);
            this.dizi_dua[0] = parser.getValue(e, this.Key_id);
            this.dizi_dua[1] = parser.getValue(e, this.Key_Dua_content);
            this.dizi_dua[2] = parser.getValue(e, this.Key_Dua_source);
            this.tv_dua.setText(this.dizi_dua[1]);
            this.tv_dua_source.setText(this.dizi_dua[2]);
        }catch (Exception e2){

        }
    }

    public String loadJSONFromAsset(String filename) {
        try {
            InputStream is = getAssets().open(filename);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            return json;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
