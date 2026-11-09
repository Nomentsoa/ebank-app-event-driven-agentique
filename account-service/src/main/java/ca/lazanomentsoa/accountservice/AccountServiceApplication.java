package ca.lazanomentsoa.accountservice;

import ca.lazanomentsoa.accountservice.domain.AccountType;
import ca.lazanomentsoa.accountservice.service.EBankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(EBankService eBankService) {
        return args -> {
            eBankService.saveNewAccount(65000, AccountType.CURRENT_ACCOUNT, "CAD", Long.valueOf(1));
            eBankService.saveNewAccount(12000, AccountType.SAVING_ACCOUNT, "CAD", Long.valueOf(1));
            eBankService.saveNewAccount(34000, AccountType.CURRENT_ACCOUNT, "CAD", Long.valueOf(1));

            eBankService.getAllAccounts().forEach(acc -> {
                for (int i = 0; i < 5; i++) {
					eBankService.credit(acc.getId(), 500 + Math.random() * 230000);
					eBankService.debit(acc.getId(), 500 + Math.random() * 12000);
                }
            });
        };
    }

}
