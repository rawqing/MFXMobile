package com.feature;

import com.fxcore2.IO2GResponseListener;
import com.fxcore2.O2GResponse;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * Created by king on 2017/11/13.
 */

public class HistoryResponseListener implements IO2GResponseListener {
    private O2GResponse mResponse = null;
    private String requestID = "";
    private Semaphore semaphore = new Semaphore(0);

    public O2GResponse getResponse() {
        return mResponse;
    }

    public void setmResponse(O2GResponse response) {
        mResponse = response;
    }
    public void setRequestID(String requestID) {
        mResponse = null;
        this.requestID = requestID;
    }


    @Override
    public void onRequestCompleted(String requestID, O2GResponse o2GResponse) {
        System.out.println("hello");
        if(requestID.equals(this.requestID)) {
            mResponse = o2GResponse;
            System.out.println("Request " + o2GResponse + " completed");
        }
    }

    @Override
    public void onRequestFailed(String requestID, String error) {
        System.out.println("Request " + requestID + " failed. Error = " + error);
    }

    @Override
    public void onTablesUpdates(O2GResponse o2GResponse) {

    }

    /**
     * 等待事件
     * @return
     */
    public boolean waitEvents()  {
        int time = 10;
        for(int i=0;i<time;i++) {
            if (mResponse != null) {
                return true;
            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public boolean waitEvents1(){
        try {
            return semaphore.tryAcquire(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
