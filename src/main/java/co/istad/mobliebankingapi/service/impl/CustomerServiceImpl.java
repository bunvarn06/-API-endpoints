package co.istad.mobliebankingapi.service.impl;

import co.istad.mobliebankingapi.domain.Customer;

import co.istad.mobliebankingapi.domain.CustomerSegment;
import co.istad.mobliebankingapi.domain.KYC;
import co.istad.mobliebankingapi.dto.CreateCustomerRequest;
import co.istad.mobliebankingapi.dto.CustomerResponse;
import co.istad.mobliebankingapi.dto.UpdateCustomerRequest;
import co.istad.mobliebankingapi.mapper.CustomerMapper;
import co.istad.mobliebankingapi.repository.CustomerRepository;
import co.istad.mobliebankingapi.repository.CustomerSegmentRepository;
import co.istad.mobliebankingapi.repository.KYCRepository;
import co.istad.mobliebankingapi.service.CustomerService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;



@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CustomerSegmentRepository customerSegmentRepository;
    private final KYCRepository kycRepository;

    @Transactional
    @Override
    public void disableByPhoneNumber(String phoneNumber) {
        if (!customerRepository.existsByPhoneNumber(phoneNumber)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Phone number not found");
        }
        customerRepository.disableByPhoneNumber(phoneNumber);
    }


    @Override
    public CustomerResponse updateByPhoneNumber(String phoneNumber,
                                                UpdateCustomerRequest updateCustomerRequest) {
        Customer customer = customerRepository
                .findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResponseStatusException
                        (HttpStatus.NOT_FOUND, "Phone number not found"));

        customerMapper.toCustomerPartially(
                updateCustomerRequest, customer
        );
        customer = customerRepository.save(customer);
        return customerMapper.mapcustomerToCustomerResponse(customer);
    }

    @Override
    public CustomerResponse findByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumberAndIsDeletedFalse(phoneNumber)
                .map(customerMapper::mapcustomerToCustomerResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::mapcustomerToCustomerResponse).toList();
    }

    @Override
    public List<CustomerResponse> findAll() {
        List<Customer> customers = customerRepository
                .findAllByIsDeletedFalse();
        return customers
                .stream()
                .map(customerMapper::mapcustomerToCustomerResponse).toList();
    }


    public CustomerResponse createNew(CreateCustomerRequest createCustomerRequest) {

        if (customerRepository.existsByEmail(createCustomerRequest.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        if (customerRepository.existsByPhoneNumber(createCustomerRequest.phoneNumber())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number already exists");
        }
        CustomerSegment customerSegment = customerSegmentRepository
                .getCustomerSegmentBySegment(createCustomerRequest.segment())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Segment not found"));

        Customer customer = customerMapper.fromCreateCustomerRequestToCustomer(createCustomerRequest);
        customer.setIsDeleted(false);
        customer.setCustomerSegment(customerSegment);

        customer = customerRepository.save(customer);
        customerRepository.flush();

        if (!kycRepository.existsByNationalCardId(createCustomerRequest.nationalCardId())) {
            KYC kyc = new KYC();
            kyc.setNationalCardId(createCustomerRequest.nationalCardId());
            kyc.setIsVerified(false);
            kyc.setIsDeleted(false);
            kyc.setCustomer(customer);

            kycRepository.save(kyc);

            return customerMapper.fromCustomerToCustomerResponse(customer);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "National card already exists");
        }

    }

}