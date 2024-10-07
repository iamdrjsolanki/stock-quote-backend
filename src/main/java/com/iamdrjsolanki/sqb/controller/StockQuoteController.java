package com.iamdrjsolanki.sqb.controller;

import com.iamdrjsolanki.sqb.model.AlphaVantageAPIResponse;
import com.iamdrjsolanki.sqb.service.StockQuoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class StockQuoteController {

    private static final Logger log = LoggerFactory.getLogger(StockQuoteController.class);

    private final StockQuoteService stockQuoteService;

    public StockQuoteController(StockQuoteService stockQuoteService) {
        this.stockQuoteService = stockQuoteService;
    }

    @Operation(summary = "Get Stock Quote",
            description = "Get stock quote details for a symbol using AlphaVantage API.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AlphaVantageAPIResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid symbol or API request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping("/stock")
    public ResponseEntity<AlphaVantageAPIResponse> getStockQuote(@RequestParam(name = "symbol") String symbol) {
        log.info("Symbol " + symbol + " received on server to query.");
        return this.stockQuoteService.fetchQuoteDetailsFromAlphaVantage(symbol);
    }

}
