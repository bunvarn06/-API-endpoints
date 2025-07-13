package co.istad.mobliebankingapi.dto;

import java.math.BigDecimal;

public record CreateNewAccount(
        String actNo,
        BigDecimal balance
) {
}
