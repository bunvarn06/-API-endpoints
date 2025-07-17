package co.istad.mobliebankingapi.dto;

import co.istad.mobliebankingapi.util.CurrencyUtil;

import java.math.BigDecimal;

public record CreateAccountRequest(
        String actNo,
        String actName,
        CurrencyUtil actCurrency,
        BigDecimal balance,
        String accountType,
        String phoneNumber
) {
}
