package com.iamdrjsolanki.sqb.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AlphaVantageAPIResponse {

    @JsonProperty("Global Quote")
    private StockQuoteDetails stockQuoteDetails;

    public AlphaVantageAPIResponse(StockQuoteDetails stockQuoteDetails) {
        this.stockQuoteDetails = stockQuoteDetails;
    }

    public StockQuoteDetails getStockQuoteDetails() {
        return stockQuoteDetails;
    }
}
