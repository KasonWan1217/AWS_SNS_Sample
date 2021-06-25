package com.example.aws_push_sample.InboxFunction;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.example.aws_push_sample.Object.InboxService.InboxRecordResponse;
import com.example.aws_push_sample.R;

public class InboxDetailActivity extends AppCompatActivity {
    TextView tv_title, tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        InboxRecordResponse inboxObject = (InboxRecordResponse) getIntent().getSerializableExtra("InboxDetailObject");

        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_content = (TextView) findViewById(R.id.tv_content);

        tv_title.setText(inboxObject.getMsg_timestamp()+"\n"+inboxObject.getTitle());
        tv_content.setText(inboxObject.getBody());
    }
}