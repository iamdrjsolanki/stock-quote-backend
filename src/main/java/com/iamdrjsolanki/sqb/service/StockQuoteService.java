package com.iamdrjsolanki.sqb.service;

import com.iamdrjsolanki.sqb.caching.AbstractCache;
import com.iamdrjsolanki.sqb.caching.StockQuoteCacheManager;
import com.iamdrjsolanki.sqb.model.AlphaVantageAPIResponse;
import com.iamdrjsolanki.sqb.model.Constants;
import com.iamdrjsolanki.sqb.model.StockQuoteDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Optional;

@Service
public class StockQuoteService {

    private static final Logger log = LoggerFactory.getLogger(StockQuoteService.class);

    private final RestTemplate restTemplate;

    protected AbstractCache<String, StockQuoteDetails> stockQuoteCache = StockQuoteCacheManager.getInstance().getStockQuoteCache();

    public StockQuoteService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<AlphaVantageAPIResponse> fetchQuoteDetailsFromAlphaVantage(String symbol) {
        String url = Constants.APLHA_VANTAGE_STOCK_QUOTE_SYMBOL_ENDPOINT + symbol + Constants.APLHA_VANTAGE_API_KEY;
        log.info("fetching from alpha vantage url: " + url);
        try {
            Optional<StockQuoteDetails> optionalStockQuoteDetails = stockQuoteCache.getFromCache(symbol);
            if(optionalStockQuoteDetails.isPresent()) {
                return ResponseEntity.ok(new AlphaVantageAPIResponse(optionalStockQuoteDetails.get()));
            }

            Object response = restTemplate.getForObject(url, Object.class);

            if (response instanceof AlphaVantageAPIResponse) {
                log.info("Response from alpha vantage: " + response);
                stockQuoteCache.putInCache(symbol, ((AlphaVantageAPIResponse) response).getStockQuoteDetails());
                return ResponseEntity.ok((AlphaVantageAPIResponse) response);
            } else if (response instanceof LinkedHashMap) {
                LinkedHashMap<String, Object> errorResponse = (LinkedHashMap<String, Object>) response;
                if (errorResponse.containsKey("Error Message")) {
                    String errorMessage = (String) errorResponse.get("Error Message");
                    log.error("Alpha Vantage API Error: " + errorMessage);
                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                } else if (errorResponse.containsKey("Information")) {
                    String info = (String) errorResponse.get("Information");
                    log.error("Alpha Vantage API Information: " + info);
                    return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
                }
            }

            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        } catch (HttpClientErrorException e) {
            log.error("Client error when calling Alpha Vantage API: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (RestClientException e) {
            log.error("Error when calling Alpha Vantage API: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            log.error("Unexpected error: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
