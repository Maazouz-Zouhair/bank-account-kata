package fr.zm.bankaccount.integrationTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.zm.bankaccount.account.Account;
import fr.zm.bankaccount.account.Bank;
import fr.zm.bankaccount.account.Client;
import fr.zm.bankaccount.enums.ErrorMessages;
import fr.zm.bankaccount.factory.BankFactory;
import fr.zm.bankaccount.factory.SimpleBankFactory;
import fr.zm.bankaccount.restapi.dto.RequestDTO;
import fr.zm.bankaccount.restapi.repository.AccountRepository;
import fr.zm.bankaccount.restapi.repository.ClientRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    private ObjectMapper objectMapper;
    private Bank bank;
    private Client client;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        BankFactory bankFactory = new SimpleBankFactory();
        bank = bankFactory.createBank("My Bank");
        client = bankFactory.createClient("C001", "John", "Doe");
        clientRepository.save(client);
        accountRepository.save(new Account(bank, client));
    }

    @Test
    public void testDeposit() throws Exception {
        // Given
        String clientId = "C001";
        RequestDTO depositRequest = new RequestDTO(BigDecimal.valueOf(100.00));

        // When & Then
        mockMvc.perform(post("/api/accounts/{clientId}/deposit", clientId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(depositRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Deposit successful!"));
    }

    @Test
    public void testDepositInvalidAmount() throws Exception {
        // Given
        String clientId = "C001";
        RequestDTO depositRequest = new RequestDTO(BigDecimal.valueOf(-1)); // Invalid amount

        // When & Then
        mockMvc.perform(post("/api/accounts/{clientId}/deposit", clientId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(depositRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.amount").value("Amount must be greater than or equal to 0.01"));
    }

    @Test
    public void testDepositInvalidClient() throws Exception {
        String invalidClientId = "xxx";
        RequestDTO depositRequest = new RequestDTO(new BigDecimal("100.00"));

        mockMvc.perform(post("/api/accounts/{clientId}/deposit", invalidClientId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(depositRequest)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").value(invalidClientId+ErrorMessages.UNKNOWN_CLIENT.getMessage()));
    }


}
