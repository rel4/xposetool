package com.meiriq.xposehook.tutorial;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.meiriq.xposehook.bean.AppInfo;
import com.meiriq.xposehook.utils.L;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by tian on 15-11-26.
 */
public class AppUtils {
    public final static int APP_TYPE_ALL = 0;
    public final static int APP_TYPE_CUSTOM = 1;

    private AppUtils(){};
    private static AppUtils appUtils;
    private static ExecutorService executorService;

    public static synchronized AppUtils getInstance(){
        if(appUtils == null){
            appUtils = new AppUtils();
            executorService = Executors.newFixedThreadPool(2);
        }
        return appUtils;
    }

    public boolean killAppByPackageName(Context context,AppInfo appInfo){
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        for (int i = 0; i < runningAppProcesses.size(); i++) {
            String[] pkgList = runningAppProcesses.get(i).pkgList;
            for (int ij = 0; ij < pkgList.length; ij++) {
                if(pkgList[ij].equals(appInfo.getPname())){

                    Shell.execCommand("kill " + runningAppProcesses.get(i).pid,true);
                    activityManager.killBackgroundProcesses(appInfo.getPname());
                    return true;
                }
            }
        }
        return false;
    }

    public void clearData(final List<AppInfo> appInfos, final Context context, final View view){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < appInfos.size(); i++) {
                    final AppInfo appInfo = appInfos.get(i);
                    if(appInfo.isSelect()){
                        Shell.execCommand("pm clear " + appInfo.getPname(), true);
                        killAppByPackageName(context, appInfo);
                        SystemClock.sleep(300);
                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToast(context, null, "清除" + appInfo.getAppname() + "完成");
                            }
                        });
                    }
                    if(i == appInfos.size()-1){
                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToast(context,null,"清除完成");
                                if(callBack != null)
                                    callBack.unInstall();
                            }
                        });
                    }
                }
            }
        });
    }

    public void uninstall(final List<AppInfo> appInfos, final Context context, final View view){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < appInfos.size(); i++) {
                    final AppInfo appInfo = appInfos.get(i);
                    if(appInfo.isSelect()){
                        Shell.execCommand("pm uninstall " + appInfo.getPname(), true);

                        try {
                            int index = 0;
                            while(true){
                                ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(appInfo.getPname(), 128);
                                L.debug("applicationInfo"+(applicationInfo==null));
                                if(applicationInfo == null){
                                    ((Activity)context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showToast(context,view,"卸载" + appInfo.getPname() + "成功");
                                        }
                                    });
                                    break;
                                }
                                if(index>10)
                                    break;
                                index ++;
                                Thread.sleep(500L);
                            }
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                            ((Activity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showToast(context,view,"卸载" + appInfo.getAppname() + "成功");
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if(i == appInfos.size()-1){
                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToast(context,null,"卸载完成");
                                if(callBack != null)
                                    callBack.unInstall();
                            }
                        });
                    }
                }
            }
        });
    }

    private static void showToast(Context context,View view,String message){
        if(view != null){
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                    .show();
        }else{
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 获取安装的程序
     * @param context
     * @param type APP_TYPE_ALL 全部程序，APP_TYPE_CUSTOM　非预装程序
     * @return
     */
    public List<AppInfo> getInstallApps(Context context,int type){
        List<AppInfo> appList = new ArrayList();
        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            if(packageInfo.packageName.equals("com.meiriq.xposehook")
                    || packageInfo.packageName.equals("de.robv.android.xpose.installer")
                    || packageInfo.packageName.equals("pro.burgerz.wsm.manager")){
                continue;
            }
            if(type == APP_TYPE_CUSTOM){
                if((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0){
                    //非系统应用
                    continue;
                }
            }else if(type == APP_TYPE_ALL){
                //所有应用
            }
            AppInfo appInfo = new AppInfo();
            appInfo.setAppname(packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString()) ;
            appInfo.setPname(packageInfo.packageName);
            appInfo.setVersionName(packageInfo.versionName);
            appInfo.setVersionCode(packageInfo.versionCode);
            appList.add(appInfo);
        }


        return appList;

    }



    public interface CallBack{
        void unInstall();
    }

    CallBack callBack;
    public void setCallBackListener(CallBack callBackListener){
        this.callBack = callBackListener;
    }

    /** Network type is unknown */
    public static final int NETWORK_TYPE_UNKNOWN = 0;
    /** Current network is GPRS */
    public static final int NETWORK_TYPE_GPRS = 1;
    /** Current network is EDGE */
    public static final int NETWORK_TYPE_EDGE = 2;
    /** Current network is UMTS */
    public static final int NETWORK_TYPE_UMTS = 3;
    /** Current network is CDMA: Either IS95A or IS95B*/
    public static final int NETWORK_TYPE_CDMA = 4;
    /** Current network is EVDO revision 0*/
    public static final int NETWORK_TYPE_EVDO_0 = 5;
    /** Current network is EVDO revision A*/
    public static final int NETWORK_TYPE_EVDO_A = 6;
    /** Current network is 1xRTT*/
    public static final int NETWORK_TYPE_1xRTT = 7;
    /** Current network is HSDPA */
    public static final int NETWORK_TYPE_HSDPA = 8;
    /** Current network is HSUPA */
    public static final int NETWORK_TYPE_HSUPA = 9;
    /** Current network is HSPA */
    public static final int NETWORK_TYPE_HSPA = 10;
    /** Current network is iDen */
    public static final int NETWORK_TYPE_IDEN = 11;
    /** Current network is EVDO revision B*/
    public static final int NETWORK_TYPE_EVDO_B = 12;
    /** Current network is LTE */
    public static final int NETWORK_TYPE_LTE = 13;
    /** Current network is eHRPD */
    public static final int NETWORK_TYPE_EHRPD = 14;
    /** Current network is HSPA+ */
    public static final int NETWORK_TYPE_HSPAP = 15;
    /** Current network is GSM {@hide} */
    public static final int NETWORK_TYPE_GSM = 16;
    public static String getNetworkTypeName(int type) {
        switch (type) {
            case NETWORK_TYPE_GPRS:
                return "GPRS";
            case NETWORK_TYPE_EDGE:
                return "EDGE";
            case NETWORK_TYPE_UMTS:
                return "UMTS";
            case NETWORK_TYPE_HSDPA:
                return "HSDPA";
            case NETWORK_TYPE_HSUPA:
                return "HSUPA";
            case NETWORK_TYPE_HSPA:
                return "HSPA";
            case NETWORK_TYPE_CDMA:
                return "CDMA";
            case NETWORK_TYPE_EVDO_0:
                return "CDMA - EvDo rev. 0";
            case NETWORK_TYPE_EVDO_A:
                return "CDMA - EvDo rev. A";
            case NETWORK_TYPE_EVDO_B:
                return "CDMA - EvDo rev. B";
            case NETWORK_TYPE_1xRTT:
                return "CDMA - 1xRTT";
            case NETWORK_TYPE_LTE:
                return "LTE";
            case NETWORK_TYPE_EHRPD:
                return "CDMA - eHRPD";
            case NETWORK_TYPE_IDEN:
                return "iDEN";
            case NETWORK_TYPE_HSPAP:
                return "HSPA+";
            case NETWORK_TYPE_GSM:
                return "GSM";
            default:
                return "UNKNOWN";
        }
    }

}
