package yuno.fintech.solarhub_payment_engine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import yuno.fintech.solarhub_payment_engine.dto.DeclineReasonCount;
import yuno.fintech.solarhub_payment_engine.entity.PaymentAttempt;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentAttemptRepository extends JpaRepository<PaymentAttempt, UUID> {

    List<PaymentAttempt> findByInstallmentId(UUID installmentId);

    @Query("""
           SELECT p.declineReason,
                  COUNT(p)
           FROM PaymentAttempt p
           WHERE p.success = false
           GROUP BY p.declineReason
           """)
    List<Object[]> getFailureBreakdown();
}
