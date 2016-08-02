package com.meiriq.xposehook.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meiriq.xposehook.LocalDataActivity;
import com.meiriq.xposehook.LocalDataDetailActivity;
import com.meiriq.xposehook.R;
import com.meiriq.xposehook.bean.DataInfo;
import com.meiriq.xposehook.dao.LocalDataDao;
import com.meiriq.xposehook.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tian on 15-11-27.
 */
public class LocalDataDetailAdapter extends RecyclerView.Adapter<LocalDataDetailAdapter.MyViewHolder>{
    private List<DataInfo> localDataDetail;
    private LayoutInflater inflater;
    private Context mContext;
    public LocalDataDetailAdapter(Context context) {
        localDataDetail = new ArrayList<>();
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
    }


    public void setData(List<DataInfo> datas){
        this.localDataDetail.clear();
        this.localDataDetail.addAll(datas);
    }

    public void clearData(){
        this.localDataDetail.clear();
    }

    public void addData(List<DataInfo> a){
        this.localDataDetail.addAll(a);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder = new MyViewHolder(inflater.inflate(R.layout.item_localdata_detail,parent,false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position) {
        holder.textView.setText(DateUtil.getCurTime(localDataDetail.get(position).getDetailTime()));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalDataDao localDataDao = new LocalDataDao(mContext);
                DataInfo dataInfo = localDataDetail.get(position);
                dataInfo.setUseTime(DateUtil.getCurDate());
                localDataDao.update(dataInfo,"imei = ?", new String[]{dataInfo.getDeviceId()});
                Intent intent = new Intent();
                intent.putExtra(LocalDataDetailActivity.DATA,dataInfo);
                ((LocalDataDetailActivity) mContext).setResult(Activity.RESULT_OK,intent);
                ((LocalDataDetailActivity) mContext).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return localDataDetail.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        AppCompatCheckBox checkBox;
        AppCompatTextView textView;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (AppCompatTextView) itemView.findViewById(R.id.actv_filename);
        }
    }
}
