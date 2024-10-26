package dev.ordy.erp.business.account;

import dev.ordy.erp.business.business.Business;
import dev.ordy.erp.business.account.enums.AccountCategory;
import dev.ordy.erp.business.account.enums.AccountType;
import dev.ordy.erp.common.Gender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;
    private final ApplicationEventPublisher eventPublisher;

    public AccountService(AccountRepository accountRepository,ApplicationEventPublisher eventPublisher) {
        this.accountRepository = accountRepository;
        this.eventPublisher = eventPublisher;
    }

    public Account createAccount(Account account) {
        accountRepository.save(account);
        eventPublisher.publishEvent(new AccountCreateEvent(this,account));
        return account;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    public Account updateAccount(Long id, Account updatedAccount) {
        return accountRepository.findById(id).map(account -> {
            account.setFirstName(updatedAccount.getFirstName());
            account.setLastName(updatedAccount.getLastName());
            account.setFullName(updatedAccount.getFullName());
            account.setRole(updatedAccount.getRole());
            account.setAccountType(updatedAccount.getAccountType());
            account.setAccountCategory(updatedAccount.getAccountCategory());
            account.setGender(updatedAccount.getGender());
            account.setBusiness(updatedAccount.getBusiness());
            return accountRepository.save(account);
        }).orElseThrow(() -> new RuntimeException("Account not found with id " + id));
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}
