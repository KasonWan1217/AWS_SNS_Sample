package com.example.aws_push_sample.InboxFunction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.aws_push_sample.DeviceStorage;
import com.example.aws_push_sample.InboxFunction.InboxService.InboxRecordObject;
import com.example.aws_push_sample.Common.CommonRequest;
import com.example.aws_push_sample.InboxFunction.InboxService.InboxRequestObject;
import com.example.aws_push_sample.R;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class InboxMainActivity extends AppCompatActivity implements InboxRecyclerViewAdapter.ItemClickListener {

    final String TAG = "InboxMainActivity";
    String postAPI = "https://mlofuci190.execute-api.ap-northeast-1.amazonaws.com/Prod/getInboxMessage/";
    //TableLayout tableView_inbox;
    ProgressBar progressBar;
    InboxRecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //tableView_inbox = (TableLayout) findViewById(R.id.tableView_inbox);
        recyclerView = (RecyclerView) findViewById(R.id.tableView_inbox);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        InboxRequestObject tempObj = new InboxRequestObject("001", "");
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        progressBar.setVisibility(View.VISIBLE);

        new CommonRequest().postJson(new CommonRequest.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                Log.e(TAG, "onSuccess()");
                //activity.getResources().getString(R.string.SHARED_PREF_KEY_ARN)
                //if (result != null || !"".equals(result))
                DeviceStorage.storeStringToSharedPreferences(getString(R.string.SHARED_PREF_KEY_Inbox_Record), result, getString(R.string.SHARED_PREF_FILE_INBOX_RECORD), InboxMainActivity.this);
                DeviceStorage.getStringFormSharedPreferences(getString(R.string.SHARED_PREF_KEY_Inbox_Record), getString(R.string.SHARED_PREF_FILE_INBOX_RECORD), InboxMainActivity.this);
                DeviceStorage.storeStringToSharedPreferences(getString(R.string.SHARED_PREF_KEY_Inbox_Record), "Content", getString(R.string.SHARED_PREF_FILE_INBOX_RECORD), InboxMainActivity.this);

                List<InboxRecordObject> list = Arrays.asList(new Gson().fromJson(result, InboxRecordObject[].class));
                recyclerView.setLayoutManager(new LinearLayoutManager(InboxMainActivity.this));
                adapter = new InboxRecyclerViewAdapter(InboxMainActivity.this, list);
                adapter.setClickListener(InboxMainActivity.this);
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
            }
        }, mQueue, tempObj, postAPI);

    }

    @Override
    public void onItemClick(View view, int position) {
        InboxDetailActivity inboxDetailActivity = new InboxDetailActivity();
        Intent i = new Intent(this, InboxDetailActivity.class);
        i.putExtra("InboxDetailObject", adapter.getItem(position));
        startActivity(i);
        Toast.makeText(this, "You click " + position, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}