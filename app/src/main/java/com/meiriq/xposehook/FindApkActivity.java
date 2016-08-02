package com.meiriq.xposehook;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.meiriq.xposehook.adapter.ApkListAdapter;
import com.meiriq.xposehook.adapter.AppListAdapter;
import com.meiriq.xposehook.bean.ApkInfo;
import com.meiriq.xposehook.bean.AppInfo;
import com.meiriq.xposehook.dao.ClearDataDao;
import com.meiriq.xposehook.dao.WhiteApkDao;
import com.meiriq.xposehook.tutorial.AppUtils;
import com.meiriq.xposehook.utils.L;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class FindApkActivity extends BaseActivity {

    private static final int REQ_APKWHITE = 0x1;
    private RecyclerView mRecyclerView;
    private ApkListAdapter adapter;
    private List<ApkInfo> allApkInfo;
    private boolean isFind = false;
    private List<ApkInfo> deleteApkInfo;
    private List<File> fileList = new ArrayList<>();

    public static final FileFilter APK_FILTER = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            return (pathname.isDirectory() || pathname.getName().contains(".apk")) ;
        }
    } ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_apk);
        initActionBar();


        initView();
        initData();
    }

    private void initData() {

    }


    private WhiteApkDao whiteApkDao;

    private void initView() {
        allApkInfo = new ArrayList<>();
        deleteApkInfo = new ArrayList<>();
        whiteApkDao = new WhiteApkDao(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFind){
                    isFind = !isFind;
                    Snackbar.make(view, "开始查找...", Snackbar.LENGTH_SHORT).show();
                    fileList.clear();
                    allApkInfo.clear();
                    deleteApkInfo.clear();
                    MyAsyncTask asyncTask = new MyAsyncTask();
                    asyncTask.execute(0);
                }
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recycleview);
        adapter = new ApkListAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
    }

    private void reSearch(File file){
        File[] files = file.listFiles(APK_FILTER);
        if(files == null){
            return;
        }
        for (int i = 0; i < files.length; i++) {
            if(files[i].isDirectory()){
                reSearch(files[i]);
            }else{
                this.fileList.add(files[i]);
            }
        }
    }

    class MyAsyncTask extends AsyncTask<Integer ,Integer ,List<ApkInfo>>{


        @Override
        protected List<ApkInfo> doInBackground(Integer... params) {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath();

            reSearch(new File(path));
            for (int i = 0; i < fileList.size(); i++) {
                ApkInfo apkInfo = new ApkInfo();
                File file = fileList.get(i);
                apkInfo.setIsSelect(true);
                apkInfo.setName(file.getAbsolutePath());
                allApkInfo.add(apkInfo);
                deleteApkInfo.add(apkInfo);
            }
            return allApkInfo;
        }

        @Override
        protected void onPostExecute(List<ApkInfo> apkInfos) {
            super.onPostExecute(apkInfos);
            isFind = false;
            Toast.makeText(FindApkActivity.this,"查找成功",Toast.LENGTH_SHORT).show();
            List<ApkInfo> allData = whiteApkDao.getAllData();
            for (int i = 0; i < allData.size(); i++) {
                for (int j = deleteApkInfo.size() - 1; j >= 0; j--) {
                    if(allData.get(i).getName().equals(deleteApkInfo.get(j).getName())){
                        deleteApkInfo.remove(j);
                    }
                }
            }
            adapter.setData(deleteApkInfo);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.findapk, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            deleteAllApk();
        }else if (id == R.id.action_white) {
            Intent intent = new Intent(this, FindApkWhiteActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("apklist", (ArrayList<? extends Parcelable>) allApkInfo);
            intent.putExtras(bundle);
            startActivityForResult(intent, REQ_APKWHITE);
        }


        return super.onOptionsItemSelected(item);
    }

    private void deleteAllApk() {
        int count = 0;
        for (int i = deleteApkInfo.size() - 1; i >=0; i--) {
            if(deleteApkInfo.get(i).isSelect()){
                File apk = new File(deleteApkInfo.get(i).getName());
                if(apk.delete()){
                    count ++;
                    deleteApkInfo.remove(i);
                }
            }
        }
        for (int i = allApkInfo.size() - 1; i >=0; i--) {
            if(allApkInfo.get(i).isSelect()){
                allApkInfo.remove(i);
            }
        }
        adapter.setData(deleteApkInfo);
        adapter.notifyDataSetChanged();
        Toast.makeText(this,"共删除"+count+"个apk...",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_APKWHITE && resultCode == RESULT_OK ){
            ArrayList<ApkInfo> whiteapks = data.getExtras().getParcelableArrayList("white");
            deleteApkInfo.clear();
            for (int i = 0; i < allApkInfo.size(); i++) {
                deleteApkInfo.add(allApkInfo.get(i));
            }
            setApkTrueWhenLocalHave(deleteApkInfo, whiteapks, true);
            adapter.setData(deleteApkInfo);
            adapter.notifyDataSetChanged();
        }
    }
}
