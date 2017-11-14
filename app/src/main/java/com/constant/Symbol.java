package com.constant;

/**
 * Created by king on 2017/11/13.
 */

public enum Symbol {

    USDJPY("USD/JPY" ,3 ,1),
    GBPUSD("GBP/USD" ,5 ,2),
    EURUSD("EUR/USD" ,5 ,3),
    AUDUSD("AUD/USD" ,5 ,4),
    NZDUSD("NZD/USD" ,5 ,5),
    USDCAD("USD/CAD" ,5 ,6),
    USDCHF("USD/CHF" ,5 ,7);

    private String describe;
    private int digits;
    private int index;

    private Symbol(String describe ,int digits ,int index) {
        this.describe = describe;
        this.digits = digits;
        this.index = index;
    }
    public String getDescribe(){
        return this.describe;
    }

    public int getDigits() {
        return digits;
    }

    public int getIndex() {
        return index;
    }
}
