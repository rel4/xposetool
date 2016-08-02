package com.meiriq.xposehook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.meiriq.xposehook.adapter.ApkListAdapter;
import com.meiriq.xposehook.bean.ApkInfo;
import com.meiriq.xposehook.dao.WhiteApkDao;

import java.util.ArrayList;
import java.util.List;

public class FindApkWhiteActivity extends BaseActivity {

    private ArrayList<ApkInfo> allApkInfos;
    private RecyclerView mRecyclerView;
    private ApkListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_apk_white);
        initActionBar();

        initView();

        initData();

    }

    private void initData() {
        allApkInfos = getIntent().getExtras().getParcelableArrayList("apklist");
        adapter.setData(allApkInfos);

        List<ApkInfo> whiteData = whiteApkDao.getAllData();
        for (int i = 0; i < allApkInfos.size(); i++) {
            allApkInfos.get(i).setIsSelect(false);
            for (int j = 0; j < whiteData.size(); j++) {
                if(allApkInfos.get(i).getName().equals(whiteData.get(j).getName())){
                    allApkInfos.get(i).setIsSelect(true);
                    break;
                }
            }
        }
    }

    private WhiteApkDao whiteApkDao;
    private void initView() {
        whiteApkDao = new WhiteApkDao(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recycleview);
        adapter = new ApkListAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                saveWhiteApk();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            saveWhiteApk();
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void saveWhiteApk() {
        if(allApkInfos.size()>0)
            whiteApkDao.clean();
        for (int i = allApkInfos.size()-1; i >=0; i--) {
            ApkInfo apkInfo = allApkInfos.get(i);
            if (!apkInfo.isSelect()){
                allApkInfos.remove(i);
            }
        }
        if(allApkInfos.size()>0){
            whiteApkDao.addList(allApkInfos);
        }
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("white",allApkInfos);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
    }

}
