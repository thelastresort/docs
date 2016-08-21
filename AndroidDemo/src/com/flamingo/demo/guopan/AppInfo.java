package com.flamingo.demo.guopan;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;


import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;


public class AppInfo{
    public String appname;
    public String packname;
    public String version;
    public Drawable icon;
    public long installTime = 0;
    public ApplicationInfo applicationInfo;

    private PackageManager packmanager;

    public AppInfo(PackageManager packmanager) {
        this.packmanager = packmanager;
    }
}

