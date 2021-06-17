package com.example.aws_push_sample;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        sendTokenToTheAppServer(token);
    }

    private void sendTokenToTheAppServer(String token) {
        Log.e(TAG, "MyFirebaseMessagingService sendTokenToTheAppServer(): " + token);
        //CALL BACKEND register
    }



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.e(TAG, "RemoteMessage: " + remoteMessage);
        Log.e(TAG, "Data: " + remoteMessage.getData());
        Log.e(TAG, "Notification: " + remoteMessage.getNotification());
        for (String key:remoteMessage.getData().keySet()) {
            Log.e(TAG, "KEY : " + key);
            Log.e(TAG, "VALUE : " + remoteMessage.getData().get(key));
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                Log.e(TAG, "scheduleJob: " + "Check if data needs to be processed by long running job");

                ReceivePushMsgObject obj = new ReceivePushMsgObject();
                if (remoteMessage.getData() != null || remoteMessage.getNotification() != null) {
                    obj = new Gson().fromJson(new JSONObject(remoteMessage.getData()).toString(), ReceivePushMsgObject.class);
                    sendNotification(obj);
                } else {
                    Log.e(TAG, "onMessageReceived: " + "Not valid data");
                }
            } else {
                // Handle message within 10 seconds
                Log.e(TAG, "scheduleJob: " + "within 10 seconds");
                //handleNow();
            }

        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    public void sendNotification(ReceivePushMsgObject obj) {
        NotificationAlert notificationAlert  = new NotificationAlert(getApplicationContext());
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        notificationAlert.showNotification(obj, intent);
    }

    public static void retrieveDeviceToken(final Activity activity) {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(activity, new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    Log.e("onComplete","Fails Task");
                    return;
                }
                if(task.getResult() == null)
                    return;
                // Get new Instance ID token
                String token = task.getResult().getToken();
                DeviceStorage.storeToken(token, activity);
                // Log and toast
                Log.e("retrieveDeviceToken","Get Token Success.");
                Log.e("Token: ", token);
            }
        });
    }

    //Subscribe
    public static void subscribe(final Activity activity, final String topic) {
        Log.e("Start","Subscribe MPF Topic");


        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e("onComplete","Fails Task");
                            return;
                        }
                        if(task.getResult() == null)
                            return;
                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        DeviceStorage.storeToken(token, activity);
                    }
                });

    }

    public static void unSubscribe(final Activity activity, final String topic) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic).addOnCompleteListener(activity, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.e("onComplete","N Subscription Task");
                String msg = activity.getResources().getString(R.string.msg_topic_cancelling_subscription_success);
                if (!task.isSuccessful()) {
                    msg = activity.getResources().getString(R.string.msg_topic_cancelling_subscription_fail);
                }
                FirebaseMessaging.getInstance().unsubscribeFromTopic(topic);
                Log.e("Action S/C", topic + " " + msg);
                Toast.makeText(activity.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
