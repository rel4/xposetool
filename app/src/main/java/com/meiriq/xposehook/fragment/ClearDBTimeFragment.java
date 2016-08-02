package com.meiriq.xposehook.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meiriq.xposehook.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClearDBTimeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClearDBTimeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClearDBTimeFragment extends TimePickFragment {


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment ClearDBTimeFragment.
     */

    public static ClearDBTimeFragment newInstance() {
        ClearDBTimeFragment fragment = new ClearDBTimeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public ClearDBTimeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_blank, container, false);
        return view;
    }


    public void dataClear(String date) {
        if (mListener != null) {
            mListener.onDataClear(date);
        }
    }

    private AppCompatEditText mDate;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDate = (AppCompatEditText) view.findViewById(R.id.edittext);
        AppCompatButton appCompatButton = (AppCompatButton) view.findViewById(R.id.button);
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCurDatePickerFragment();
            }
        });
        final AppCompatButton mBtClearTime = (AppCompatButton) view.findViewById(R.id.clear_time);
        mBtClearTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDate.getText().toString().split("-").length == 3){
                    dataClear(mDate.getText().toString());
                }else {
                    mDate.setText("");
                    Snackbar.make(mBtClearTime,"数据不正确",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onDataClear(String date);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        super.onDateSet(view, year, monthOfYear, dayOfMonth);
        DecimalFormat format = new DecimalFormat("00");

        mDate.setText(year+"-"+(format.format(monthOfYear+1))+"-"+format.format(dayOfMonth));
    }
}
