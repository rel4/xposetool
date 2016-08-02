package com.meiriq.xposehook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.meiriq.xposehook.adapter.AppListAdapter;
import com.meiriq.xposehook.bean.AppInfo;
import com.meiriq.xposehook.utils.L;
import com.meiriq.xposehook.utils.RecordFileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ru.bartwell.exfilepicker.ExFilePickerParcelObject;

public class RecordFileWhiteActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    public static final String DATA = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_file_white);
        initActionBar();

        initView();

    }

    private AppListAdapter adapter;
    private List<String> mCurLists;
    private ArrayList<String> mWhiteFileRecord;
    private List<AppInfo> mShowWhiteLists;
    private void initView() {
        mCurLists = getIntent().getStringArrayListExtra(DATA);
        mWhiteFileRecord = RecordFileUtil.getWhiteFileRecord();
        mShowWhiteLists = new ArrayList<>();
        //添加未选择数据供用户选择
        for (int i = 0; i < mCurLists.size(); i++) {
            if(!mWhiteFileRecord.contains(mCurLists.get(i))){
                AppInfo appInfo = new AppInfo();
                appInfo.setAppname(mCurLists.get(i));
                mShowWhiteLists.add(appInfo);
            }
        }
        //添加已经是白名单的数据供用户修改
        for (int i = 0; i < mWhiteFileRecord.size(); i++) {
            AppInfo appInfo = new AppInfo();
            appInfo.setIsSelect(true);
            appInfo.setAppname(mWhiteFileRecord.get(i));
            mShowWhiteLists.add(appInfo);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recycleview);
        adapter = new AppListAdapter(this);
        adapter.setData(mShowWhiteLists);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.record_white_folder, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
//            startActivity(new Intent(this,WhiteListActivity.class));
            Intent intent = new Intent(getApplicationContext(), ru.bartwell.exfilepicker.ExFilePickerActivity.class);
            startActivityForResult(intent, EX_FILE_PICKER_RESULT);
        }

        return super.onOptionsItemSelected(item);
    }
    private static final int EX_FILE_PICKER_RESULT = 0;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EX_FILE_PICKER_RESULT) {
            if (data != null) {
                ExFilePickerParcelObject object = (ExFilePickerParcelObject) data.getParcelableExtra(ExFilePickerParcelObject.class.getCanonicalName());
                if (object.count > 0) {
                    // Here is object contains selected files names and path
                    for (int i = 0; i < object.count; i++) {
                        File file = new File(object.path,object.names.get(i));
                        String absolutePath = file.getAbsolutePath();
                        if(!absolutePath.endsWith("/")){
                            absolutePath = absolutePath + "/";
                        }
                        AppInfo appInfo = new AppInfo();
                        appInfo.setIsSelect(true);
                        appInfo.setAppname(absolutePath);
                        mShowWhiteLists.add(appInfo);
                        adapter.setData(mShowWhiteLists);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            back();
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void back() {
        RecordFileUtil.deleteWhiteFile();
        for (int i = 0; i < mShowWhiteLists.size(); i++) {
            if(mShowWhiteLists.get(i).isSelect()){
                RecordFileUtil.addWhiteFileRecord(mShowWhiteLists.get(i).getAppname());
            }
            if(new File(mShowWhiteLists.get(i).getAppname()).isDirectory() && mShowWhiteLists.get(i).isSelect()){
                Log.d("unlock","白名单文件夹"+mShowWhiteLists.get(i).getAppname());
                RecordFileUtil.addWhiteFolderRecord(mShowWhiteLists.get(i).getAppname());
            }
        }
    }
}
