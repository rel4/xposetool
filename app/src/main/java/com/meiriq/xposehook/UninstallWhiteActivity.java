package com.meiriq.xposehook;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.meiriq.xposehook.adapter.AppListAdapter;
import com.meiriq.xposehook.bean.AppInfo;
import com.meiriq.xposehook.dao.WhiteUninstallDao;
import com.meiriq.xposehook.tutorial.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class UninstallWhiteActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private List<AppInfo> installApps;
    private AppListAdapter adapter;
    private WhiteUninstallDao whiteUninstallDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uninstall_white);
        initActionBar();

        initView();
        initData();
    }


    private void initData() {

        whiteUninstallDao = new WhiteUninstallDao(this);
        List<AppInfo> whiteAppInfos = whiteUninstallDao.getAllData();

        installApps = AppUtils.getInstance().getInstallApps(this, AppUtils.APP_TYPE_CUSTOM);
        for (AppInfo whiteAppInfo :
                whiteAppInfos) {
            for (AppInfo installAppInfo :
                    installApps) {
                if (installAppInfo.getPname().equals(whiteAppInfo.getPname()))
                    installAppInfo.setIsSelect(true);
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
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                saveWhiteApp();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            saveWhiteApp();
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    //增加白名单数据
    private void saveWhiteApp() {
        whiteUninstallDao.clean();
        for (int i = installApps.size()-1; i >=0; i--) {
            AppInfo appInfo = installApps.get(i);
            if (!appInfo.isSelect()){
                installApps.remove(i);
            }
        }
        if(installApps.size()>0){
            whiteUninstallDao.addList(installApps);
        }
        setResult(RESULT_OK);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        whiteUninstallDao.close();
    }
}
