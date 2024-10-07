package com.iamdrjsolanki.sqb.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class StockQuoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetStockSymbol() throws Exception {
        /*this.mockMvc.perform(get("/api/stock?symbol=INFY"))
                .andExpect(status().isOk())
                .andExpect(content().string("Symbol INFY received on server to query."));*/
    }

}
