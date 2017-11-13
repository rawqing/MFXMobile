package com.FXTSMobile;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.fxcore2.IO2GResponseListener;
import com.fxcore2.O2GMarketDataSnapshotResponseReader;
import com.fxcore2.O2GRequest;
import com.fxcore2.O2GRequestFactory;
import com.fxcore2.O2GResponse;
import com.fxcore2.O2GResponseReaderFactory;
import com.fxcore2.O2GSession;
import com.fxcore2.O2GTableType;
import com.fxcore2.O2GTimeframe;
import com.fxcore2.O2GTimeframeCollection;

public class CamarillaActivity extends Activity {

    private O2GSession mSession;
    private O2GResponse mResponse = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camarilla);

        mSession = SharedObjects.getInstance().getSession();
//        mSession.subscribeResponse(this);

        O2GRequestFactory factory = mSession.getRequestFactory();
        if (factory != null) {
            O2GTimeframeCollection timeFrames = factory.getTimeFrameCollection();
            O2GTimeframe timeFrame = timeFrames.get("D1");
            if (timeFrame == null) {
                System.out.println("You specified an invalid time frame.");
                return;
            }
            O2GRequest marketDataRequest = factory.createMarketDataSnapshotRequestInstrument("EUR/USD", timeFrame, 2);
//            factory.fillMarketDataSnapshotRequestTime(marketDataRequest,null,null,false,);
            mSession.sendRequest(marketDataRequest);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            O2GResponseReaderFactory responseFactory = mSession.getResponseReaderFactory();
            O2GMarketDataSnapshotResponseReader reader = responseFactory.createMarketDataSnapshotReader(mResponse);
            int mReaderSize = reader.size();
            int h_index = 0;
            if (mReaderSize == 2) {
                double bidHigh = reader.getBidHigh(h_index);
                double bidClose = reader.getBidClose(h_index);
                double bidLow = reader.getBidLow(h_index);
                double bidOpen = reader.getBidOpen(h_index);

//                TextView eurh5 = findViewById(R.id.eurusd_h5);
//                eurh5.setText(Double.toString(bidHigh));
            }
        }
    }

}
