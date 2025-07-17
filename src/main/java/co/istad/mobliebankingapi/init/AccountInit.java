package co.istad.mobliebankingapi.init;


import co.istad.mobliebankingapi.domain.AccountType;
import co.istad.mobliebankingapi.repository.AccountTypeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountInit {


    private final AccountTypeRepository accountTypeRepository;

    @PostConstruct
    public void init() {
        if (accountTypeRepository.count() == 0) {
            AccountType payroll = new AccountType();
            payroll.setType("PAYROLL");
            payroll.setIsDeleted(false);

            AccountType saving = new AccountType();
            saving.setType("SAVING");
            saving.setIsDeleted(false);

            AccountType junior = new AccountType();
            junior.setType("JUNIOR");
            junior.setIsDeleted(false);

            accountTypeRepository.saveAll(List.of(payroll, saving, junior));
        }

    }
}