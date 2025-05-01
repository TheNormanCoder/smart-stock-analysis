package com.example.smartstockanalysis.model;

import java.util.Date;

public class StockData {

    private Date date;
    private double open;
    private double high;
    private double low;
    private double close;
    private long volume;

    public StockData(Date date, double open, double high, double low, double close, long volume) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }

    public Date getDate() {
        return date;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }

    public long getVolume() {
        return volume;
    }
}
