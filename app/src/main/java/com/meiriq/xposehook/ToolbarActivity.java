package com.meiriq.xposehook;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/**
 * Created by tian on 15-11-26.
 */
public class ToolbarActivity extends AppCompatActivity{

    private Toolbar toolbar;
    public void initActionBar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(this, R.style.TextAppearance_AppCompat_Medium);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                back();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void back() {

    }

}
