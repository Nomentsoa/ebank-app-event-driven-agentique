package ca.lazanomentsoa.accountservice.rest;

import ca.lazanomentsoa.accountservice.domain.Account;
import ca.lazanomentsoa.accountservice.domain.AccountTransaction;
import ca.lazanomentsoa.accountservice.domain.AccountType;
import ca.lazanomentsoa.accountservice.service.EBankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class EBankRestController {
    private final EBankService eBankService;
    public EBankRestController(EBankService eBankService) {
        this.eBankService = eBankService;
    }

    @GetMapping("/accounts/{accountId}")
    public Account getAccountById(@PathVariable String accountId) {
        return eBankService.getAccountById(accountId);
    }

    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {
        return eBankService.getAllAccounts();
    }

    @PostMapping("/accounts/save")
    public Account saveNewAccount(double initialBalance, AccountType type, String currency, Long customerId) {
        return eBankService.saveNewAccount(initialBalance, type, currency, customerId);
    }

    @PostMapping("/accounts/credit")
    public AccountTransaction credit(String accountId, double amount) {
        return eBankService.credit(accountId, amount);
    }

    @PostMapping("/accounts/debit")
    public AccountTransaction debit(String accountId, double amount) {
        return eBankService.debit(accountId, amount);
    }

    //gestion global de tous les exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler(Exception e){
        return ResponseEntity.internalServerError().body(Map.of("errorMessage", e.getMessage()));
    }


}
