package com.meiriq.xposehook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.meiriq.xposehook.adapter.AppListAdapter;
import com.meiriq.xposehook.bean.AppInfo;
import com.meiriq.xposehook.bean.ConfigHelper;
import com.meiriq.xposehook.dao.LocalDataDao;
import com.meiriq.xposehook.dao.WhiteUninstallDao;
import com.meiriq.xposehook.net.control.DataService;
import com.meiriq.xposehook.tutorial.AppUtils;

import java.util.List;

public class UninstallActivity extends BaseActivity {

    private static final int REQ_UNINSTALL = 1001;

    private RecyclerView mRecyclerView;
    //已安装的应用
    private List<AppInfo> installApps;
    //白名单应用
    private List<AppInfo> whiteApps;
    private AppListAdapter adapter;
    private WhiteUninstallDao whiteUninstallDao;

    private DataService dataService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uninstall);
        initActionBar();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        dataService = new DataService(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                boolean haveApp = false;
                for (int i = 0; i < installApps.size(); i++) {
                    if (installApps.get(i).isSelect()) {
                        haveApp = true;
                        break;
                    }
                }
                if (haveApp) {
                    Snackbar.make(view, "开始卸载程序...", Snackbar.LENGTH_SHORT).show();
                    AppUtils.getInstance().uninstall(installApps, UninstallActivity.this, view);
                } else {
//                    Snackbar.make(view, "没有选择程序!!!", Snackbar.LENGTH_SHORT).show();
//                    dataService.sendDataDeprecated(UninstallActivity.this, ConfigHelper.loadDataInfo(UninstallActivity.this).getId());
//                    LocalDataDao mLocalDataDao = new LocalDataDao(UninstallActivity.this);
//                    int delete = mLocalDataDao.delete("id = ?", new String[]{ConfigHelper.loadDataInfo(UninstallActivity.this).getId()});
                }
            }
        });

        initView();
        initData();
    }


    private void initData() {
        installApps = AppUtils.getInstance().getInstallApps(this, AppUtils.APP_TYPE_CUSTOM);
        whiteApps = whiteUninstallDao.getAllData();

        setAppTrueWhenLocalHave(installApps, whiteApps);
        adapter = new AppListAdapter(this);
        adapter.setData(installApps);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);

        //卸载完成回调
        AppUtils.getInstance().setCallBackListener(new AppUtils.CallBack() {
            @Override
            public void unInstall() {
                installApps = AppUtils.getInstance().getInstallApps(UninstallActivity.this, AppUtils.APP_TYPE_CUSTOM);
//                updateCanUninstallApp();
                setAppTrueWhenLocalHave(installApps, whiteApps);
                adapter.setData(installApps);
                adapter.notifyDataSetChanged();
            }
        });
    }

//    private void updateCanUninstallApp() {
//        for (AppInfo whiteAppInfo :
//                whiteApps) {
//            for (int i = installApps.size()-1; i >= 0; i--) {
//
//                if (installApps.get(i).getPname().equals(whiteAppInfo.getPname())){
//                    installApps.get(i).setIsSelect(true);
//                    installApps.remove(i);
//                    break;
//                }
//            }
//        }
//    }


    private void initView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recycleview);
        whiteUninstallDao = new WhiteUninstallDao(this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.uninstall, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_all) {
            boolean chooseAll = false;
            for(int j = 0; j < installApps.size(); j++){
                if(!installApps.get(j).isSelect()){
                    chooseAll = true;
                    break;
                }
            }
            if(!chooseAll)
                item.setTitle("全选");
            else
                item.setTitle("反选");
            for (int i = 0; i < installApps.size(); i++) {
                installApps.get(i).setIsSelect(chooseAll);
                adapter.setData(installApps);
                adapter.notifyDataSetChanged();
            }
        }else if(id ==R.id.action_white){
            Intent intent = new Intent(this,UninstallWhiteActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putParcelableArrayList("installapp",installApps);
//            intent.putExtras(bundle);
            startActivityForResult(intent, REQ_UNINSTALL);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_UNINSTALL && resultCode == RESULT_OK){
            installApps = AppUtils.getInstance().getInstallApps(this, AppUtils.APP_TYPE_CUSTOM);
            whiteApps = whiteUninstallDao.getAllData();
//            updateCanUninstallApp();
            setAppTrueWhenLocalHave(installApps, whiteApps);
            adapter.setData(installApps);
            adapter.notifyDataSetChanged();
        }
    }
}
