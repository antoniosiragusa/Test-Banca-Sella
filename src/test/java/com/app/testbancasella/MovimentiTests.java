package com.app.testbancasella;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class MovimentiTests extends TestBancaSellaApplicationTests{
	
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void init() throws JSONException{
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
    @Test
    public void getTransactions() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/fabrick/14537780/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Auth-Schema","S2S")
                .header("Api-Key","FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP")
                .param("fromAccountingDate","2019-04-01")
                .param("toAccountingDate","2019-06-01"))
                .andExpect(status().isOk())
                .andDo(print());

    }

}
