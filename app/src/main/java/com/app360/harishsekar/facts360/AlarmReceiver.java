package com.app360.harishsekar.facts360;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

/**
 * Created by harishsekar on 10/21/17.
 */

public class  AlarmReceiver extends BroadcastReceiver {

    FactsStore factsStore;
    @Override
    public void onReceive(Context context, Intent intent) {

        factsStore = new FactsStore(context);
        Intent mainActivirt_intent = new Intent(context ,MainActivity.class);

//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//        stackBuilder.addParentStack(MainActivity.class);
//        stackBuilder.addNextIntent(notificatoin_intent);

//        PendingIntent pendingIntent = stackBuilder.getPendingIntent(100,PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,100,mainActivirt_intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);


        notificationBuilder.setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentTitle("Daily Facts: 360")
                .setAutoCancel(true)
//                .setContentText(factsStore.getFatcs())
                .setSmallIcon(R.mipmap.bookshelf_64);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(100,notificationBuilder.build());
    }
}
