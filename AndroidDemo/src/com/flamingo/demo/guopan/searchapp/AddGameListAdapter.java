package com.flamingo.demo.guopan.searchapp;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.flamingo.demo.oak.R;

import java.util.ArrayList;
import java.util.List;

class AddGameListAdapter extends BaseAdapter {

    private PackageManager packmanager;
    List<AppInfo> mAppList;
    Context mContext;

    public AddGameListAdapter(ArrayList<AppInfo> mAppList, Context mContext) {
        this.mAppList = mAppList;
        this.mContext = mContext;
        this.packmanager = mContext.getPackageManager();
    }

    public void setData(List<AppInfo> list){
        mAppList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mAppList == null)
            return 0;
        return mAppList.size();
    }

    @Override
    public Object getItem(int pos) {
        if (null == mAppList || pos >= mAppList.size()) {
            return null;
        }
        return mAppList.get(pos);
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {
        ViewHolder holder = null;
        if (convertView == null) {
            View view = View.inflate(mContext, R.layout.app_item, null);
            holder = new ViewHolder();
            holder.icon = (ImageView) view.findViewById(R.id.add_game_icon);
            holder.name = (TextView) view.findViewById(R.id.add_game_name);
            convertView = view;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Object obj = getItem(position);
        if (null == obj) {
            return convertView;
        }
        final AppInfo appInfo = (AppInfo) obj;
        holder.name.setText(appInfo.appname);
        if (appInfo.icon != null) {
            holder.icon.setBackgroundDrawable(appInfo.icon);
        } else {
            final ViewHolder finalHolder = holder;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final Drawable drawable = appInfo.applicationInfo.loadIcon(packmanager);
                    ((Activity)mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            appInfo.icon = drawable;
                            finalHolder.icon.setBackgroundDrawable(appInfo.icon);
                        }
                    });

                }
            }).start();
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpSettingUtils.startInstalledAppDetailsActivity(mContext, appInfo.packname);
            }
        });
        return convertView;
    }


    class ViewHolder {
        ImageView icon;
        TextView name;
    }
}