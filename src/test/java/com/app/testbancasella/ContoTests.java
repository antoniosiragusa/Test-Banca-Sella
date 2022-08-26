package com.app.testbancasella;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class ContoTests extends TestBancaSellaApplicationTests{
	
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void init() throws JSONException, IOException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    String jsonMoneyTransfer= "{\n"
            + "  \"creditor\": {\n"
            + "    \"name\": \"John Doe\",\n"
            + "    \"account\": {\n"
            + "      \"accountCode\": \"IT23A0336844430152923804660\",\n"
            + "      \"bicCode\": \"SELBIT2BXXX\"\n"
            + "    },\n"
            + "    \"address\": {\n"
            + "      \"address\": null,\n"
            + "      \"city\": null,\n"
            + "      \"countryCode\": null\n"
            + "    }\n"
            + "  },\n"
            + "  \"executionDate\": \"2019-04-01\",\n"
            + "  \"uri\": \"REMITTANCE_INFORMATION\",\n"
            + "  \"description\": \"Payment invoice 75/2017\",\n"
            + "  \"amount\": 800,\n"
            + "  \"currency\": \"EUR\",\n"
            + "  \"isUrgent\": false,\n"
            + "  \"isInstant\": false,\n"
            + "  \"feeType\": \"SHA\",\n"
            + "  \"feeAccountId\": \"45685475\",\n"
            + "  \"taxRelief\": {\n"
            + "    \"taxReliefId\": \"L449\",\n"
            + "    \"isCondoUpgrade\": false,\n"
            + "    \"creditorFiscalCode\": \"56258745832\",\n"
            + "    \"beneficiaryType\": \"NATURAL_PERSON\",\n"
            + "    \"naturalPersonBeneficiary\": {\n"
            + "      \"fiscalCode1\": \"MRLFNC81L04A859L\",\n"
            + "      \"fiscalCode2\": null,\n"
            + "      \"fiscalCode3\": null,\n"
            + "      \"fiscalCode4\": null,\n"
            + "      \"fiscalCode5\": null\n"
            + "    },\n"
            + "    \"legalPersonBeneficiary\": {\n"
            + "      \"fiscalCode\": null,\n"
            + "      \"legalRepresentativeFiscalCode\": null\n"
            + "    }\n"
            + "  }\n"
            + "}";
    @Test
    public void createMoneyTransfer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/fabrick/accounts/14537780/payments/money-transfers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                        .header("Auth-Schema","S2S")
                        .header("Api-Key","FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP")
                        .content(jsonMoneyTransfer))
                .andExpect(status().isBadRequest())
                .andDo(print());

    }
    @Test
    public void  getBalance()throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/fabrick/accounts/14537780/balance")
                .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Auth-Schema","S2S")
                        .header("Api-Key","FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP"))
                    .andExpect(status().isOk()).
                    andDo(print());
    }

}
