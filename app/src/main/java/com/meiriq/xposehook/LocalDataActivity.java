package com.meiriq.xposehook;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.meiriq.xposehook.adapter.LocalDataDirectoryAdapter;
import com.meiriq.xposehook.bean.DataStatus;
import com.meiriq.xposehook.dao.LocalDataDao;
import com.meiriq.xposehook.utils.DateUtil;
import com.meiriq.xposehook.utils.L;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LocalDataActivity extends BaseActivity {

    public static final int REQ_DETAIL_DATA = 0x1<<1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_data);
        initActionBar();

        initView();

        deleteLocalData();

    }

    private RecyclerView mRecyclerView;
    private LocalDataDao mLocalDataDao;
    private LocalDataDirectoryAdapter adapter;
    ArrayList<DataStatus> mDataStatus;
    private void initView() {
        mLocalDataDao = new LocalDataDao(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recycleview);
        adapter = new LocalDataDirectoryAdapter(this);
        mDataStatus = getDataStatus();
        adapter.setData(mDataStatus);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
    }

    //删除30天前的数据
    private void deleteLocalData(){
        long timeMillis = DateUtil.getTimeMillis(30);
        mLocalDataDao.delete("detailtime < ?",new String[]{String.valueOf(timeMillis)});
    }


    private ArrayList<DataStatus> getDataStatus(){
        Cursor cursor = mLocalDataDao.queryDataType(new String[]{DateUtil.getCurDate()});
        ArrayList<DataStatus> dataStatuses = DataStatus.parseCursor2List(cursor);
        return dataStatuses;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_DETAIL_DATA && resultCode == Activity.RESULT_OK){
            setResult(RESULT_OK,data);
            finish();
        }
    }
}
