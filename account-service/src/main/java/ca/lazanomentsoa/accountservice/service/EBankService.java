package ca.lazanomentsoa.accountservice.service;


import ca.lazanomentsoa.accountservice.domain.*;
import ca.lazanomentsoa.accountservice.exception.AccountNotFoundException;
import ca.lazanomentsoa.accountservice.exception.BalanceNotSufficientException;
import ca.lazanomentsoa.accountservice.exception.NegativeAmountException;
import ca.lazanomentsoa.accountservice.repository.AccountRepository;
import ca.lazanomentsoa.accountservice.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

// Paterne CQRS (Command Query Responsability Segregation), il faut separer les methodes ecritures des ecritures
@Service
@Transactional
public class EBankService {
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    public EBankService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }


    public Account getAccountById(String accountId){
        Account account = accountRepository.findById(accountId).orElseThrow(() ->
                new AccountNotFoundException("Account not found")
        );
        return account;
    }

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    public Account saveNewAccount(double initialBalance, AccountType type, String currency, Long customerId){
        if(initialBalance <=0)
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

    public AccountTransaction credit(String accountId, double amount){
        if(amount <=0) throw new NegativeAmountException("Amount should be positive");

        Account account = getAccountById(accountId);
        account.setBalance(account.getBalance() + amount);
        Account savedAccount = accountRepository.save(account);

        AccountTransaction accountTransaction = AccountTransaction.builder()
                .account(savedAccount)
                .amount(amount)
                .type(TransactionType.CREDIT)
                .dateTime(LocalDateTime.now())
                .build();

        return transactionRepository.save(accountTransaction);

    }

    public AccountTransaction debit(String accountId, double amount){
        if(amount <=0) throw new NegativeAmountException("Amount should be positive");
        Account account = getAccountById(accountId);
        if(account.getBalance() < amount) throw new BalanceNotSufficientException("Balance Not SufficientException");
        account.setBalance(account.getBalance() - amount);
        Account savedAccount = accountRepository.save(account);

        AccountTransaction accountTransaction = AccountTransaction.builder()
                .account(savedAccount)
                .amount(amount)
                .type(TransactionType.DEBIT)
                .dateTime(LocalDateTime.now())
                .build();

        return transactionRepository.save(accountTransaction);

    }

}
