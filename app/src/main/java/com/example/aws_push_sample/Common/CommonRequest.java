package com.example.aws_push_sample.Common;

import android.util.Log;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.aws_push_sample.Object.CommonResponse;
import com.example.aws_push_sample.R;
import com.google.gson.Gson;
import android.content.res.Resources;

import org.json.JSONObject;

import java.util.ArrayList;

public class CommonRequest {
    final String TAG = "API Request";
    private static String AWS_ApiGateway = "klb4ptx1v5";     //Resources.getSystem().getString(R.string.AWS_ApiGateway);
    private static String AWS_Region = "ap-northeast-1";     //Resources.getSystem().getString(R.string.AWS_Region);
    private static String AWS_Stage = "Dev";                 //Resources.getSystem().getString(R.string.AWS_Stage);
    public static String registerAPI_Url = "https://"+ AWS_ApiGateway +".execute-api."+ AWS_Region +".amazonaws.com/"+ AWS_Stage +"/registerDeviceToken/";
    public static String getInboxMessageAPI_Url = "https://"+ AWS_ApiGateway +".execute-api."+ AWS_Region +".amazonaws.com/"+ AWS_Stage +"/retrieveInboxRecord/";
    public interface VolleyCallback {
        void onSuccess(CommonResponse result);
        void onError(CommonResponse result);
    }

    public void postJson(final VolleyCallback callback, RequestQueue mQueue, Object obj, String url){
        final Gson gson = new Gson();
        String json = gson.toJson(obj);
        Log.e(TAG, " Start - Call API");
        JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("postJson Success",String.valueOf(response));
                        callback.onSuccess(gson.fromJson(String.valueOf(response), CommonResponse.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("postJson Fails",String.valueOf(error));
                        CommonResponse response = null;
                        try {
                            response = gson.fromJson(String.valueOf(error), CommonResponse.class);
                        } catch (Exception e) {
                            Log.e("postJson Fails", e.toString());
                            response = new CommonResponse(999, String.valueOf(error));
                        }
                        callback.onError(response);
                    }
                }
        );
        mJsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mQueue.add(mJsonObjectRequest);
    }
}
