package com.feature;

import java.util.Date;

public class Candlestick {

	private Date date;
	private double open;
	private double low;
	private double high;
	private double close;

	public Candlestick(Date date, double open, double low, double high, double close) {
		this.date = date;
		this.open = open;
		this.low = low;
		this.high = high;
		this.close = close;
	}

	public Candlestick() {
	}
	// getters and setters goes here...

	
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}


}
