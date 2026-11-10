package ca.lazanomentsoa.customerservice.repository;

import ca.lazanomentsoa.customerservice.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
