package com.example.aws_push_sample;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.aws_push_sample.Object.PushMessageResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.json.JSONObject;

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

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                PushMessageResponse obj = new PushMessageResponse();
                if (remoteMessage.getData() != null || remoteMessage.getNotification() != null) {
                    obj = new Gson().fromJson(new JSONObject(remoteMessage.getData()).toString(), PushMessageResponse.class);
                    Log.e(TAG, "Push Message: "+ new Gson().toJson(obj));
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

    public void sendNotification(PushMessageResponse obj) {
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
}
