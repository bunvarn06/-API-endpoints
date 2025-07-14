package co.istad.mobliebankingapi.service;

import co.istad.mobliebankingapi.domain.AccountType;
import co.istad.mobliebankingapi.domain.Customer;
import co.istad.mobliebankingapi.dto.AccountDto;
import co.istad.mobliebankingapi.dto.CreateCustomerRequest;
import co.istad.mobliebankingapi.dto.CustomerResponse;
import co.istad.mobliebankingapi.dto.UpdateCustomerRequest;

import java.util.List;

public interface CustomerService {

    List<Customer> findAllByIsDeletedFalse();

    void disableByPhoneNumber(String phoneNumber);


    CustomerResponse updateByPhoneNumber(String phoneNumber, UpdateCustomerRequest updateCustomerRequest);

    CustomerResponse findByPhoneNumber(String phoneNumber);

    List<CustomerResponse> getAllCustomers();

    CustomerResponse createNew(CreateCustomerRequest createCustomerRequest);
}
