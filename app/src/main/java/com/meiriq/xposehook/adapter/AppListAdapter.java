package com.meiriq.xposehook.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.meiriq.xposehook.R;
import com.meiriq.xposehook.bean.AppInfo;
import com.meiriq.xposehook.utils.DialogUtil;
import com.meiriq.xposehook.utils.L;
import com.meiriq.xposehook.utils.RecordFileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tian on 15-11-27.
 */
public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.MyViewHolder>{
    private List<AppInfo> appInfos ;
    private LayoutInflater inflater;
    private Context mContext;
    ArrayList<String> whiteFolderFileRecord;
    public AppListAdapter(Context context) {
        appInfos = new ArrayList<>();
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        whiteFolderFileRecord = RecordFileUtil.getWhiteFolderFileRecord();
    }

    public void setData(List<AppInfo> a){
        this.appInfos.clear();
        this.appInfos.addAll(a);
    }

    public void updateWhiteFolderRecord(){
        whiteFolderFileRecord.clear();
        whiteFolderFileRecord = RecordFileUtil.getWhiteFolderFileRecord();
    }

    public void addData(List<AppInfo> a){
        this.appInfos.addAll(a);
    }

    public void setSelect(boolean select){
        int size = appInfos.size();
        for (int i = 0; i < size; i++) {
            appInfos.get(i).setIsSelect(select);
        }
    }

    public boolean isAllSelect(){
        boolean select = true;
        for(int j = 0; j < appInfos.size(); j++){
            if(!appInfos.get(j).isSelect()){
                select = false;
            }
        }
        return select;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder = new MyViewHolder(inflater.inflate(R.layout.item_uninstall_applist,parent,false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position) {
//        L.debug("position"+position);
        final AppInfo appInfo = appInfos.get(position);
        final String appname = appInfo.getAppname();
        int indexOf = appname.lastIndexOf("/");
        holder.textView.setBackground(null);
        if(indexOf != -1){
            String floder = appname.substring(0, appname.lastIndexOf("/"));
            for (int i = 0; i < whiteFolderFileRecord.size(); i++) {
                if (floder.equals(whiteFolderFileRecord.get(i))){
                Log.d("unlock", "folder" + floder + "==" + whiteFolderFileRecord.get(i));
                    holder.textView.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
                }
            }
        }
        holder.textView.setText(appInfo.getAppname());
        //符合白名单文件夹的文件全部变色
        holder.checkBox.setChecked(appInfo.isSelect());
        holder.checkBox.setClickable(false);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appInfos.get(position).setIsSelect(!holder.checkBox.isChecked());
                holder.checkBox.setChecked(appInfo.isSelect());
            }
        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String ask = "";
                final String testfloder = appname.substring(0, appname.lastIndexOf("/"));
                boolean saveOrDelete = true;
                //判断是否已经在白名单文件夹中
                for (int i = 0; i < whiteFolderFileRecord.size(); i++) {
                    if(!testfloder.equals(whiteFolderFileRecord.get(i))){
                        saveOrDelete = true;
                    }else {
                        saveOrDelete = false;
                        break;//有了就要跳出来，不然白名单大于1个时，会一直是true
                    }
                }
                if(saveOrDelete){
                    ask = "将文件夹%s录入白名单?";
                    DialogUtil.showChooseDialog(mContext, String.format(ask, testfloder), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            whiteFolderFileRecord.add(testfloder);
                            RecordFileUtil.addWhiteFolderRecord(testfloder);
                            updateWhiteFolderRecord();
                            notifyDataSetChanged();
                        }
                    });
                }else {
                    ask = "将文件夹%s从白名单剔除?";
                    DialogUtil.showChooseDialog(mContext, String.format(ask, testfloder), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("unlock","whiteFolderFileRecord.toString()"+whiteFolderFileRecord.toString());
                            whiteFolderFileRecord.remove(testfloder);
                            Log.d("unlock", "whiteFolderFileRecord.toString()"+whiteFolderFileRecord.toString());
                            RecordFileUtil.deleteWhiteFolderFile();
                            RecordFileUtil.addWhiteFolderRecord(whiteFolderFileRecord);
                            updateWhiteFolderRecord();
                            notifyDataSetChanged();
                        }
                    });
                }


                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return appInfos.size();
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
