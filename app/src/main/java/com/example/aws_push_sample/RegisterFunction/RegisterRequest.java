package com.example.aws_push_sample.RegisterFunction;

import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.aws_push_sample.InboxFunction.InboxService.InboxRequestObject;
import com.google.gson.Gson;
import org.json.JSONObject;

public class RegisterRequest {
    final String TAG = "RegisterRequest";

    public interface VolleyCallback {
        void onSuccess(String result);
    }

    public void getTableRecord(final VolleyCallback callback, RequestQueue mQueue, InboxRequestObject obj, String url){
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
                        callback.onSuccess(String.valueOf(response));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("err", error.toString());
                    }
                }
        );
        mQueue.add(mJsonObjectRequest);
    }
}
