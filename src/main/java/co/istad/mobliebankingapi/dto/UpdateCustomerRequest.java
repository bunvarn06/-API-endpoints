package co.istad.mobliebankingapi.dto;

public record UpdateCustomerRequest(
        String fullName,
        String gender,
        String remark
) {
}
