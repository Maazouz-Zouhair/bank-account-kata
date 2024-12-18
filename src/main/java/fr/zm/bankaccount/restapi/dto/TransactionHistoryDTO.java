package fr.zm.bankaccount.restapi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransactionHistoryDTO {
    private String operationType;
    private LocalDate date;
    private BigDecimal amount;
    private BigDecimal balance;

}
