package com.clunkybyte.apps.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created by hybr1d on 6/29/2014.
 */
public abstract class SingleFragmentActivity extends FragmentActivity {
  protected abstract Fragment createFragment();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fragment);
    FragmentManager fm = getSupportFragmentManager();
    Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

    if (fragment == null) {
      fragment = createFragment();
      fm.beginTransaction()
              .add(R.id.fragmentContainer, fragment)
              .commit();
    }
  }
}
