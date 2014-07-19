package com.clunkybyte.apps.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Ervin Barta on 7/8/2014.
 */
public class DatePickerFragment extends DialogFragment {
    public static String EXTRA_CRIME_DATE = "com.clunkybyte.extras.crime_date";
    private Date mDate;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDate = (Date) getArguments().get(EXTRA_CRIME_DATE);

        // Create a Calendar to get the date in int
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        View datePickerView = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);

        DatePicker datePicker = (DatePicker) datePickerView.findViewById(R.id.dialog_date_datePicker);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
                mDate = new GregorianCalendar(year, month, day).getTime();

                // Update the arguments, to preserve data when a screen rotation happens
                getArguments().putSerializable(EXTRA_CRIME_DATE, mDate);
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(datePickerView)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(
                        android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sendResult(Activity.RESULT_OK);
                            }
                        }
                )
                .create();
    }

    public static Fragment newInstance(Date crimeDate) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();

        // Pass the arguments
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_DATE, crimeDate);
        datePickerFragment.setArguments(args);

        return datePickerFragment;
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null)
            return;

        Intent intent = new Intent();
        intent.putExtra(EXTRA_CRIME_DATE, mDate);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
