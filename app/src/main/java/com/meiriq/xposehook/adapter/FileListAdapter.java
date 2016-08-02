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
import com.meiriq.xposehook.bean.AppInfo;
import com.meiriq.xposehook.utils.RecordFileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tian on 15-11-27.
 */
public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.MyViewHolder>{
    private List<String> fileInfos;
    private LayoutInflater inflater;
    private Context mContext;
    private ArrayList<String> whiteFileRecord;
    ArrayList<String> whiteFolderFileRecord;
    public FileListAdapter(Context context) {
        fileInfos = new ArrayList<>();
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        whiteFileRecord = RecordFileUtil.getWhiteFileRecord();
        whiteFolderFileRecord = RecordFileUtil.getWhiteFolderFileRecord();
    }

    public void updateWhite(){
        whiteFileRecord.clear();
        whiteFolderFileRecord.clear();
        whiteFileRecord = RecordFileUtil.getWhiteFileRecord();
        whiteFolderFileRecord = RecordFileUtil.getWhiteFolderFileRecord();
    }

    public void setData(List<String> a){
        this.fileInfos.clear();
        this.fileInfos.addAll(a);
    }

    public void clearData(){
        this.fileInfos.clear();
    }

    public void addData(List<String> a){
        this.fileInfos.addAll(a);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder = new MyViewHolder(inflater.inflate(R.layout.item_filelist,parent,false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position) {
//        L.debug("position"+position);
        String s = fileInfos.get(position);
        holder.textView.setText(s);
        holder.textView.setBackground(null);
        for (String whiteFolder: whiteFolderFileRecord) {
            if(s.contains(whiteFolder)){
                holder.textView.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
            }
        }
        if(whiteFileRecord.contains(fileInfos.get(position))){
            holder.textView.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
        }
    }

    @Override
    public int getItemCount() {
        return fileInfos.size();
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
