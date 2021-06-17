package com.example.aws_push_sample;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;
import androidx.core.graphics.drawable.IconCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class NotificationAlert {
    private Context mContext;
    private String NOTIFICATION_CHANNEL_id = "";       //R.string.notification_channel_id
    private String NOTIFICATION_CHANNEL_Name = "";     //R.string.notification_channel_id
    private String NOTIFICATION_STYLE_Normal = "";     //R.string.NOTIFICATION_STYLE_Normal
    private String NOTIFICATION_STYLE_Reply = "";      //R.string.NOTIFICATION_STYLE_Reply
    private String NOTIFICATION_STYLE_Action = "";     //R.string.NOTIFICATION_STYLE_Action

    private static String KEY_TEXT_REPLY = "key_text_reply";
    public NotificationAlert(Context mContext) {
        this.mContext = mContext;
    }

    public void showNotification(ReceivePushMsgObject obj, Intent intent) {
        //postVolleyResponse();

        final Icon icon;
        RemoteInput remoteInput = null;
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        NOTIFICATION_CHANNEL_id = mContext.getResources().getString(R.string.notification_channel_id);
        NOTIFICATION_CHANNEL_Name = mContext.getResources().getString(R.string.notification_channel_id);

        //SET Action
        PendingIntent pendingIntent = PendingIntent.getActivity(
                mContext,
                Integer.parseInt(NOTIFICATION_CHANNEL_id),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        NOTIFICATION_STYLE_Normal = mContext.getResources().getString(R.string.NOTIFICATION_STYLE_Normal);
        NOTIFICATION_STYLE_Reply = mContext.getResources().getString(R.string.NOTIFICATION_STYLE_Reply);
        NOTIFICATION_STYLE_Action = mContext.getResources().getString(R.string.NOTIFICATION_STYLE_Action);


        Bitmap image = null;
        try {
            String picture_url = obj.getPicture_url();
            if (picture_url != null && !"".equals(picture_url)) {
                URL url = new URL(picture_url);
                image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Notification newMessageNotification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Log.e(TAG, "VERSION_CODES >= O: " + android.os.Build.VERSION_CODES.O);
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_id, NOTIFICATION_CHANNEL_Name, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Action action = null;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && NOTIFICATION_STYLE_Reply.equals(obj.getType())) {
            Log.e(TAG, "VERSION.SDK_INT >= Android 7(Build.VERSION_CODES.N): "+Build.VERSION_CODES.N);

            String replyLabel = "Enter your reply here";
            remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                    .setLabel(replyLabel)
                    .build();
            icon = Icon.createWithResource(mContext,
                    android.R.drawable.ic_dialog_info);
            action = new NotificationCompat.Action.Builder(android.R.drawable.ic_dialog_info,
                            "Reply", pendingIntent)
                            .addRemoteInput(remoteInput)
                            .build();
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_id);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && NOTIFICATION_STYLE_Reply.equals(obj.getType())) {
            newMessageNotification = builder.setContentTitle(obj.getTitle())
                    .setContentText(obj.getBody())
                    .setPriority(Notification.PRIORITY_MAX)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher))
                    .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(image).setSummaryText(obj.getBody()))
                    .addAction(action)
                    .build();
        } else {
            newMessageNotification = builder.setContentTitle(obj.getTitle())
                    .setContentText(obj.getBody())
                    .setPriority(Notification.PRIORITY_MAX)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher))
                    .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(image).setSummaryText(obj.getBody()))
                    .setAutoCancel(true)
                    .build();
        }

        //SET Reply

        notificationManager.notify(Integer.parseInt(NOTIFICATION_CHANNEL_id), newMessageNotification);
    }




    //Send Requence
    private void postVolleyResponse() {
        String url = "https://localhost:8888/";
        RequestQueue mRequestQueue = Volley.newRequestQueue(mContext);
        JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Response: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error Response: " + error.toString());
                    }
                });
        mRequestQueue.add(mJsonObjectRequest);

    }
}
