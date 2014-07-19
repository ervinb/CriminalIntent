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
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ervin Barta on 7/16/2014.
 */
public class TimePickerFragment extends DialogFragment {
    private static Date mDate = new Date();
    public static final String EXTRA_CRIME_TIME = "com.clunkybyte.extras.crime_time";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDate = (Date) getArguments().get(EXTRA_CRIME_TIME);

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        View timePickerDialog = getActivity().getLayoutInflater().inflate(R.layout.dialog_time, null);

        // Setup the TimePicker and assure that when the device is rotated, the current time value will
        // be preserved

        TimePicker timePicker = (TimePicker) timePickerDialog.findViewById(R.id.dialog_time_timePicker);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                calendar.set(Calendar.HOUR, hour);
                calendar.set(Calendar.MINUTE, minute);

                mDate = calendar.getTime();
                getArguments().putSerializable(EXTRA_CRIME_TIME, mDate);
            }
        });

       // Display the dialog with the time picker
       return new AlertDialog.Builder(getActivity())
               .setView(timePickerDialog)
               .setTitle("Time of the crime")
               .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       sendResult(Activity.RESULT_OK);
                   }
               })
               .show();
    }

    public static Fragment newInstance(Date date) {
        TimePickerFragment timePickerFragment = new TimePickerFragment();

        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_TIME, date);

        timePickerFragment.setArguments(args);

        return timePickerFragment;
    }

    private void sendResult(int resultCode) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CRIME_TIME, mDate);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
