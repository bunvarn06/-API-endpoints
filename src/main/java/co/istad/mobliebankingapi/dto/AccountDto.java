package co.istad.mobliebankingapi.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AccountDto(
       String actNo

) {
}
