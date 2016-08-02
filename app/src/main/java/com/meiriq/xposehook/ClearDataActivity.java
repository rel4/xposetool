package com.meiriq.xposehook;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.meiriq.xposehook.adapter.AppListAdapter;
import com.meiriq.xposehook.bean.AppInfo;
import com.meiriq.xposehook.dao.ClearDataDao;
import com.meiriq.xposehook.tutorial.AppUtils;

import java.util.List;

public class ClearDataActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private ClearDataDao clearDataDao;
    //已安装的应用
    private List<AppInfo> installApps;
    //白名单应用
    private List<AppInfo> chooseApps;
    private AppListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_data);
        initActionBar();

        initView();
        initData();
    }

    private void initData() {
        installApps = AppUtils.getInstance().getInstallApps(this, AppUtils.APP_TYPE_CUSTOM);
        chooseApps = clearDataDao.getAllData();

        setAppTrueWhenLocalHave(installApps, chooseApps,false);

        adapter = new AppListAdapter(this);
        adapter.setData(installApps);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
    }


    private void initView() {
        clearDataDao = new ClearDataDao(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recycleview);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean haveApp = false;
                for (int i = 0; i < installApps.size(); i++) {
                    if (installApps.get(i).isSelect()) {
                        haveApp = true;
                        break;
                    }
                }
                if (haveApp) {
//                    Snackbar.make(view, "开始清除数据...", Snackbar.LENGTH_SHORT).show();
                    AppUtils.getInstance().clearData(installApps, ClearDataActivity.this, view);
                } else {
                    Snackbar.make(view, "没有选择程序!!!", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                saveWhiteApp();
                break;
            case R.id.action_cancel:
                boolean allSelect = adapter.isAllSelect();
                adapter.setSelect(!allSelect);
                if(allSelect)
                    item.setTitle("全选");
                else
                    item.setTitle("反选");
                adapter.notifyDataSetChanged();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.clear_data, menu);
        return true;
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
//        clearDataDao.clean();
        for (int i = installApps.size()-1; i >=0; i--) {
            AppInfo appInfo = installApps.get(i);
            if (!appInfo.isSelect()){
                clearDataDao.delete("pkgname = ?", new String[]{appInfo.getPname()});
                installApps.remove(i);
            }
        }
        if(installApps.size()>0){
            clearDataDao.addList(installApps);
        }
    }

}
