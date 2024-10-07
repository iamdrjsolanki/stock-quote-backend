package com.iamdrjsolanki.sqb.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockQuoteDetails {

    @JsonProperty("01. symbol")
    private String symbol;

    @JsonProperty("05. price")
    private String price;

    @JsonProperty("07. latest trading day")
    private String latestTradingDay;

    @JsonProperty("09. change")
    private String change;

    @JsonProperty("10. change percent")
    private String changePercent;

    public StockQuoteDetails(String symbol, String price, String latestTradingDay,
                             String change, String changePercent) {
        this.symbol = symbol;
        this.price = price;
        this.latestTradingDay = latestTradingDay;
        this.change = change;
        this.changePercent = changePercent;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getPrice() {
        return price;
    }

    public String getLatestTradingDay() {
        return latestTradingDay;
    }

    public String getChange() {
        return change;
    }

    public String getChangePercent() {
        return changePercent;
    }

}
