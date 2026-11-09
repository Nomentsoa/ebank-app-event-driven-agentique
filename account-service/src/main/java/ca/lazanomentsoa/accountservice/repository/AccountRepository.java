package ca.lazanomentsoa.accountservice.repository;

import ca.lazanomentsoa.accountservice.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
