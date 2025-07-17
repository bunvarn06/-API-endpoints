package co.istad.mobliebankingapi.service;

import co.istad.mobliebankingapi.domain.Customer;
import co.istad.mobliebankingapi.dto.AccountResponse;
import co.istad.mobliebankingapi.dto.CreateAccountRequest;
import co.istad.mobliebankingapi.dto.CreateNewAccount;
import co.istad.mobliebankingapi.dto.UpdateAccountRequest;


public interface AccountService {


    AccountResponse createNew(CreateAccountRequest createAccountRequest);

    AccountResponse findByAccountNumber(String actNo);


    AccountResponse createAccount(CreateNewAccount createNewAccount);


    void deleteByActNo(String actNo);
    void disableAccount(String actNo);



    Customer findByCustomer(Customer customer);

    AccountResponse updateAccount(String actNo, UpdateAccountRequest updateAccountRequest);


}
