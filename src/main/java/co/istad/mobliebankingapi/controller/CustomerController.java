package co.istad.mobliebankingapi.controller;

import co.istad.mobliebankingapi.dto.CreateCustomerRequest;
import co.istad.mobliebankingapi.dto.CustomerResponse;
import co.istad.mobliebankingapi.repository.CustomerRepository;
import co.istad.mobliebankingapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerRepository customerRepository;


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{phoneNumber}")
    public void disableByPhoneNumber(@PathVariable
                                                     String phoneNumber) {
        customerService.disableByPhoneNumber(phoneNumber);
    }


    @GetMapping("/{phoneNumber}")
    public CustomerResponse findByPhoneNumber(

          @PathVariable  String phoneNumber) {
        return customerService.findByPhoneNumber(phoneNumber);

    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CustomerResponse createNew(@RequestBody CreateCustomerRequest createCustomerRequest) {
        return customerService.createNew(createCustomerRequest);
    }

    @GetMapping
    public List<CustomerResponse> getAllCustomers() {

        return customerService.getAllCustomers();
    }

    public List<CustomerResponse> findAll(){
        return customerService.findAll();
    }



}
