package com.flamingo.demo.guopan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    String TAG = "MainActivity";
    View mGoToLogin;
    TextView mTvLog;

    private static final String APP_ID = "101101";
    private static final String APP_KEY = "GuopanSDK8^(Llad";

    private boolean mIsInitSuc = false;

    private boolean mIsTest = true;

    // TODO 请注意demo中标记TODO的地方
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.goto_login_btn).setOnClickListener(listener);
        findViewById(R.id.buy_btn).setOnClickListener(listener);
        findViewById(R.id.btn_logout).setOnClickListener(listener);
        findViewById(R.id.btn_invoke_init_and_login).setOnClickListener(listener);
        findViewById(R.id.btn_invoke_sdk_version).setOnClickListener(listener);
        findViewById(R.id.btn_invoke_login_info).setOnClickListener(listener);
        findViewById(R.id.btn_invoke_exit).setOnClickListener(listener);
        findViewById(R.id.btn_switch_environment).setOnClickListener(listener);
        findViewById(R.id.btn_upload_player_info).setOnClickListener(listener);
        mGoToLogin = findViewById(R.id.goto_login_btn);
        mTvLog = (TextView) findViewById(R.id.tv_log);
    }

    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buy_btn:
                    break;
                default:
                    break;
            }
        }
    };


    private void showToast(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    public static void show(Context context, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
