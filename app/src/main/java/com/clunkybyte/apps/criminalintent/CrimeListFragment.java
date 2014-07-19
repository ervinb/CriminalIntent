package com.clunkybyte.apps.criminalintent;

import android.support.v4.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hybr1d on 6/29/2014.
 */
public class CrimeListFragment extends ListFragment{
  private ArrayList<Crime> mCrimes;
  private static final String TAG = "CrimeListFragment";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getActivity().setTitle(R.string.crimes_title);
    mCrimes = CrimeLab.get(getActivity()).getCrimes();

    CrimeAdapter adapter = new CrimeAdapter(mCrimes);
    setListAdapter(adapter);
  }

  @Override
  public void onListItemClick(ListView l, View v, int position, long id) {
    Crime crime = ((CrimeAdapter) getListAdapter()).getItem(position);

    Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
    intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
    startActivity(intent);
  }

  private class CrimeAdapter extends ArrayAdapter<Crime> {
    private CrimeAdapter(ArrayList<Crime> crimes){
      super(getActivity(), 0, crimes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      if (convertView == null) {
        convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null);
      }

      Crime crime = getItem(position);

      CheckBox solvedCheckbox = (CheckBox) convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
      solvedCheckbox.setChecked(crime.getSolved());

      TextView crimeTitle = (TextView) convertView.findViewById(R.id.crime_list_item_titleTextView);
      crimeTitle.setText(crime.getTitle());

      TextView dateText = (TextView) convertView.findViewById(R.id.crime_list_item_dateTextView);
      dateText.setText(crime.getDate().toString());

      return convertView;
    }
  }
}
