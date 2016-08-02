package com.meiriq.xposehook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.meiriq.xposehook.bean.DataKeepStatus;
import com.meiriq.xposehook.utils.L;
import com.meiriq.xposehook.view.Linera;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tian on 16-1-19.
 */
public class DataKeepStatusActivity extends BaseActivity{

    private Linera linera;
    public static final int REQ_DATA_KEEP = 0x01<<1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_keep_status);
        initActionBar();

        linera = (Linera) findViewById(R.id.data_status);
        ArrayList<DataKeepStatus> dataKeepStatuses = (ArrayList<DataKeepStatus>) DataKeepStatus.loadDataKeepStatus(this);
        if(dataKeepStatuses != null)
            linera.addItemList(dataKeepStatuses);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveData();
    }

    @Override
    public void back() {
        super.back();
        saveData();
    }

    private void saveData() {
        ArrayList<DataKeepStatus> list = linera.getList();
        DataKeepStatus.saveDataKeepStatus(this, list);
        Toast.makeText(this,"保持成功",Toast.LENGTH_SHORT).show();
    }
}
