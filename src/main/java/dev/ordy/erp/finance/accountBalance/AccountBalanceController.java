package dev.ordy.erp.finance.accountBalance;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account-balances")
class AccountBalanceController {

    private final AccountBalanceRepository repository;

    AccountBalanceController(AccountBalanceRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping
    List<AccountBalance> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping
    AccountBalance newAccountBalance(@RequestBody AccountBalance newAccountBalance) {
        return repository.save(newAccountBalance);
    }

    // Single item

    @GetMapping("/{id}")
    AccountBalance one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new AccountBalanceNotFoundException(id));
    }

    @PutMapping("/{id}")
    AccountBalance replaceAccountBalance(@RequestBody AccountBalance newAccountBalance, @PathVariable Long id) {

        return repository.findById(id)
                .map(accountBalance -> {
                    accountBalance.setRole(newAccountBalance.getRole());
                    return repository.save(accountBalance);
                })
                .orElseGet(() -> {
                    newAccountBalance.setId(id);
                    return repository.save(newAccountBalance);
                });
    }

    @DeleteMapping("/{id}")
    void deleteAccountBalance(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
