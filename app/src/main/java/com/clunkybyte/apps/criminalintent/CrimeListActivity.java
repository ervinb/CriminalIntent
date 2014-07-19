package com.clunkybyte.apps.criminalintent;


import android.support.v4.app.Fragment;

/**
 * Created by hybr1d on 6/29/2014.
 */
public class CrimeListActivity extends SingleFragmentActivity {
  @Override
  protected Fragment createFragment() {
    return new CrimeListFragment();
  }
}
