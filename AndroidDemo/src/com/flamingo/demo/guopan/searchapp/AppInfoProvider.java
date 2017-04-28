package com.flamingo.demo.guopan.searchapp;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class AppInfoProvider {

    private static final String TAG = "AppInfoProvider";
    private PackageManager packmanager;
    public static List<AppInfo> infos_list = new ArrayList<>();

    public AppInfoProvider(Context context) {
        packmanager = context.getPackageManager();
    }


    /**
     * 返回当前手机里面安装的所有的程序信息的集合
     *
     * @return 应用程序的集合
     */
    public List<AppInfo> getAllApps() {
        if (infos_list.size()!=0) {
            return infos_list;
        } else {
            infos_list = new ArrayList<AppInfo>();
            List<PackageInfo> packinfos = packmanager.getInstalledPackages(0);
            for (PackageInfo info : packinfos) {
                AppInfo myApp = new AppInfo(packmanager);
                String packname = info.packageName;
                String version = info.versionName;
                if(version == null)
                    version = "unkonw";
                myApp.version = version;
                myApp.packname = packname;
                myApp.versionCode = info.versionCode;
                ApplicationInfo appinfo = info.applicationInfo;
                myApp.appname = appinfo.loadLabel(packmanager).toString();
                myApp.installTime = info.firstInstallTime;
                myApp.applicationInfo = appinfo;

                if (filterApp(appinfo)) {
                    Log.i(TAG, ""+myApp.appname+" " + myApp.version);
                    infos_list.add(myApp);
                }
            }
        }
        return infos_list;

    }


    public boolean filterApp(ApplicationInfo info) {
        if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            return true;
        } else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            return true;
        }
        return false;
    }


    private AppInfo getAppInfo(PackageInfo info) {
        AppInfo app = new AppInfo(packmanager);
        String packName = info.packageName;
        String version = info.versionName;

        if(version == null) {
            version = "unknown";
        }
        app.version = version;
        app.packname = packName;
        ApplicationInfo application = info.applicationInfo;
        String appName = application.loadLabel(packmanager).toString();
        app.appname = appName;

        return app;
    }

}
