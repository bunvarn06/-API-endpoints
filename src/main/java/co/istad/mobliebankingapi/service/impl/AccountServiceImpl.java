package co.istad.mobliebankingapi.service.impl;

import co.istad.mobliebankingapi.domain.Account;
import co.istad.mobliebankingapi.domain.Customer;
import co.istad.mobliebankingapi.dto.AccountDto;
import co.istad.mobliebankingapi.dto.CreateNewAccount;
import co.istad.mobliebankingapi.dto.UpdateAccountRequest;
import co.istad.mobliebankingapi.mapper.AccountMapper;
import co.istad.mobliebankingapi.repository.AccountRepository;
import co.istad.mobliebankingapi.repository.CustomerRepository;
import co.istad.mobliebankingapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final CustomerRepository customerRepository;




    @Override
    public AccountDto findByAccountNumber(String actNo) {
        return accountRepository.findByActNo(actNo)
                .map(accountMapper::maptoAccountDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }



    @Override
    public AccountDto createAccount(CreateNewAccount createNewAccount) {
        if (accountRepository.existsByActNo(createNewAccount.actNo())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Account with this number already exists.");
        }

        Account account = new Account();
        account.setActNo(createNewAccount.actNo());
        account.setBalance(createNewAccount.balance());

        log.info("Account ID before save: {}", account.getId());
        account = accountRepository.save(account);
        log.info("Account ID after save: {}", account.getId());

        return accountMapper.maptoAccountDto(account);
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
    public AccountDto updateAccount(String actNo, UpdateAccountRequest updateAccountRequest) {
        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        accountMapper.toAccountPartially(updateAccountRequest, account);
       account = accountRepository.save(account);

       return accountMapper.maptoAccountDto(account);




    }
}
