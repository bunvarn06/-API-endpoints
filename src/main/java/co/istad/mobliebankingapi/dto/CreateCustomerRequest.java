package co.istad.mobliebankingapi.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CreateCustomerRequest(
        String fullName,
        String gender,
        LocalDate dob,
        String email,
        String phoneNumber,
        String remark,
        String nationalCardId,
        String segment

) {
}
