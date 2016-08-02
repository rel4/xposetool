package com.meiriq.xposehook.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.meiriq.xposehook.R;
import com.meiriq.xposehook.bean.DataKeepStatus;
import com.meiriq.xposehook.utils.L;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by tian on 16-1-19.
 */
public class Linera extends LinearLayout implements View.OnClickListener ,TimePickerDialog.OnTimeSetListener{
    private Context mContext;
    private ArrayList<DataKeepStatus> mDataKeepStatuses;
    private int mIndex;
    private static LayoutParams MAT_WRA_LAYOUT = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    public Linera(Context context) {
        this(context, null);
    }

    public Linera(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Linera(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    LinearLayout mTitle;
    LayoutParams mWeightLayout;
    private EditText editText;
    private EditText editText2;
    private EditText editText3;
    private LayoutInflater mInflater;
    private View mLine;
    int mHeight;
    private void init() {
        mDataKeepStatuses = new ArrayList<>();
        mInflater = LayoutInflater.from(mContext);
        mHeight = mContext.getResources().getDisplayMetrics().heightPixels;

        setOrientation(VERTICAL);
        mTitle = new LinearLayout(mContext);
        mTitle.setOrientation(HORIZONTAL);
        mTitle.setLayoutParams(MAT_WRA_LAYOUT);
        mWeightLayout = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1);
        mWeightLayout.gravity = Gravity.CENTER_HORIZONTAL;


        TextInputLayout inputLayout = new TextInputLayout(mContext);
        editText = new EditText(mContext);
        editText.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        inputLayout.addView(editText, 0, MAT_WRA_LAYOUT);
        inputLayout.setHint("留存量");

        TextInputLayout inputLayout2 = new TextInputLayout(mContext);
        editText2 = new EditText(mContext);
        editText2.setInputType(EditorInfo.TYPE_NULL);
        editText2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(Linera.this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.show(((Activity) mContext).getFragmentManager(), "TimePickerDialog");

            }
        });
        inputLayout2.addView(editText2, 0, MAT_WRA_LAYOUT);
        inputLayout2.setHint("时间");

        TextInputLayout inputLayout3 = new TextInputLayout(mContext);
        editText3 = new EditText(mContext);
        editText3.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        inputLayout3.addView(editText3, 0, MAT_WRA_LAYOUT);
        inputLayout3.setHint("状态");

        ImageView imageView = new ImageView(mContext);
        imageView.setBackgroundResource(R.drawable.ic_exit_to_app_black_36dp);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        params.setMargins((int) dp2px(10), 0, (int) dp2px(10), 0);
        imageView.setOnClickListener(this);

        mLine = new View(mContext);
        LayoutParams lineLayout = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) dp2px(1));
        mLine.setLayoutParams(lineLayout);


        mTitle.addView(inputLayout, mWeightLayout);
        mTitle.addView(inputLayout2, mWeightLayout);
        mTitle.addView(inputLayout3, mWeightLayout);
        mTitle.addView(imageView, params);
        MAT_WRA_LAYOUT.setMargins(0, 0, 0, 20);
        addView(mTitle, MAT_WRA_LAYOUT);
        addView(mLine);
    }

//    public void setData(ArrayList<DataKeepStatus> dataKeepStatuses){
//        mDataKeepStatuses.clear();
//        mDataKeepStatuses.addAll(dataKeepStatuses);
//    }

    public ArrayList<DataKeepStatus> getList(){
        return mDataKeepStatuses;
    }

    public void addItemList(ArrayList<DataKeepStatus> dataKeepStatuses){
        for (int i = 0; i < dataKeepStatuses.size(); i++) {
            addItem(dataKeepStatuses.get(i));
        }
    }

    public void addItem(final DataKeepStatus dataKeepStatus){

        mDataKeepStatuses.add(dataKeepStatus);
        final View view = mInflater.inflate(R.layout.item_datastatus, null);
        TextView keep = (TextView) view.findViewById(R.id.tv_keep);
        TextView time = (TextView) view.findViewById(R.id.tv_time);
        TextView status = (TextView) view.findViewById(R.id.tv_status);
        keep.setText(dataKeepStatus.getKeepCount()+"");
        time.setText(dataKeepStatus.getKeepTime());
        status.setText(dataKeepStatus.getKeepDayStatus() + "");
        view.findViewById(R.id.iv_delete).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataKeepStatuses.remove(dataKeepStatus);
                ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", mHeight);
                animator.setDuration(400);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        removeView(view);
                    }
                });
                animator.start();
            }
        });
        addView(view);
    }



    @Override
    public void onClick(View v) {
        DataKeepStatus dataKeepStatus = new DataKeepStatus();
        String keep = editText.getText().toString();
        String time = editText2.getText().toString();
        String status = editText3.getText().toString();
        try {
            int keepCount = Integer.parseInt(keep);
            int keepStatus = Integer.parseInt(status);
            if(keepStatus>2){
                throw new IllegalAccessException();
            }
            String s = time.split(":")[1];
            dataKeepStatus.setKeepCount(keepCount);
            dataKeepStatus.setKeepTime(time);
            dataKeepStatus.setKeepDayStatus(keepStatus);
            addItem(dataKeepStatus);
            editText.setText("");
            editText2.setText("");
            editText3.setText("");
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(mContext,"输入数据格式失效",Toast.LENGTH_SHORT).show();
        }

    }

    private float dp2px(int dp){
        float density = mContext.getResources().getDisplayMetrics().density;
        return dp * density + 0.5f;
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

        editText2.setText(hourOfDay+":"+minute);
    }
}
