package dev.ordy.erp.business.account;

import dev.ordy.erp.business.business.Business;
import org.springframework.context.ApplicationEvent;

public class AccountCreateEvent extends ApplicationEvent {

    private final Account account;

    public AccountCreateEvent(Object source, Account account) {
        super(source);
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}

