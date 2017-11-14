package com.constant;


import com.feature.Candlestick;

/**
 * Created by king on 2017/11/14.
 */

public enum Level {
    H5 {
        @Override
        public double getLevelData(Candlestick candl) {
            return (candl.getHigh() / candl.getLow()) * candl.getClose();
        }
    },
    H4 {
        @Override
        public double getLevelData(Candlestick candl) {
            return candl.getClose() + (candl.getHigh() - candl.getLow())*1.1/2;
        }
    },
    H3 {
        @Override
        public double getLevelData(Candlestick candl) {
            return candl.getClose() + (candl.getHigh() - candl.getLow())*1.1/4;
        }
    },
    L3 {
        @Override
        public double getLevelData(Candlestick candl) {
            return candl.getClose() - (candl.getHigh() - candl.getLow())*1.1/4;
        }
    },
    L4 {
        @Override
        public double getLevelData(Candlestick candl) {
            return candl.getClose() - (candl.getHigh() - candl.getLow())*1.1/2;
        }
    },
    L5 {
        @Override
        public double getLevelData(Candlestick candl) {
            return candl.getClose() - ((candl.getHigh() / candl.getLow()) * candl.getClose() - candl.getClose() );
        }
    };

    abstract public double getLevelData(Candlestick candl);
}
