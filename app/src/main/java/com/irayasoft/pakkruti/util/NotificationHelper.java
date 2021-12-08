package com.irayasoft.pakkruti.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.irayasoft.pakkruti.R;
import com.irayasoft.pakkruti.view.MainActivity;

public class NotificationHelper {
    private static String CHANNEL_ID = "dogs_id";
    private static String NOTIFICATION_ID = "dogs_id";
    private static NotificationHelper INSTANCE;
    private Context context;

    public NotificationHelper(Context context) {
        this.context = context;
    }

    public static NotificationHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new
                    NotificationHelper(context);
        }

        return INSTANCE;
    }

    //methode for notification
    public void createNotification() {
        //showing action
        createNotificationChannel();

        Intent intent=new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //prnding intent
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,0);
        // icon for notification
        Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.dog_icon);

//        Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
//        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
//        intent.putExtra(Settings.EXTRA_CHANNEL_ID, myNotificationChannel.getId());
//        startActivity(intent);

        Notification notification=new NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(R.drawable.dog_icon)
                .setLargeIcon(bitmap)
                .setContentTitle("dogtittle")
                .setContentText("ghjjhkjkjkljlk  hjjhkjhkjjk jhkjhkj")
                .setStyle( new NotificationCompat.BigPictureStyle()
                .bigLargeIcon(bitmap)
                .bigLargeIcon(null))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
    }

    public void createNotificationChannel() {

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String Name = CHANNEL_ID;
            String description = "Dog retrieved notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, Name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }
}
