package com.meiriq.xposehook;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.meiriq.xposehook.adapter.AppListAdapter;
import com.meiriq.xposehook.bean.AppInfo;
import com.meiriq.xposehook.tutorial.AppUtils;
import com.meiriq.xposehook.utils.L;
import com.meiriq.xposehook.utils.XposeUtil;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecordAppFileChooseActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private List<AppInfo> installApps;
    private AppListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uninstall_white);
        initActionBar();

        initView();
        initData();
    }


    private List<String> mapKey2List(String map){
        List<String> list = new ArrayList<>();
        if(TextUtils.isEmpty(map)){
            return list;
        }
        String[] split = map.split(",");
        if(split == null)
            return list;
        for (int i = 0; i < split.length; i++) {
            String pkg = split[i].split("=")[0];
            if(pkg.startsWith("{"))
                pkg = pkg.substring(1);
            list.add(pkg.trim());
        }
        return list;
    }
    private void initData() {


        installApps = AppUtils.getInstance().getInstallApps(this, AppUtils.APP_TYPE_CUSTOM);
        List<String>selectApps = mapKey2List(XposeUtil.configMap.optString(XposeUtil.FileRecordPackageName));
        if(selectApps.size()>0){
            for (int i = 0; i < selectApps.size(); i++) {
                for (int j = 0; j < installApps.size(); j++) {
                    if(selectApps.get(i).equals(installApps.get(j).getPname())){
                        installApps.get(j).setIsSelect(true);
                        break;
                    }
                }
            }

        }
        adapter = new AppListAdapter(this);
        adapter.setData(installApps);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
    }

    private void initView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recycleview);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            back();
        }


        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void back() {
        super.back();
        save();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        XposeUtil.saveConfigMap();
    }

    private void save() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.clear();
        for (int i = 0; i < installApps.size(); i++) {
            AppInfo appInfo = installApps.get(i);
            if(appInfo.isSelect()){
                hashMap.put(appInfo.getPname(), appInfo.getAppname());
            }
        }
        L.debug(hashMap.toString());
        try {
            XposeUtil.configMap.put(XposeUtil.FileRecordPackageName,hashMap.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setResult(Activity.RESULT_OK);
        finish();
    }
}
