package com.example.aws_push_sample;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreatePlatformEndpointRequest;
import com.amazonaws.services.sns.model.CreatePlatformEndpointResult;
import com.amazonaws.services.sns.model.ListSubscriptionsByTopicRequest;
import com.amazonaws.services.sns.model.ListSubscriptionsByTopicResult;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.amazonaws.services.sns.model.Subscription;
import com.amazonaws.services.sns.model.UnsubscribeRequest;

import static android.content.ContentValues.TAG;

public class AWSMessagingService {
    private static String access_id = "AKIAIGRBVOUVFSDOQIYA";
    private static String secret_key ="fmIHxy7m+1mk+EoujY4B1MXABWnrYV+ihv09zrkB";
    private static String appArn = "arn:aws:sns:ap-northeast-1:307834178100:app/GCM/AWS_Push_Sample";
    private static String topicArn = "arn:aws:sns:ap-northeast-1:307834178100:";

    private static AmazonSNSClient snsClient = getAmazonSNSClient();
    private static String arnStorage;

    private static AmazonSNSClient getAmazonSNSClient(){
        Log.e(TAG, "AWSMessagingService()");
        AWSCredentials credentials = new BasicAWSCredentials(access_id,secret_key);
        AWSCredentialsProvider provider = new StaticCredentialsProvider(credentials);
        snsClient = new AmazonSNSClient(provider);
        snsClient.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1));
        return snsClient;
    }

    public static void subscriptSNSTopic(String token, final String topic, final Activity activity) {
        final String tempTopic = topicArn +topic;
        Log.e(TAG, "subscriptSNSTopic Start.");

        final CreatePlatformEndpointRequest createPlatformEndpointRequest = new CreatePlatformEndpointRequest()
                .withPlatformApplicationArn(appArn)
                .withToken(token)
                .withCustomUserData(Build.DEVICE);

        new Thread(new Runnable(){
            @Override
            public void run() {
                CreatePlatformEndpointResult result = snsClient.createPlatformEndpoint(createPlatformEndpointRequest);
                Log.e(TAG, "Get Endpoint Arn " +result.getEndpointArn());
                SubscribeRequest subscribeRequest = new SubscribeRequest()
                        .withTopicArn(tempTopic)
                        .withProtocol("application")
                        .withEndpoint(result.getEndpointArn());
                snsClient.subscribe(subscribeRequest);
                AWSMessagingService.arnStorage = result.getEndpointArn();
                DeviceStorage.storeARN(result.getEndpointArn(), activity);
                Log.e(TAG, "Success to subscript SNS Topic : " + topic);
            }
        }).start();
    }

    public static void unSubscriptSNSTopic(String token, String topic, final Activity activity) {
        Log.e(TAG, "unSubscriptSNSTopic Start.");
        final String tempTopic = topicArn +topic;
        Log.e(TAG, "unSubscriptSNSTopic Start."+AWSMessagingService.arnStorage);
        new Thread(new Runnable(){
            @Override
            public void run() {
                ListSubscriptionsByTopicRequest listSubscriptionsByTopicRequest = new ListSubscriptionsByTopicRequest().withTopicArn(tempTopic);
                ListSubscriptionsByTopicResult listSubscriptionsByTopicResult = snsClient.listSubscriptionsByTopic(listSubscriptionsByTopicRequest);
                for (Subscription subscription : listSubscriptionsByTopicResult.getSubscriptions()) {
                    UnsubscribeRequest unsubscribeRequest = new UnsubscribeRequest().withSubscriptionArn(subscription.getSubscriptionArn());
                    Log.e(TAG, "subscription.getSubscriptionArn() : " + subscription.getSubscriptionArn());
                    Log.e(TAG,"unsubscribeRequest : " + unsubscribeRequest);
                   // if (subscription.get)
                    if (DeviceStorage.getStringFormConfigFile(activity.getResources().getString(R.string.SHARED_PREF_KEY_INBOX_RECORDSHARED_PREF_KEY_ARN), activity).equals(subscription.getEndpoint()))
                        snsClient.unsubscribe(unsubscribeRequest);
                }

                Log.e(TAG, "Success to unsubscribe SNS Topic " );
            }
        }).start();


//        UnsubscribeRequest unsubscribeRequest = new UnsubscribeRequest().;
//        snsClient.unsubscribe(unsubscribeRequest);

/*
        ListSubscriptionsByTopicRequest listSubscriptionsByTopicRequest = new ListSubscriptionsByTopicRequest().withTopicArn(tempTopic);
        ListSubscriptionsByTopicResult listSubscriptionsByTopicResult = snsClient.listSubscriptionsByTopic(listSubscriptionsByTopicRequest);
        for (Subscription subscription : listSubscriptionsByTopicResult.getSubscriptions()) {
            UnsubscribeRequest unsubscribeRequest = new UnsubscribeRequest().withSubscriptionArn(subscription.getSubscriptionArn());
            snsClient.unsubscribe(unsubscribeRequest);
        }
*/

    }
    /**
     * @return the ARN the app was registered under previously, or null if no
     *         platform endpoint ARN is stored.
     */
    private String retrieveEndpointArn() {
        // Retrieve the platform endpoint ARN from permanent storage,
        // or return null if null is stored.
        return arnStorage;
    }

}
