package ca.lazanomentsoa.accountservice.mcp;

import ca.lazanomentsoa.accountservice.domain.Account;
import ca.lazanomentsoa.accountservice.domain.AccountTransaction;
import ca.lazanomentsoa.accountservice.domain.AccountType;
import ca.lazanomentsoa.accountservice.service.EBankService;

import org.springaicommunity.mcp.annotation.McpArg;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EBankMCPController {
    private final EBankService eBankService;
    public EBankMCPController(EBankService eBankService) {
        this.eBankService = eBankService;
    }

    @McpTool(description = "Get Account by id")
    public Account getAccountById(@McpArg(description = "The account id") String accountId) {
        return eBankService.getAccountById(accountId);
    }

    @McpTool(description = "Get All Accounts")
    public List<Account> getAllAccounts() {
        return eBankService.getAllAccounts();
    }

    @McpTool(description = "Save new Account")
    public Account saveNewAccount(@McpArg(description = "The initial balance") double initialBalance,
                                  @McpArg(description = "The Account type") AccountType type,
                                  @McpArg(description = "The Account currency")String currency,
                                  @McpArg(description = "The customer id") Long customerId) {
        return eBankService.saveNewAccount(initialBalance, type, currency, customerId);
    }

    @McpTool(description = "Credit Account")
    public AccountTransaction credit( @McpArg(description = "The account id") String accountId,
                                      @McpArg(description = "The credited amount") double amount) {
        return eBankService.credit(accountId, amount);
    }

    @McpTool(description = "Debit Account")
    public AccountTransaction debit(@McpArg(description = "The account id") String accountId,
                                    @McpArg(description = "The debited amount") double amount) {
        return eBankService.debit(accountId, amount);
    }




}
