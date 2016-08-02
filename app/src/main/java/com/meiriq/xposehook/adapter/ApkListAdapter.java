package com.meiriq.xposehook.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meiriq.xposehook.R;
import com.meiriq.xposehook.bean.ApkInfo;
import com.meiriq.xposehook.bean.AppInfo;
import com.meiriq.xposehook.utils.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tian on 15-11-27.
 */
public class ApkListAdapter extends RecyclerView.Adapter<ApkListAdapter.MyViewHolder>{
    private List<ApkInfo> apkInfos ;
    private LayoutInflater inflater;
    public ApkListAdapter(Context context) {
        apkInfos = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
    }

    public void setData(List<ApkInfo> apkInfos){
        this.apkInfos.clear();
        this.apkInfos.addAll(apkInfos);
        L.debug(this.apkInfos.size()+"");

    }

    public void addData(List<ApkInfo> a){
        this.apkInfos.addAll(a);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder = new MyViewHolder(inflater.inflate(R.layout.item_uninstall_applist,parent,false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position) {
//        L.debug("position"+position);
        holder.textView.setText(apkInfos.get(position).getName());
        holder.checkBox.setChecked(apkInfos.get(position).isSelect());
        holder.checkBox.setClickable(false);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apkInfos.get(position).setIsSelect(!holder.checkBox.isChecked());
                holder.checkBox.setChecked(apkInfos.get(position).isSelect());
            }
        });
    }

    @Override
    public int getItemCount() {
        return apkInfos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        AppCompatCheckBox checkBox;
        AppCompatTextView textView;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            checkBox = (AppCompatCheckBox) itemView.findViewById(R.id.accb_appchoose);
            textView = (AppCompatTextView) itemView.findViewById(R.id.actv_appname);
            cardView = (CardView) itemView.findViewById(R.id.cv_appinfo);
        }
    }
}
