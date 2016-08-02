package com.meiriq.xposehook.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.meiriq.xposehook.LocalDataActivity;
import com.meiriq.xposehook.LocalDataDetailActivity;
import com.meiriq.xposehook.R;
import com.meiriq.xposehook.bean.DataStatus;
import com.meiriq.xposehook.utils.RecordFileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tian on 15-11-27.
 */
public class LocalDataDirectoryAdapter extends RecyclerView.Adapter<LocalDataDirectoryAdapter.MyViewHolder>{
    private List<DataStatus> localDataDirectorys;
    private LayoutInflater inflater;
    private Context mContext;
    public LocalDataDirectoryAdapter(Context context) {
        localDataDirectorys = new ArrayList<>();
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
    }


    public void setData(List<DataStatus> datas){
        this.localDataDirectorys.clear();
        this.localDataDirectorys.addAll(datas);
    }

    public void clearData(){
        this.localDataDirectorys.clear();
    }

    public void addData(List<DataStatus> a){
        this.localDataDirectorys.addAll(a);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder = new MyViewHolder(inflater.inflate(R.layout.item_localdata_directory,parent,false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position) {
//        L.debug("position"+position);
        holder.textView.setText(localDataDirectorys.get(position).getDate());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, LocalDataDetailActivity.class);

                intent.putExtra(LocalDataDetailActivity.TIME, localDataDirectorys.get(position).getDate());
                ((LocalDataActivity) mContext).startActivityForResult(intent, LocalDataActivity.REQ_DETAIL_DATA);
            }
        });
        holder.actv_count.setText(localDataDirectorys.get(position).getCount()+"条");
    }

    @Override
    public int getItemCount() {
        return localDataDirectorys.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        AppCompatCheckBox checkBox;
        AppCompatTextView textView;
        AppCompatTextView actv_count;
        LinearLayout cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (AppCompatTextView) itemView.findViewById(R.id.actv_filename);
            actv_count = (AppCompatTextView) itemView.findViewById(R.id.actv_count);
            cardView = (LinearLayout) itemView.findViewById(R.id.cv_appinfo);
        }
    }
}
