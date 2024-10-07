package com.iamdrjsolanki.sqb.service;

import com.iamdrjsolanki.sqb.model.AlphaVantageAPIResponse;
import com.iamdrjsolanki.sqb.model.Constants;
import com.iamdrjsolanki.sqb.model.StockQuoteDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class StockQuoteServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    @Spy
    private StockQuoteService stockQuoteService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Success response for stock quote")
    public void testFetchQuoteDetailsFromAlphaVantage_success() {
        StockQuoteDetails stockQuoteDetails = new StockQuoteDetails("INFY", "23.4",
                "2024-10-01", "2.3", "10");
        AlphaVantageAPIResponse response = new AlphaVantageAPIResponse(stockQuoteDetails);

        String url = Constants.APLHA_VANTAGE_STOCK_QUOTE_SYMBOL_ENDPOINT + "INFY" + Constants.APLHA_VANTAGE_API_KEY;
        Mockito.when(restTemplate.getForObject(url, Object.class)).thenReturn((Object)response);

        ResponseEntity<AlphaVantageAPIResponse> output = stockQuoteService.fetchQuoteDetailsFromAlphaVantage("ITC");

        Assertions.assertEquals(HttpStatus.OK, output.getStatusCode());
    }

    @Test
    @DisplayName("Client error for stock quote")
    void testFetchQuoteDetailsFromAlphaVantage_ClientError() {
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.anyString().getClass()))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Client Error"));

        ResponseEntity<AlphaVantageAPIResponse> response = stockQuoteService.fetchQuoteDetailsFromAlphaVantage("ITC");

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("Limit exceeded information for stock quote")
    void testFetchQuoteDetailsFromAlphaVantage_LimitExceeded() {
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.anyString().getClass()))
                .thenThrow(new HttpClientErrorException(HttpStatus.SERVICE_UNAVAILABLE, "Information"));

        ResponseEntity<AlphaVantageAPIResponse> response = stockQuoteService.fetchQuoteDetailsFromAlphaVantage("ITC");

        Assertions.assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
    }

}
