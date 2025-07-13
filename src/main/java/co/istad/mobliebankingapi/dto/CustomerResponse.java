package co.istad.mobliebankingapi.dto;

import lombok.Builder;

@Builder
public record CustomerResponse(
        String fullName,
        String gender,
        String email,
        String phoneNumber

) {
}
