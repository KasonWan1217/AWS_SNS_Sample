package com.example.aws_push_sample;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.widget.*;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.aws_push_sample.Common.CommonRequest;
import com.example.aws_push_sample.InboxFunction.InboxMainActivity;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.example.aws_push_sample.RegisterFunction.RegisterRequestObject;
import com.example.aws_push_sample.RegisterFunction.RegisterResponseObject;
import com.google.android.gms.common.internal.service.Common;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    final String TAG = "MainActivity";
    private Button btn_retrieveToken, btn_regToken, btn_mpf_subscript, btn_mpf_unsubscript, btn_sendNotification, btn_sendGroupNotification;
    private ImageButton imagebtn_inbox;
    private TextView txt_deviceToken;
    private ProgressBar progressBar;
    String postAPI = "https://"+R.string.AWS_ApiGateway+".execute-api."+R.string.AWS_Region+".amazonaws.com/"+R.string.AWS_Stage+"/getInboxMessage/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        btn_retrieveToken = (Button) findViewById(R.id.btn_retrieveToken);
        btn_regToken = (Button) findViewById(R.id.btn_regToken);
        btn_mpf_subscript = (Button) findViewById(R.id.btn_mpf_subscript);
        btn_mpf_unsubscript = (Button) findViewById(R.id.btn_mpf_unsubscript);
        btn_sendNotification = (Button) findViewById(R.id.btn_sendNotification);
        btn_sendGroupNotification = (Button) findViewById(R.id.btn_sendGroupNotification);
        txt_deviceToken = (TextView) findViewById(R.id.label_deviceToken);

        View headerview = navigationView.getHeaderView(0);
        imagebtn_inbox = (ImageButton) headerview.findViewById(R.id.imagebtn_inbox);
        imagebtn_inbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this , InboxMainActivity.class);
                startActivity(intent);
            }
        });

        txt_deviceToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("txt_token", txt_deviceToken.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), R.string.COMMON_COPIED, Toast.LENGTH_LONG).show();
            }
        });

        btn_retrieveToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //retrieveToken();
                MyFirebaseMessagingService.retrieveDeviceToken(MainActivity.this);
                txt_deviceToken.setVisibility(View.VISIBLE);
                txt_deviceToken.setText(DeviceStorage.getStringFormConfigFile(getString(R.string.SHARED_PREF_KEY_Token), MainActivity.this));

            }
        });
        btn_regToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = DeviceStorage.getStringFormConfigFile(getString(R.string.SHARED_PREF_KEY_Token), MainActivity.this);
                RegisterRequestObject request =  new RegisterRequestObject("", token, "BEA APP", "iOS", "BA");

                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
                progressBar.setVisibility(View.VISIBLE);

                new CommonRequest().postJson(new CommonRequest.VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        Log.e(TAG, "onSuccess()");
                        //activity.getResources().getString(R.string.SHARED_PREF_KEY_ARN)
                        //if (result != null || !"".equals(result))

                        RegisterResponseObject response = new Gson().fromJson(result, RegisterResponseObject.class);
                        DeviceStorage.storeStringToSharedPreferences(getString(R.string.SHARED_PREF_KEY_App_Ref_ID), response.getApp_reg_id(), getString(R.string.SHARED_PREF_FILE_PUSH_SERVICE_SETTING), MainActivity.this);
                        DeviceStorage.storeStringToSharedPreferences(getString(R.string.SHARED_PREF_KEY_Create_Datetime), response.getCreate_datetime(), getString(R.string.SHARED_PREF_FILE_PUSH_SERVICE_SETTING), MainActivity.this);
                        progressBar.setVisibility(View.GONE);
                    }
                }, mQueue, request, postAPI);
                //AWSMessagingService.subscriptSNSTopic(token,  getString(R.string.topic_Default_Group01), MainActivity.this);
                Toast.makeText(getApplicationContext(), "Register Success.", Toast.LENGTH_LONG).show();
            }
        });

        btn_mpf_subscript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = DeviceStorage.getStringFormConfigFile(getString(R.string.SHARED_PREF_KEY_Token), MainActivity.this);
                //MyFirebaseMessagingService.subscribe(MainActivity.this, getString(R.string.topic_MPF));
                //AWSMessagingService.subscriptSNSTopic(token, getString(R.string.topic_MPF), MainActivity.this);
                Toast.makeText(getApplicationContext(), "subscript SNS Topic Success.", Toast.LENGTH_LONG).show();
            }
        });
        btn_mpf_unsubscript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MyFirebaseMessagingService.unSubscribe(MainActivity.this, getString(R.string.topic_MPF));
                //AWSMessagingService.unSubscriptSNSTopic(DeviceStorage.getStringFormConfigFile(getString(R.string.SHARED_PREF_KEY_Token), MainActivity.this),getString(R.string.topic_MPF),MainActivity.this);
                Toast.makeText(getApplicationContext(), "unsubscribe SNS Topic Success.", Toast.LENGTH_LONG).show();
            }
        });

        btn_sendNotification.setVisibility(View.GONE);
        btn_sendGroupNotification.setVisibility(View.GONE);
    }

//Other
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the action
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
