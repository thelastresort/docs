package com.flamingo.demo.guopan;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jammy on 5/5/16.
 */
public class DemoAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mData;

    public DemoAdapter(Context context, List<String> data) {
        this.mContext = context;
        this.mData = data;
    }

    public void setData(List<String> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return null == mData ? 0 : mData.size();
    }

    @Override
    public Object getItem(int pos) {
        return (null == mData || pos < 0 || pos >= mData.size()) ? null : mData.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.list_item_layout, null);
        }
        TextView textView = (TextView)convertView.findViewById(R.id.list_view_item_text_view);
        String str = (String)getItem(position);
        textView.setText(str);
        return convertView;
    }
}
