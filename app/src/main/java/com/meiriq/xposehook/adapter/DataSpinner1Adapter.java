package com.meiriq.xposehook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.meiriq.xposehook.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tian on 15-12-8.
 */
public class DataSpinner1Adapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater inflater;
    private List<String> channels;

    private DataSpinner1Adapter(){};

    public DataSpinner1Adapter(Context context){
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        channels = new ArrayList<>();
    }

    public void setData(List<String> datas){
        channels.addAll(datas);
    }
    public void setData(String [] datas){
        channels.addAll(Arrays.asList(datas));
    }

    public void clear(){
        channels.clear();
    }

    @Override
    public int getCount() {
        return channels.size();
    }

    @Override
    public Object getItem(int position) {
        return channels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.spinner_layout_head1, parent, false);
        }
        ((TextView) convertView).setText(channels.get(position));

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.spinner_layout_item, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setText(channels.get(position));

//        ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
//        icon.setImageResource(spinnerIcons[position]);

//        if (checkPos == position) {
//            convertView.setBackgroundColor(Color.parseColor("#ffeeeeee"));
//        } else {
//            convertView.setBackgroundColor(Color.parseColor("#00000000"));
//        }
        return convertView;
    }
}
