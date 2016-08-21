package com.flamingo.demo.guopan;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jammy on 5/5/16.
 */
public class SearchAppActivity extends Activity {

    private static final String TAG = "SearchAppActivity";
    private ListView mListView;
    private ArrayList<AppInfo> mData = new ArrayList<>();
    private ArrayList<AppInfo> mDataSearch = new ArrayList<>();
    private AddGameListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_list_layout);

        mAdapter = new AddGameListAdapter(mData, this);
        mListView = (ListView)findViewById(R.id.my_app_list);
        mListView.setAdapter(mAdapter);

        final EditText input = (EditText)findViewById(R.id.input_app_name);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                input.removeTextChangedListener(this);
                searchApp(editable.toString());
                input.addTextChangedListener(this);
            }
        });

        findViewById(R.id.input_app_name_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText("");
            }
        });

        getAppList();
    }


    private void searchApp(String key) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(key.trim())) {
            mAdapter.setData(mData);
            return;
        }
        if (mData == null || mData.size() == 0) {
            return;
        }
        String keyNoSpace = key.trim();
        mDataSearch.clear();
        for (AppInfo appInfo : mData) {
            if (appInfo.appname.contains(keyNoSpace)) {
                mDataSearch.add(appInfo);
            }
        }
        mAdapter.setData(mDataSearch);
    }


    private void getAppList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppInfoProvider appInfoProvider = new AppInfoProvider(SearchAppActivity.this);
                List<AppInfo> list = appInfoProvider.getAllApps();
                mHandler.sendMessage(mHandler.obtainMessage(MSG_GET_DATA_DONE, 0, 0, list));
            }
        }).start();
    }


    private static final int MSG_GET_DATA_DONE = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case MSG_GET_DATA_DONE:
                    List<AppInfo> list = (List<AppInfo>)message.obj;
                    mData.clear();
                    mData.addAll(list);
                    mAdapter.setData(mData);
                    break;
                default:
                    break;
            }
        }
    };
}
