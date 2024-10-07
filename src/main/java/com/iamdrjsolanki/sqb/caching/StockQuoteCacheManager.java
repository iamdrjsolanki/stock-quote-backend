package com.iamdrjsolanki.sqb.caching;

import com.iamdrjsolanki.sqb.model.StockQuoteDetails;

public class StockQuoteCacheManager {

    public static StockQuoteCacheManager instance = null;

    private final AbstractCache<String, StockQuoteDetails> stockQuoteCache;

    private StockQuoteCacheManager() {
        this.stockQuoteCache = new StockQuoteCache();
    }

    public static StockQuoteCacheManager getInstance() {
        if(instance == null) {
            instance = new StockQuoteCacheManager();
        }
        return instance;
    }

    public AbstractCache<String, StockQuoteDetails> getStockQuoteCache() {
        return this.stockQuoteCache;
    }

}
