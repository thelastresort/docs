package com.flamingo.demo.guopan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private static final String TAG = "MainActivity_TAG";
    private ArrayList<String> mStringList = new ArrayList<>();
    private Message mMessage = null;


    private static final int MSG_MULTI_THREAD_ACCESS_DATA = 1;
    private static final int MSG_USE_USED_MESSAGE = 2;
    private static final int MSG_DELAY = 3;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case MSG_MULTI_THREAD_ACCESS_DATA:
                    handleMultiThreadDataMessage();
                    break;
                case MSG_USE_USED_MESSAGE:
                    handleUseUsedMessage(message);
                    break;
                case MSG_DELAY:
                    Log.i(TAG, "handle delay mesage, msg.what=" + message.what);
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 主线程处理数据（数据是其他线程已经处理好的）
     */
    private void handleMultiThreadDataMessage() {
        for (String str : mStringList) {
            Log.i(TAG, "str is  " + str);
        }
    }


    private void handleUseUsedMessage(Message msg) {
        Log.i(TAG, String.format("msg addr=%d, msg what=%d, msg arg1=%d", msg.hashCode(), msg.what, msg.arg1));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_multi_thread_access_data).setOnClickListener(listener);
        findViewById(R.id.btn_use_used_message).setOnClickListener(listener);
        findViewById(R.id.btn_send_an_delay_message).setOnClickListener(listener);
    }


    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_multi_thread_access_data:
                    onClickMultiThreadAccessData();
                    break;
                case R.id.btn_use_used_message:
                    onClickUsedMessage();
                    break;
                case R.id.btn_send_an_delay_message:
                    onClickSendDelayMessage();
                    break;
                default:
                    break;
            }
        }
    };


    private void onClickSendDelayMessage() {
        Log.i(TAG, "onClickSendDelayMessage");
        mHandler.sendEmptyMessageDelayed(MSG_DELAY, 10000);
    }


    private void onClickUsedMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i<5; ++i) {
                    mMessage = Message.obtain();
                    mMessage.what = MSG_USE_USED_MESSAGE;
                    mMessage.arg1 = i;
                    mHandler.sendMessage(mMessage);
                    try {
                        Log.i(TAG, "************ onClickUsedMessage sleep for some time ***********");
                        Thread.sleep(5000); // 试试这里改成1毫秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    private void onClickMultiThreadAccessData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Log.i(TAG, "************ onClickMultiThreadAccessData sleep for some time ***********");
                        Thread.sleep(1000); // 试试这里改成1毫秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message.obtain();
                    mStringList.clear();
                    for (int i = 0; i < 10; ++i) {
                        mStringList.add("str" + i);
                    }

                    // 发送消息到主线程，将数据较给主线程处理
                    mHandler.sendEmptyMessage(MSG_MULTI_THREAD_ACCESS_DATA);
                }
            }
        }).start();
    }


    private void showToast(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }


    public static void show(Context context, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }
}
