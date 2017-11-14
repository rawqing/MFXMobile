package com.FXTSMobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.constant.Level;
import com.constant.Symbol;
import com.feature.Candlestick;
import com.feature.HistoryResponseListener;
import com.feature.SharedObjects;
import com.fxcore2.IO2GResponseListener;
import com.fxcore2.O2GCandleOpenPriceMode;
import com.fxcore2.O2GMarketDataSnapshotResponseReader;
import com.fxcore2.O2GRequest;
import com.fxcore2.O2GRequestFactory;
import com.fxcore2.O2GResponse;
import com.fxcore2.O2GResponseReaderFactory;
import com.fxcore2.O2GSession;
import com.fxcore2.O2GTimeframe;
import com.fxcore2.O2GTimeframeCollection;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import static com.constant.Level.H3;
import static com.constant.Level.H4;
import static com.constant.Level.H5;
import static com.constant.Level.L3;
import static com.constant.Level.L4;
import static com.constant.Level.L5;
import static com.constant.Symbol.USDJPY;

public class CamarillaActivity extends Activity {

    private O2GSession mSession;
    private O2GResponse mResponse = null;
    HistoryResponseListener responseListener = new HistoryResponseListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camarilla);

        mSession = SharedObjects.getInstance().getSession();

        Map<Symbol, Candlestick> data = getAllHistory("D1", 2);
        createLiveData(data);
        System.out.println("hello");
    }

    private Map<Symbol,Candlestick> getAllHistory(String timer , int len){
        Map<Symbol,Candlestick> map = new HashMap<>();
        mSession.subscribeResponse(responseListener);
        O2GRequestFactory factory = mSession.getRequestFactory();
        if (factory == null) {
            System.out.println("factory is null !");
            return null;
        }
        O2GTimeframeCollection timeFrames = factory.getTimeFrameCollection();
        O2GTimeframe timeFrame = timeFrames.get(timer);
        if (timeFrame == null) {
            System.out.println("You specified an invalid time frame.");
            return null;
        }
        for(Symbol symbol : Symbol.values()){
            O2GRequest marketDataRequest = factory.createMarketDataSnapshotRequestInstrument(symbol.getDescribe(), timeFrame, len);
            factory.fillMarketDataSnapshotRequestTime(marketDataRequest, null, null, false,
                    O2GCandleOpenPriceMode.PREVIOUS_CLOSE);
            String requestId = marketDataRequest.getRequestId();
            responseListener.setRequestID(requestId);
            //必须发送请求
            mSession.sendRequest(marketDataRequest);

            if (!responseListener.waitEvents()) {
                System.out.println("Response waiting timeout expired");
            }
            O2GResponse response = responseListener.getResponse();
            if(response == null){
                System.out.println(symbol.name() + " response is null!");
            }
            O2GResponseReaderFactory responseFactory = mSession.getResponseReaderFactory();
            O2GMarketDataSnapshotResponseReader reader = responseFactory.createMarketDataSnapshotReader(response);
            int mReaderSize = reader.size();
            int h_index = 0;
            if (mReaderSize == 2) {
                Candlestick candl = new Candlestick(reader.getDate(h_index).getTime(),
                        reader.getBidOpen(h_index),
                        reader.getBidLow(h_index),
                        reader.getBidHigh(h_index),
                        reader.getBidClose(h_index));
                map.put(symbol, candl);
            }
        }
        return map;
    }


    private void createLiveData(Map<Symbol, Candlestick> data){
        for(Symbol symbol: data.keySet()) {
            Candlestick candlestick = data.get(symbol);
            createView(R.id.l_h5 ,symbol ,candlestick ,H5);
            createView(R.id.l_h4 ,symbol ,candlestick ,H4);
            createView(R.id.l_h3 ,symbol ,candlestick ,H3);
            createView(R.id.l_l3 ,symbol ,candlestick ,L3);
            createView(R.id.l_l4 ,symbol ,candlestick ,L4);
            createView(R.id.l_l5 ,symbol ,candlestick ,L5);
        }
    }

    private void createView(int pid , Symbol symbol , Candlestick candlestick , Level level){
        TableRow row = findViewById(pid);
        TextView text = (TextView) row.getChildAt(symbol.getIndex());
//        text.setPadding(0, 5, 0, 5);
        text.setText(toBigDecimal(symbol, level.getLevelData(candlestick)).toString());
//        row.addView(text, symbol.getIndex());
    }
    /**
     * 将double转换成 BigDecimal
     * @param symbol
     * @param d
     * @return
     */
    private BigDecimal toBigDecimal(Symbol symbol ,double d) {
        BigDecimal bData = new BigDecimal(d);
        bData = bData.setScale(symbol.getDigits(), RoundingMode.HALF_UP);
        return bData;
    }
}
