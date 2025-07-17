package co.istad.mobliebankingapi.service.impl;

import co.istad.mobliebankingapi.domain.Account;
import co.istad.mobliebankingapi.domain.AccountType;
import co.istad.mobliebankingapi.domain.Customer;
import co.istad.mobliebankingapi.dto.AccountResponse;
import co.istad.mobliebankingapi.dto.CreateAccountRequest;
import co.istad.mobliebankingapi.dto.CreateNewAccount;
import co.istad.mobliebankingapi.dto.UpdateAccountRequest;
import co.istad.mobliebankingapi.mapper.AccountMapper;
import co.istad.mobliebankingapi.repository.AccountRepository;
import co.istad.mobliebankingapi.repository.AccountTypeRepository;
import co.istad.mobliebankingapi.repository.CustomerRepository;
import co.istad.mobliebankingapi.service.AccountService;
import co.istad.mobliebankingapi.util.CurrencyUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Random;


@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final CustomerRepository customerRepository;
    private final AccountTypeRepository accountTypeRepository;


    @Override
    public AccountResponse createNew(CreateAccountRequest createAccountRequest) {


        Account account = new Account();

//        validate acc type
        AccountType accountType = accountTypeRepository
                .findByType(createAccountRequest.accountType())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Account Type Not Found"));


        Customer customer = customerRepository
                .findByPhoneNumber(createAccountRequest
                .phoneNumber())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Customer phone number not found"));


        switch (createAccountRequest.actCurrency()){

//            luy dollar
            case CurrencyUtil.USD ->{
    if (createAccountRequest.balance().compareTo(BigDecimal.TEN)<0){
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Balance Not Enough,Should be greater than 10$");
}
        if (customer.getCustomerSegment().getSegment().equals("Regular")){
    account.setOverLimit(BigDecimal.valueOf(5000));
        } else if (customer.getCustomerSegment().getSegment().equals("Silver")) {
    account.setOverLimit(BigDecimal.valueOf(10000));
        }else{
    account.setOverLimit(BigDecimal.valueOf(50000));
        }
            }


//            luy khmer
            case CurrencyUtil.KHR ->{

                if (createAccountRequest.balance().compareTo(BigDecimal.TEN)<0){

                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Balance Not Enough,Should be greater than 40000");
                }
        if (customer.getCustomerSegment().getSegment().equals("Regular")){
        account.setOverLimit(BigDecimal.valueOf(5000 * 4000));
        } else if (customer.getCustomerSegment().getSegment().equals("Silver")) {
                account.setOverLimit(BigDecimal.valueOf(10000 * 4000));
        }else{
            account.setOverLimit(BigDecimal.valueOf(50000 * 4000));
        }
            }
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Currency is not supported");
        }

//        validate acc number


        if (createAccountRequest.actNo() != null){
            if (accountRepository.existsByActNo(createAccountRequest.actNo())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,String.format("Account with ActNo %s already exists",createAccountRequest.actNo()));
            }
            account.setActNo(createAccountRequest.actNo());
        }else{
            String actNo;

            do {
                actNo = String.format("%09d", new Random().nextInt(1_000_000_000)); // Max: 999,999,999
            } while (accountRepository.existsByActNo(actNo));
            account.setActNo(actNo);
        }

        // Set data logic
        account.setActName(createAccountRequest.actName());
        account.setActCurrency(createAccountRequest.actCurrency().name());
        account.setBalance(createAccountRequest.balance());
        account.setIsHide(false);
        account.setIsDeleted(false);
        account.setCustomer(customer);
        account.setAccountType(accountType);

        account = accountRepository.save(account);

        return accountMapper.maptoAccountResponse(account);
    }

    @Override
    public AccountResponse findByAccountNumber(String actNo) {
        return accountRepository.findByActNo(actNo)
                .map(accountMapper::maptoAccountResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }



    @Override
    public AccountResponse createAccount(CreateNewAccount createNewAccount) {
        if (accountRepository.existsByActNo(createNewAccount.actNo())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Account with this number already exists.");
        }

        Account account = new Account();
        account.setActNo(createNewAccount.actNo());
        account.setBalance(createNewAccount.balance());

        log.info("Account ID before save: {}", account.getId());
        account = accountRepository.save(account);
        log.info("Account ID after save: {}", account.getId());

        return accountMapper.maptoAccountResponse(account);
    }



    @Override
    public void deleteByActNo(String actNo) {
        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account with number " + actNo + " not found."
                ));

        accountRepository.delete(account);

        log.info("Successfully deleted account with Account number: {}", actNo);
    }

    @Override
    public void disableAccount(String actNo) {
        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account with number " + actNo + " not found."
                ));

        account.setIsDeleted(true);
        accountRepository.save(account);

        log.info("Account with number {} has been disabled (isDeleted=true).", actNo);
    }

    @Override
    public Customer findByCustomer(Customer customer) {
        Account account = accountRepository.findByActNo(String.valueOf(customer))
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return account.getCustomer();
    }

    @Override
    public AccountResponse updateAccount(String actNo, UpdateAccountRequest updateAccountRequest) {
        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        accountMapper.toAccountPartially(updateAccountRequest, account);
       account = accountRepository.save(account);

       return accountMapper.maptoAccountResponse(account);




    }
}
