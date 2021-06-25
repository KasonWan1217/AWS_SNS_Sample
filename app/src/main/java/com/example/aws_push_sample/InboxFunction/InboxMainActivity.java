package com.example.aws_push_sample.InboxFunction;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.aws_push_sample.DeviceStorage;
import com.example.aws_push_sample.Object.CommonResponse;
import com.example.aws_push_sample.Object.InboxService.InboxRecordResponse;
import com.example.aws_push_sample.Common.CommonRequest;
import com.example.aws_push_sample.Object.InboxService.InboxRequest;
import com.example.aws_push_sample.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.aws_push_sample.Common.CommonRequest.getInboxMessageAPI_Url;

public class InboxMainActivity extends AppCompatActivity implements InboxRecyclerViewAdapter.ItemClickListener {

    final String TAG = "InboxMainActivity";
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
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        //tableView_inbox = (TableLayout) findViewById(R.id.tableView_inbox);
        recyclerView = findViewById(R.id.tableView_inbox);
        progressBar = findViewById(R.id.progressBar);
        String app_ref_id = DeviceStorage.getStringFormConfigFile(getString(R.string.SHARED_PREF_KEY_App_Ref_ID), InboxMainActivity.this);
        String datetime = DeviceStorage.getStringFormConfigFile(getString(R.string.SHARED_PREF_KEY_Access_Datetime), InboxMainActivity.this);

        final InboxRequest request = new InboxRequest(app_ref_id, datetime);
        Log.e("InboxRequest: ", new Gson().toJson(request) );
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        progressBar.setVisibility(View.VISIBLE);

        new CommonRequest().postJson(new CommonRequest.VolleyCallback() {
            @Override
            public void onSuccess(CommonResponse result) {
                //activity.getResources().getString(R.string.SHARED_PREF_KEY_ARN)
                //if (result != null || !"".equals(result))
                List<InboxRecordResponse> list_record = new ArrayList<InboxRecordResponse>();

                if (result.getMessage() != null && result.getMessage().size() > 0) {
                    Log.e("Response: ", result.getString_message());
                    //DeviceStorage.storeStringToSharedPreferences(getString(R.string.SHARED_PREF_KEY_Access_Datetime), response.getCreate_datetime(), getString(R.string.SHARED_PREF_FILE_PUSH_SERVICE_SETTING), InboxMainActivity.this);
                    list_record.addAll(Arrays.asList(new Gson().fromJson(result.getList_message(), InboxRecordResponse[].class)));
                    DeviceStorage.storeStringToSharedPreferences(getString(R.string.SHARED_PREF_KEY_Inbox_Record), new Gson().toJson(list_record), getString(R.string.SHARED_PREF_FILE_INBOX_RECORD), InboxMainActivity.this);
                    DeviceStorage.storeStringToSharedPreferences(getString(R.string.SHARED_PREF_KEY_Access_Datetime), list_record.get(0).getMsg_timestamp(), getString(R.string.SHARED_PREF_FILE_PUSH_SERVICE_SETTING), InboxMainActivity.this);
                }
                String inbox_record = DeviceStorage.getStringFormSharedPreferences(getString(R.string.SHARED_PREF_KEY_Inbox_Record), getString(R.string.SHARED_PREF_FILE_INBOX_RECORD), InboxMainActivity.this);
                if (!"null".equals(inbox_record))
                    list_record.addAll(Arrays.asList(new Gson().fromJson(inbox_record, InboxRecordResponse[].class)));

                recyclerView.setLayoutManager(new LinearLayoutManager(InboxMainActivity.this));
                adapter = new InboxRecyclerViewAdapter(InboxMainActivity.this, list_record);
                adapter.setClickListener(InboxMainActivity.this);
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(CommonResponse result) {
                Toast.makeText(getApplicationContext(), "Get Inbox Record Fails.", Toast.LENGTH_LONG).show();

                String inbox_record = DeviceStorage.getStringFormSharedPreferences(getString(R.string.SHARED_PREF_KEY_Inbox_Record), getString(R.string.SHARED_PREF_FILE_INBOX_RECORD), InboxMainActivity.this);
                List<InboxRecordResponse> list_record = Arrays.asList(new Gson().fromJson(inbox_record, InboxRecordResponse[].class));
                recyclerView.setLayoutManager(new LinearLayoutManager(InboxMainActivity.this));
                adapter = new InboxRecyclerViewAdapter(InboxMainActivity.this, list_record);
                adapter.setClickListener(InboxMainActivity.this);
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
            }
        }, mQueue, request, getInboxMessageAPI_Url);

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