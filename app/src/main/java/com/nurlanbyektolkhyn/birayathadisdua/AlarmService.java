package com.nurlanbyektolkhyn.birayathadisdua;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import androidx.core.app.NotificationCompat.Builder;
import com.google.android.gms.plus.PlusShare;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.TimeZone;
import org.w3c.dom.Element;

public class AlarmService extends IntentService {
    private static final int notificationID  = 1;
    String Key_Ayet_content = PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE;;
    String Key_Ayet_source = "source";
    String Key_Hadis_content =  "hadis";
    String Key_Hadis_source = "kaynak";
    String Key_Dua_content =  "pray";
    String Key_Dua_source = "cite";
    String Key_id = "id";
    String Key_item = "dict";
    int  bugun = Calendar.getInstance(TimeZone.getDefault()).get(Calendar.DAY_OF_YEAR);
    Editor editorum;
    Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;
    SharedPreferences veriler;

    public AlarmService() {
        super("AlarmService");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public void onHandleIntent(Intent intent) {
        Context context = getApplicationContext();
        String titlee = "";
        String content = "";
        try {
            this.veriler = getSharedPreferences("veriler",0);
            this.editorum = this.veriler.edit();
            int bugun_ne = this.veriler.getInt("bugun_ne", 0);
            if (bugun_ne == 0) {
                titlee = "Бір Аят";
                content = gunAyet();
                this.editorum.putInt("bugun_ne", 1);
                this.editorum.commit();
            } else if (bugun_ne == 1) {
                titlee = "Бір Хадис";
                content = gunHadis();
                this.editorum.putInt("bugun_ne", 2);
                this.editorum.commit();
            } else if (bugun_ne == 2) {
                titlee = "Бір Дұға";
                content = gunDua();
                this.editorum.putInt("bugun_ne", 0);
                this.editorum.commit();
            }
        } catch (Exception e) {
        }
       notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        pendingIntent = PendingIntent.getActivity(context, 0,
                new Intent(this, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT);
        Builder notification = new Builder(this);
        notification.setContentIntent(this.pendingIntent).setContentTitle(titlee).setContentText(content).setSmallIcon(R.drawable.ic_bildirim);
        Notification note = notification.build();
        note.defaults |= 2;
        note.defaults |= 1;
        note.flags |= 16;
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, note);
    }

    public String gunAyet() {
        try {
            XMLParser parser = new XMLParser();
            return parser.getValue((Element) parser.getDomElement(loadJSONFromAsset("a.xml")).getElementsByTagName(this.Key_item).item(this.bugun), Key_Ayet_content);
        } catch (Exception e) {
            return "";
        }
    }

    public String gunHadis() {
        try {
            XMLParser parser = new XMLParser();
            return parser.getValue((Element) parser.getDomElement(loadJSONFromAsset("hs.xml")).getElementsByTagName(this.Key_item).item(this.bugun), this.Key_Hadis_content);
        } catch (Exception e) {
            return "";
        }       
    }

    public String gunDua() {
        try {
            XMLParser parser = new XMLParser();
            return parser.getValue((Element) parser.getDomElement(loadJSONFromAsset("da.xml")).getElementsByTagName(this.Key_item).item(this.bugun), this.Key_Dua_content);
        } catch (Exception e) {
            return "";
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
