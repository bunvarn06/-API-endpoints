package co.istad.mobliebankingapi.service.impl;

import co.istad.mobliebankingapi.domain.Customer;

import co.istad.mobliebankingapi.dto.CreateCustomerRequest;
import co.istad.mobliebankingapi.dto.CustomerResponse;
import co.istad.mobliebankingapi.dto.UpdateCustomerRequest;
import co.istad.mobliebankingapi.mapper.CustomerMapper;
import co.istad.mobliebankingapi.repository.CustomerRepository;
import co.istad.mobliebankingapi.service.CustomerService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {



    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;


    @Override
    public CustomerResponse updateByPhoneNumber(String phoneNumber,
                                                UpdateCustomerRequest updateCustomerRequest) {
        Customer customer = customerRepository
                .findByPhoneNumber(phoneNumber)
                .orElseThrow(()-> new ResponseStatusException
                        (HttpStatus.NOT_FOUND, "Phone number not found"));

        customerMapper.toCustomerPartially(
                updateCustomerRequest, customer
        );
  customer = customerRepository.save(customer);
        return customerMapper.mapcustomerToCustomerResponse(customer);
    }

    @Override
    public CustomerResponse findByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber)
                .map(customerMapper::mapcustomerToCustomerResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customer -> CustomerResponse.builder()
                        .email(customer.getEmail())
                        .gender(customer.getGender())
                        .fullName(customer.getFullName())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponse createNew(CreateCustomerRequest createCustomerRequest) {

        if (customerRepository.existsByEmail(createCustomerRequest.email())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Email already exists"
            );
        }

        if (customerRepository.existsByPhoneNumber(createCustomerRequest.phoneNumber())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "PhoneNumber already exists"
            );
        }




        Customer customer = customerMapper.fromCreateCustomerRequest(createCustomerRequest);
        customer.setIsDeleted(false);

        log.info("Customer ID Before save:{} " , customer.getId());
        customer = customerRepository.save(customer);
        log.info("Customer ID after save:{} " , customer.getId());


        return customerMapper.mapcustomerToCustomerResponse(customer);
    }
}
