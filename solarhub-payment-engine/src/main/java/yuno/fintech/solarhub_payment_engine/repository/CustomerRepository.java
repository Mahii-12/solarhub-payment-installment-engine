package yuno.fintech.solarhub_payment_engine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yuno.fintech.solarhub_payment_engine.entity.Customer;

import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
