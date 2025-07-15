package co.istad.mobliebankingapi.mapper;


import co.istad.mobliebankingapi.domain.Customer;
import co.istad.mobliebankingapi.dto.CreateCustomerRequest;
import co.istad.mobliebankingapi.dto.CustomerResponse;
import co.istad.mobliebankingapi.dto.UpdateCustomerRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
//    DTO -> Model
//    Model -> DTO
//    What is source data?(parameter)
//    What is target data? (return_type)
//    Response is target.
//    Customer is source

    @Mapping(target = "customerSegment", ignore = true)
    @Mapping(target = "kyc", ignore = true)
    Customer fromCreateCustomerRequestToCustomer(CreateCustomerRequest customerRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toCustomerPartially(
            UpdateCustomerRequest updateCustomerRequest,
          @MappingTarget Customer customer
    );
    CustomerResponse mapcustomerToCustomerResponse(Customer customer);
    CustomerResponse fromCustomerToCustomerResponse(Customer customer);

    Customer fromCreateCustomerRequest(CreateCustomerRequest createCustomerRequest);
}
