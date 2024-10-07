package com.iamdrjsolanki.sqb.caching;

import com.iamdrjsolanki.sqb.model.StockQuoteDetails;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class StockQuoteCache implements AbstractCache<String, StockQuoteDetails> {

    public final int sizeOfCache = 1000;

    private final Map<String, StockQuoteDetails> stockQuoteDetailsMap =
            Collections.synchronizedMap(new LinkedHashMap<>(sizeOfCache));

    /**
     * If the size of the map is 1000, then remove the first entry from the map
     * else, put the key, value at the end of the map
     * @param key
     * @param value
     */
    @Override
    public void putInCache(String key, StockQuoteDetails value) {
        if(stockQuoteDetailsMap.size() == sizeOfCache) {
            removeFromCache(stockQuoteDetailsMap.entrySet().iterator().next().getKey());
        }
        stockQuoteDetailsMap.put(key, value);
    }

    /**
     * get the value from the map
     * remove the key, value from the map
     * put the key value back in the map to that the latest is always at the end
     * and least used is always at the top
     * @param key
     * @return
     */
    @Override
    public Optional<StockQuoteDetails> getFromCache(String key) {
        Optional<StockQuoteDetails> optionalStockQuoteDetails = Optional.empty();
        if(stockQuoteDetailsMap.containsKey(key)) {
            optionalStockQuoteDetails = Optional.of(stockQuoteDetailsMap.get(key));
            removeFromCache(key);
            putInCache(key, optionalStockQuoteDetails.get());
        }
        return optionalStockQuoteDetails;
    }

    @Override
    public boolean containsKeyInCache(String key) {
        return stockQuoteDetailsMap.containsKey(key);
    }

    @Override
    public void removeFromCache(String key) {
        stockQuoteDetailsMap.remove(key);
    }
}
