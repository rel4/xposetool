package com.meiriq.xposehook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.meiriq.xposehook.adapter.FileListAdapter;
import com.meiriq.xposehook.utils.L;
import com.meiriq.xposehook.utils.RecordFileUtil;
import com.meiriq.xposehook.utils.XposeUtil;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RecordAppFileActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener{

    private static final int REQ_CHOOSEAPP = 0x1 << 1;
    private static final int REQ_FILEWHITE = 0x1 << 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_app_file);
        initActionBar();



        initView();
    }

    private RecyclerView recyclerView;
    private EditText editText;
    private AppCompatCheckBox checkBox;
    /**
     * 存储要监听的程序包名
     */
    private List<String> pkgList;
    List<String> lists;
    private void initView() {
        TextInputLayout textInputLayout = (TextInputLayout) findViewById(R.id.til_appinfo);
        textInputLayout.setHint("请选择应用");
        editText = textInputLayout.getEditText();
        editText.setEnabled(false);
        recyclerView = (RecyclerView) findViewById(R.id.rv_recycleview);

        XposeUtil.initConfigMap();
        checkBox = (AppCompatCheckBox) findViewById(R.id.accb_appchoose);
        checkBox.setOnCheckedChangeListener(this);
        checkBox.setChecked(XposeUtil.configMap.optBoolean(XposeUtil.FileRecordPackageNameSwitch));

        RecordFileUtil.clearFileMap();
        pkgList = mapKey2List(XposeUtil.configMap.optString(XposeUtil.FileRecordPackageName));
        for (int i = 0; i <pkgList.size(); i++) {
            RecordFileUtil.getFileRecord(pkgList.get(i));
        }
        Set<String> strings = RecordFileUtil.fileMap.keySet();
        lists = new ArrayList<>();
        lists.addAll(strings);

        adapter = new FileListAdapter(this);
        adapter.setData(lists);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);



        setEdit();
    }

    private List<String> mapKey2List(String map){
        List<String> list = new ArrayList<>();
        if(TextUtils.isEmpty(map) || map.length() == 2){
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

    private List<String> mapValue2List(String map){
        L.debug(map);
        List<String> list = new ArrayList<>();
        if(TextUtils.isEmpty(map) || map.length() == 2){
            return list;
        }
        String[] split = map.split(",");
        if(split == null)
            return list;
        for (int i = 0; i < split.length; i++) {
            L.debug(split[i]);
            String pkg = split[i].split("=")[1];
            if(pkg.endsWith("}"))
                pkg = pkg.substring(0,pkg.length() - 1);
            list.add(pkg.trim());
        }
        return list;
    }


    private FileListAdapter adapter;
    private void setEdit() {
        String recordappname = "";
        List<String>Strings = mapValue2List(XposeUtil.configMap.optString(XposeUtil.FileRecordPackageName));
        for (int i = 0; i < Strings.size(); i++) {
            recordappname = recordappname + Strings.get(i) + ",";
        }
        editText.setText(recordappname);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.record_app_file, menu);
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
            delete();
        }else if(id == R.id.action_add){
            startActivityForResult(new Intent(this,RecordAppFileChooseActivity.class),REQ_CHOOSEAPP);
        }else if(id == R.id.action_white){
            Intent intent = new Intent(this,RecordFileWhiteActivity.class);
            intent.putStringArrayListExtra(RecordFileWhiteActivity.DATA, (ArrayList<String>) lists);
            startActivityForResult(intent, REQ_FILEWHITE);
        }

        return super.onOptionsItemSelected(item);
    }

    private void delete() {
        RecordFileUtil.deleteFile();
        for (int i = 0; i < pkgList.size(); i++) {

            boolean b = RecordFileUtil.clearFileRecord(pkgList.get(i));
            L.debug("目录删除－－－－－－－－－－－"+b+pkgList.get(i));
        }
        Toast.makeText(this,"删除成功",Toast.LENGTH_SHORT).show();
        RecordFileUtil.fileMap.clear();
        lists.clear();
        adapter.clearData();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        XposeUtil.saveConfigMap();
        Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_CHOOSEAPP){
            setEdit();
        }else if(requestCode == REQ_FILEWHITE){
            adapter.updateWhite();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            try {
                XposeUtil.configMap.put(XposeUtil.FileRecordPackageNameSwitch,true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                XposeUtil.configMap.put(XposeUtil.FileRecordPackageNameSwitch,false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
