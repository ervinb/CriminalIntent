package com.clunkybyte.apps.criminalintent;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by hybr1d on 6/22/2014.
 */
public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private Boolean mSolved;

    public Crime(){

        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public Boolean getSolved() {
        return mSolved;
    }

    public void setSolved(Boolean mSolved) {
        this.mSolved = mSolved;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    // Used for setting the hh:mm (Time) as well
    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public String getTime() {
        DateFormat dateFormat = DateFormat.getTimeInstance();
        return dateFormat.format(mDate);
    }

    @Override
      public String toString() {
                                     return mTitle;
                                                                                    }

      public Date getDate(){
                                  return mDate;
    }
}
