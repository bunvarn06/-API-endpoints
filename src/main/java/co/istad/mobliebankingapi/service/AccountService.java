package co.istad.mobliebankingapi.service;

import co.istad.mobliebankingapi.domain.Account;
import co.istad.mobliebankingapi.domain.Customer;
import co.istad.mobliebankingapi.dto.AccountDto;
import co.istad.mobliebankingapi.dto.CreateNewAccount;
import co.istad.mobliebankingapi.dto.CustomerResponse;
import co.istad.mobliebankingapi.dto.UpdateAccountRequest;

import java.util.List;

public interface AccountService {


    AccountDto updateAccount(String actNo,UpdateAccountRequest updateAccountRequest);
    AccountDto findByActNo(String actNo);

//   CustomerResponse findByCustomer(Customer customer);
//    Customer findByCustomer(CustomerResponse customerResponse);

    AccountDto createAccount(CreateNewAccount createNewAccount);


    void deleteByActNo(String actNo);
    void disableAccount(String actNo);
    List<Customer> findByCustomer();
}
