package com.meiriq.xposehook;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.meiriq.xposehook.adapter.LocalDataDetailAdapter;
import com.meiriq.xposehook.adapter.LocalDataDirectoryAdapter;
import com.meiriq.xposehook.bean.DataInfo;
import com.meiriq.xposehook.bean.DataStatus;
import com.meiriq.xposehook.bean.util.SetDataUtil;
import com.meiriq.xposehook.dao.LocalDataDao;
import com.meiriq.xposehook.utils.DateUtil;
import com.meiriq.xposehook.utils.L;

import java.util.ArrayList;

public class LocalDataDetailActivity extends BaseActivity {

    public static final String DATA = "data";
    public static final String TIME = "time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_data_detail);
        initActionBar();

        initView();
    }

    private RecyclerView mRecyclerView;
    private LocalDataDao mLocalDataDao;
    private LocalDataDetailAdapter adapter;
    private ArrayList<DataInfo> mDataStatus;
    String time;
    private void initView() {
        time = getIntent().getStringExtra(TIME);

        mLocalDataDao = new LocalDataDao(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recycleview);
        adapter = new LocalDataDetailAdapter(this);
        mDataStatus = getDataStatus();
        adapter.setData(mDataStatus);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);

    }

    private ArrayList<DataInfo> getDataStatus(){
        L.debug(time+"--"+DateUtil.getCurDate());
        Cursor cursor = mLocalDataDao.queryDateTimeAll(new String[]{time});
        ArrayList<DataInfo> dataInfos = SetDataUtil.parseCursor2List(cursor);
        return dataInfos;
    }

}
