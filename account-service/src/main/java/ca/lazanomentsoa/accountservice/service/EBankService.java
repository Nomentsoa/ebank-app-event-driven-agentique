package ca.lazanomentsoa.accountservice.service;


import ca.lazanomentsoa.accountservice.domain.*;
import ca.lazanomentsoa.accountservice.exception.NegativeAmountException;
import ca.lazanomentsoa.accountservice.repository.AccountRepository;
import ca.lazanomentsoa.accountservice.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class EBankService {
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    public EBankService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Account saveNewAccount(double initialBalance, AccountType type, String currency, Long customerId){
        if(initialBalance < 0)
            throw new NegativeAmountException("The amount should be positive");

        Account account = Account.builder()
                .id(UUID.randomUUID().toString())
                .currency(currency)
                .type(type)
                .status(AccountStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .customerId(customerId)
                .balance(initialBalance)
                .build();


        Account savedAccount = accountRepository.save(account);
        AccountTransaction transaction = AccountTransaction.builder()
                .amount(initialBalance)
                .type(TransactionType.CREDIT)
                .dateTime(LocalDateTime.now())
                .account(savedAccount)
                .build();
        transactionRepository.save(transaction);
        return savedAccount;

    }

}
