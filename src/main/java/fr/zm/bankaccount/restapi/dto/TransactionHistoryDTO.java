package fr.zm.bankaccount.restapi.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransactionHistoryDTO {
    private String operationType;
    private String date;
    private BigDecimal amount;
    private BigDecimal balance;

}
