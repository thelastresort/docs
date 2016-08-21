package com.flamingo.demo.guopan;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import com.flamingo.demo.oak.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jammy on 5/5/16.
 */
public class ListViewActivity extends Activity {

    private static final String TAG = "ListViewActivity";
    private ListView mListView;
    private List<String> mData = new ArrayList<>();
    private DemoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity_layout);

        findViewById(R.id.btn_start_load_data).setOnClickListener(listener);

        mAdapter = new DemoAdapter(this, mData);
        mListView = (ListView)findViewById(R.id.listview);
        mListView.setAdapter(mAdapter);
    }


    private static final int MSG_FRESH_LIST_DATA = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case MSG_FRESH_LIST_DATA:
                    mAdapter.setData(mData);
                    break;
                default:
                    break;
            }
        }
    };


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_start_load_data:
                    onClickStartLoadData();
                    break;
                default:
                    break;
            }
        }
    };


    private void onClickStartLoadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i<100; ++i) {
                    try {
                        Thread.sleep(1); // TODO
                    } catch (Exception e) {
                    }
                    mData.clear();
                    for (int j=0; j<30; j++) {
                        mData.add("string_" + i + "_" + j);
                    }
                    mHandler.sendEmptyMessage(MSG_FRESH_LIST_DATA);
                }
            }
        }).start();
    }
}
