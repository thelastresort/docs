package com.flamingo.demo.guopan.searchapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.flamingo.demo.oak.R;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jammy on 5/5/16.
 */
public class SearchAppActivity extends Activity {

    private static final String TAG = "SearchAppActivity";
    private ListView mListView;
    private ArrayList<AppInfo> mData = new ArrayList<>();
    private ArrayList<AppInfo> mDataSearch = new ArrayList<>();
    private AddGameListAdapter mAdapter;
    private Map<Character, String> chiCharacterToDigit = new HashMap<>();
    private static final String PINYIN_TO_DIGIT = "22233344455566677778889999";

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


    private void initPinyinTable() {
        String pinyinTable = "pinyin_table.txt";
        String outputFilePath = this.getCacheDir() + File.separator + pinyinTable;
        if (!new File(outputFilePath).exists()) {
            AssetsUtil.copyFromAssets(this, pinyinTable, outputFilePath);
        }
        String content = AssetsUtil.readFile(new File(outputFilePath), null);
        String[] characters = content.split("\n");
        Log.i(TAG, "characters size " + characters.length);
        for (String character : characters) {
            char c = character.charAt(0);
            String pinyin = character.substring(character.indexOf("（")+1, character.indexOf("）")-1);
            String pinyinDigit = getAllDigitForPinyin(romoveDigitAndSpace(pinyin));
//            Log.i(TAG, c + " " + pinyin + " " + pinyinDigit);
            chiCharacterToDigit.put(c, pinyinDigit);
        }
    }


    private String romoveDigitAndSpace(String s) {
        String result = "";
        for (int i=0; i<s.length(); ++i) {
            if (s.charAt(i) != ' ' && !Character.isDigit(s.charAt(i))) {
                result  += s.charAt(i);
            }
        }
        return result;
    }


    private String getAllDigitForPinyin(String pinyin) {
        if (TextUtils.isEmpty(pinyin)) {
            return "";
        }
        String s = "";
        for (int i=0; i<pinyin.length(); ++i) {
            s += PINYIN_TO_DIGIT.charAt(pinyin.charAt(i)-'a');
        }
        return s;
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
            if (isMacth(appInfo, keyNoSpace)) {
                mDataSearch.add(appInfo);
            }
        }
        mAdapter.setData(mDataSearch);
    }

    private boolean isMacth(AppInfo appInfo, String key) {
        if (null == appInfo || TextUtils.isEmpty(key)) {
            return false;
        }
        if (appInfo.appname.contains(key)) {
            return true;
        }
        String strLarge = appInfo.pinyinNameToDigit;
        String strSmall = key;
        int startPos = 0;
        int matchCount = 0;
        for (int i=0; i<strSmall.length(); ++i) {
            for (; startPos<strLarge.length(); ++startPos) {
                if (strLarge.charAt(startPos) == strSmall.charAt(i)) {
                    matchCount++;
                    startPos++;
                    break;
                }
            }
        }
        if (matchCount == strSmall.length()) {
            return true;
        }
        return false;
    }


    private void getAppList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppInfoProvider appInfoProvider = new AppInfoProvider(SearchAppActivity.this);
                List<AppInfo> list = appInfoProvider.getAllApps();
                initPinyinTable();
                setPinyinNameDigitToAppinfo(list);
                mHandler.sendMessage(mHandler.obtainMessage(MSG_GET_DATA_DONE, 0, 0, list));
            }
        }).start();
    }


    private void setPinyinNameDigitToAppinfo(List<AppInfo> list) {
        if (null == list || list.size() == 0) {
            return;
        }
        for (AppInfo appInfo : list) {
            appInfo.pinyinNameToDigit = getPinyinNameDigit(appInfo.appname);
            Log.i(TAG, "app name to digit " + appInfo.appname + " " + appInfo.pinyinNameToDigit);
        }
    }


    private String getPinyinNameDigit(String appName) {
        if (TextUtils.isEmpty(appName)) {
            return "";
        }
        String s = "";
        for (int i=0; i<appName.length(); ++i) {
            char c = appName.charAt(i);
            if (Character.isDigit(c)) {
                s += c;
            } else if (('A' <=c  && c <= 'Z') || ('a' <= c && c <= 'z')) {
                s += PINYIN_TO_DIGIT.charAt(Character.toLowerCase(c) - 'a');
            } else if (Character.isSpaceChar(appName.charAt(i))) {
                // 空格不处理
            } else if (chiCharacterToDigit.containsKey(appName.charAt(i))) {
                Log.i(TAG, appName.charAt(i) + " -> " + chiCharacterToDigit.get(appName.charAt(i)));
                s += chiCharacterToDigit.get(appName.charAt(i));
            }
        }
        return s;
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
