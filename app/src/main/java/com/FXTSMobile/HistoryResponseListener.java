package com.FXTSMobile;

import com.fxcore2.IO2GResponseListener;
import com.fxcore2.O2GResponse;

/**
 * Created by king on 2017/11/13.
 */

public class HistoryResponseListener implements IO2GResponseListener {
    private O2GResponse mResponse = null;
    public O2GResponse getResponse() {
        return mResponse;
    }
    // Constructor
    public HistoryResponseListener() {

    }

    @Override
    public void onRequestCompleted(String s, O2GResponse o2GResponse) {
        mResponse = o2GResponse;
        System.out.println("Request " + o2GResponse + " completed");
    }

    @Override
    public void onRequestFailed(String requestID, String error) {
        System.out.println("Request " + requestID + " failed. Error = " + error);
    }

    @Override
    public void onTablesUpdates(O2GResponse o2GResponse) {

    }
}
