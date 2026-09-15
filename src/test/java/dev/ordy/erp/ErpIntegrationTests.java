package dev.ordy.erp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ErpIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String loginAndGetToken() throws Exception {
        String loginJson = """
                {
                  "username": "sina",
                  "password": "123"
                }
                """;

        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        return json.get("token").asText();
    }

    @Test
    void login_withCorrectCredentials_returnsToken() throws Exception {
        String token = loginAndGetToken();
        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void login_withWrongPassword_returnsUnauthorized() throws Exception {
        String loginJson = """
                {
                  "username": "sina",
                  "password": "wrong-password"
                }
                """;

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void protectedEndpoint_withoutToken_returnsUnauthorized() throws Exception {
        mockMvc.perform(get("/orders"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void protectedEndpoint_withValidToken_returnsOk() throws Exception {
        String token = loginAndGetToken();

        mockMvc.perform(get("/orders")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void createOrder_withValidData_ignoresClientSuppliedIdAndStatus() throws Exception {
        String token = loginAndGetToken();

        String orderJson = """
                {
                  "id": 999,
                  "businessId": 1,
                  "customerId": 1,
                  "totalLogisticPrice": 10.0,
                  "discountInPercent": 0,
                  "discountInCurrency": 0,
                  "currency": "USD",
                  "paymentType": "CASH",
                  "orderStatus": "CANCELED"
                }
                """;

        mockMvc.perform(post("/orders")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(not(999)))
                .andExpect(jsonPath("$.orderStatus").value("PENDING"));
    }

    @Test
    void createOrder_missingRequiredField_returnsBadRequest() throws Exception {
        String token = loginAndGetToken();

        String orderJson = """
                {
                  "customerId": 1,
                  "totalLogisticPrice": 10.0,
                  "discountInPercent": 0,
                  "discountInCurrency": 0,
                  "currency": "USD",
                  "paymentType": "CASH"
                }
                """;

        mockMvc.perform(post("/orders")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isBadRequest());
    }
}