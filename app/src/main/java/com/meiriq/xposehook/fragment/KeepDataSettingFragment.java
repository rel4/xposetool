package com.meiriq.xposehook.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.meiriq.xposehook.R;
import com.meiriq.xposehook.bean.SetDay;

/**
 * Created by Administrator on 2016/3/3.
 */
public class KeepDataSettingFragment extends DialogFragment implements View.OnClickListener {


    public static KeepDataSettingFragment newInstance(SetDay setDay) {
        KeepDataSettingFragment f = new KeepDataSettingFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putSerializable("setday", setDay);
        f.setArguments(args);

        return f;
    }

    private OnDataCallBack mListener;
    private SetDay mSetDay;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSetDay = (SetDay) getArguments().getSerializable("setday");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("留存天数设置");
        getDialog().setCanceledOnTouchOutside(false);
        setCancelable(false);
        View view = inflater.inflate(R.layout.fragment_datakeep_setting, container, false);
        initView(view);
        return view;
    }

    AppCompatEditText day2;
    AppCompatEditText day3;
    AppCompatEditText day4;
    AppCompatEditText day5;
    AppCompatEditText day6;
    AppCompatEditText day7;
    AppCompatEditText day8;
    AppCompatEditText day9;
    AppCompatEditText day10;
    AppCompatEditText day11;
    AppCompatEditText day12;
    AppCompatEditText day13;
    AppCompatEditText day14;
    AppCompatEditText day15;
    AppCompatEditText day16;
    AppCompatEditText day17;
    AppCompatEditText day18;
    AppCompatEditText day19;
    AppCompatEditText day20;
    AppCompatEditText day21;
    AppCompatEditText day22;
    AppCompatEditText day23;
    AppCompatEditText day24;
    AppCompatEditText day25;
    AppCompatEditText day26;
    AppCompatEditText day27;
    AppCompatEditText day28;
    AppCompatEditText day29;
    AppCompatEditText day30;
    AppCompatEditText day31;
    private void initView(View view) {

        day2 = (AppCompatEditText) view.findViewById(R.id.et_day2);
        day3 = (AppCompatEditText) view.findViewById(R.id.et_day3);
        day4 = (AppCompatEditText) view.findViewById(R.id.et_day4);
        day5 = (AppCompatEditText) view.findViewById(R.id.et_day5);
        day6 = (AppCompatEditText) view.findViewById(R.id.et_day6);
        day7 = (AppCompatEditText) view.findViewById(R.id.et_day7);
        day8 = (AppCompatEditText) view.findViewById(R.id.et_day8);
        day9 = (AppCompatEditText) view.findViewById(R.id.et_day9);
        day10 = (AppCompatEditText) view.findViewById(R.id.et_day10);
        day11 = (AppCompatEditText) view.findViewById(R.id.et_day11);
        day12 = (AppCompatEditText) view.findViewById(R.id.et_day12);
        day13 = (AppCompatEditText) view.findViewById(R.id.et_day13);
        day14 = (AppCompatEditText) view.findViewById(R.id.et_day14);
        day15 = (AppCompatEditText) view.findViewById(R.id.et_day15);
        day16 = (AppCompatEditText) view.findViewById(R.id.et_day16);
        day17 = (AppCompatEditText) view.findViewById(R.id.et_day17);
        day18 = (AppCompatEditText) view.findViewById(R.id.et_day18);
        day19 = (AppCompatEditText) view.findViewById(R.id.et_day19);
        day20 = (AppCompatEditText) view.findViewById(R.id.et_day20);
        day21 = (AppCompatEditText) view.findViewById(R.id.et_day21);
        day22 = (AppCompatEditText) view.findViewById(R.id.et_day22);
        day23 = (AppCompatEditText) view.findViewById(R.id.et_day23);
        day24 = (AppCompatEditText) view.findViewById(R.id.et_day24);
        day25 = (AppCompatEditText) view.findViewById(R.id.et_day25);
        day26 = (AppCompatEditText) view.findViewById(R.id.et_day26);
        day27 = (AppCompatEditText) view.findViewById(R.id.et_day27);
        day28 = (AppCompatEditText) view.findViewById(R.id.et_day28);
        day29 = (AppCompatEditText) view.findViewById(R.id.et_day29);
        day30 = (AppCompatEditText) view.findViewById(R.id.et_day30);
        day31 = (AppCompatEditText) view.findViewById(R.id.et_day31);

        day2.setText(mSetDay.getDay2());
        day3.setText(mSetDay.getDay3());
        day4.setText(mSetDay.getDay4());
        day5.setText(mSetDay.getDay5());
        day6.setText(mSetDay.getDay6());
        day7.setText(mSetDay.getDay7());
        day8.setText(mSetDay.getDay8());
        day9.setText(mSetDay.getDay9());
        day10.setText(mSetDay.getDay10());
        day11.setText(mSetDay.getDay11());
        day12.setText(mSetDay.getDay12());
        day13.setText(mSetDay.getDay13());
        day14.setText(mSetDay.getDay14());
        day15.setText(mSetDay.getDay15());
        day16.setText(mSetDay.getDay16());
        day17.setText(mSetDay.getDay17());
        day18.setText(mSetDay.getDay18());
        day19.setText(mSetDay.getDay19());
        day20.setText(mSetDay.getDay20());
        day21.setText(mSetDay.getDay21());
        day22.setText(mSetDay.getDay22());
        day23.setText(mSetDay.getDay23());
        day24.setText(mSetDay.getDay24());
        day25.setText(mSetDay.getDay25());
        day26.setText(mSetDay.getDay26());
        day27.setText(mSetDay.getDay27());
        day28.setText(mSetDay.getDay28());
        day29.setText(mSetDay.getDay29());
        day30.setText(mSetDay.getDay30());
        day31.setText(mSetDay.getDay31());



        Button mOk = (Button) view.findViewById(R.id.ok);
        Button mCancel = (Button) view.findViewById(R.id.cancel);
        mOk.setOnClickListener(this);
        mCancel.setOnClickListener(this);

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnDataCallBack) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        Log.d("unlock","detach");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("unlock", "onDestroy");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.ok:
                SetDay setDay = new SetDay(getDayString(day2),getDayString(day3),getDayString(day4),getDayString(day5),getDayString(day6),getDayString(day7),getDayString(day8),getDayString(day9),getDayString(day10),
                        getDayString(day11),getDayString(day12),getDayString(day13),getDayString(day14),getDayString(day15),getDayString(day16),getDayString(day17),getDayString(day18),getDayString(day19),getDayString(day20),
                        getDayString(day21),getDayString(day22),getDayString(day23),getDayString(day24),getDayString(day25),getDayString(day26),getDayString(day27),getDayString(day28),getDayString(day29),getDayString(day30),getDayString(day31));
                mListener.onKeppData(setDay);
                dismiss();
                break;
            case R.id.cancel:
                dismiss();
                break;
        }

    }

    public interface OnDataCallBack {
        // TODO: Update argument type and name
        public void onKeppData(SetDay setDay);

    }

    /**
     * 根据参数获取指定文本框的数据
     * @param
     * @return
     */
    private String getDayString(AppCompatEditText appCompatEditText){

        String result;
        result = appCompatEditText.getText().toString().trim();
        if(result == null){
            result = "";
        }
        return result;
    }
}
