package fr.zm.bankaccount.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransactionResponseDTO {
    private boolean success;
    private String message;
}
