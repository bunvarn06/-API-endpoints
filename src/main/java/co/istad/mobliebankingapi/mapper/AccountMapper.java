package co.istad.mobliebankingapi.mapper;


import co.istad.mobliebankingapi.domain.Account;
import co.istad.mobliebankingapi.dto.AccountResponse;
import co.istad.mobliebankingapi.dto.CreateNewAccount;
import co.istad.mobliebankingapi.dto.UpdateAccountRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toAccountPartially(UpdateAccountRequest updateAccountRequest, @MappingTarget Account account);

    @Mapping(target = "accountType", expression = "java(account.getAccountType().getType())")
    AccountResponse maptoAccountResponse(Account account);


    Account fromCreateAccountRequest(CreateNewAccount createNewAccount);


}
