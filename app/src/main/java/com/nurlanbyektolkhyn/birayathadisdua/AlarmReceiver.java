package com.nurlanbyektolkhyn.birayathadisdua;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
  public void onReceive(Context context, Intent intent) {
    try {
      context.startService(new Intent(context, AlarmService.class));
    } catch (Exception e) {
    }
  }
}