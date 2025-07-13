package co.istad.mobliebankingapi.service;

import co.istad.mobliebankingapi.domain.Customer;
import co.istad.mobliebankingapi.dto.AccountDto;
import co.istad.mobliebankingapi.dto.CreateNewAccount;
import co.istad.mobliebankingapi.dto.UpdateAccountRequest;


public interface AccountService {



    AccountDto findByAccountNumber(String actNo);


    AccountDto createAccount(CreateNewAccount createNewAccount);


    void deleteByActNo(String actNo);
    void disableAccount(String actNo);



    Customer findByCustomer(Customer customer);

    AccountDto updateAccount(String actNo, UpdateAccountRequest updateAccountRequest);


}
