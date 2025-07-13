package co.istad.mobliebankingapi.controller;

import co.istad.mobliebankingapi.domain.Customer;
import co.istad.mobliebankingapi.dto.AccountDto;
import co.istad.mobliebankingapi.dto.CreateNewAccount;
import co.istad.mobliebankingapi.dto.UpdateAccountRequest;
import co.istad.mobliebankingapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v2/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;




  @PutMapping
    public AccountDto updateAccount(
            @RequestBody UpdateAccountRequest updateAccountRequest) {
        return accountService.updateAccount(updateAccountRequest.actNo(), updateAccountRequest);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AccountDto createAccount(
            @RequestBody CreateNewAccount createNewAccount) {
        return accountService.createAccount(createNewAccount);
    }


    @GetMapping({"/{actNo}"})
    public AccountDto findByAccountNumber(
           @PathVariable String actNo) {
      return accountService.findByAccountNumber(actNo);
    }

    @GetMapping({"/{customer}"})
    public Customer findByCustomer(@PathVariable Customer customer) {
      return accountService.findByCustomer(customer);
    }

}
