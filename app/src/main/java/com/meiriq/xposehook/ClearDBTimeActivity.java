package com.meiriq.xposehook;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.meiriq.xposehook.dao.LocalDataDao;
import com.meiriq.xposehook.fragment.ClearDBTimeFragment;


public class ClearDBTimeActivity extends TimePickActivity implements ClearDBTimeFragment.OnFragmentInteractionListener{

    LocalDataDao mLocalDataDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_dbtime);
        initActionBar();

        mLocalDataDao = new LocalDataDao(this);
        if(savedInstanceState == null){
            getFragmentManager().beginTransaction().add(R.id.container,ClearDBTimeFragment.newInstance()).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_clear_dbtime, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDataClear(String date) {
        int count = mLocalDataDao.clearWhichDay(date);
        Toast.makeText(this,count+"条数据修改",Toast.LENGTH_SHORT).show();
    }
}
