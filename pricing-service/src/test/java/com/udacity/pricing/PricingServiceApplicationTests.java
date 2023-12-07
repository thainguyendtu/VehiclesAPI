package com.udacity.pricing;

import com.udacity.pricing.api.PricingController;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PricingController.class)
public class PricingServiceApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void contextLoads() {
    }

    @Test
    public void getPriceById()
            throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                                .get("/services/price?vehicleId=1")
                                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.currency").exists())
               .andExpect(jsonPath("$.currency").value("USD"));
    }


    @Test
    public void responseException()
            throws Exception {
        String errorMessage = mockMvc.perform(MockMvcRequestBuilders
                                                      .get("/services/price?vehicleId=")
                                                      .accept(MediaType.APPLICATION_JSON))
                                     .andExpect(status().is4xxClientError())
                                     .andReturn().getResolvedException().getMessage();

        assertTrue(StringUtils.contains(errorMessage, "404 NOT_FOUND \"Price Not Found\""));
    }


}
