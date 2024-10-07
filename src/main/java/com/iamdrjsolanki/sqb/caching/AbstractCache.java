package com.iamdrjsolanki.sqb.caching;

import com.iamdrjsolanki.sqb.model.StockQuoteDetails;

import java.util.Optional;

public interface AbstractCache<K, V> {

    void putInCache(K key, V value);

    Optional<V> getFromCache(K key);

    boolean containsKeyInCache(K key);

    void removeFromCache(K key);

}
