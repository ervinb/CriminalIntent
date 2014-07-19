package com.clunkybyte.apps.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

/**
 * Created by hybr1d on 6/22/2014.
 */
public class CrimeFragment extends Fragment {
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private Button mTimeButton;
    private CheckBox mSolvedCheckbox;

    public static final String EXTRA_CRIME_ID = "com.clunkybyte.crime";

    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_TIME = "time";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;

    DateFormat formattedDate = new DateFormat();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleField = (EditText) view.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                mCrime.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        // Handle the DateButton click

        mDateButton = (Button) view.findViewById(R.id.crime_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                DatePickerFragment datePickerDialog = (DatePickerFragment) DatePickerFragment.newInstance(mCrime.getDate());
                datePickerDialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                datePickerDialog.show(fragmentManager, DIALOG_DATE);
            }
        });

        // Handle the TimeButton click

        mTimeButton = (Button) view.findViewById(R.id.crime_time);
        mTimeButton.setText(mCrime.getTime());
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                TimePickerFragment timePickerDialog = (TimePickerFragment) TimePickerFragment.newInstance(mCrime.getDate());
                timePickerDialog.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
                timePickerDialog.show(fragmentManager, DIALOG_TIME);
            }
        });

        mSolvedCheckbox = (CheckBox) view.findViewById(R.id.crime_solved);
        mSolvedCheckbox.setChecked(mCrime.getSolved());
        mSolvedCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCrime.setSolved(b);
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().get(EXTRA_CRIME_ID);

        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    public static Fragment newInstance(UUID crime_id) {
        CrimeFragment crimeFragment = new CrimeFragment();

        // Supply arguments
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crime_id);
        crimeFragment.setArguments(args);

        return crimeFragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_CRIME_DATE);
            mCrime.setmDate(date);
            updateDate();
        }
        else if (requestCode == REQUEST_TIME) {
            Date date = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_CRIME_TIME);
            mCrime.setmDate(date);
            mTimeButton.setText(mCrime.getTime());
        }
    }

    public void updateDate() {
        mDateButton.setText(mCrime.getDate().toString());
    }
}
