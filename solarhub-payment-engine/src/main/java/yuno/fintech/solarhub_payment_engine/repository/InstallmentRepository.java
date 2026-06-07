package yuno.fintech.solarhub_payment_engine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import yuno.fintech.solarhub_payment_engine.constants.InstallmentStatus;
import yuno.fintech.solarhub_payment_engine.entity.Installment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface InstallmentRepository
        extends JpaRepository<Installment, UUID> {

    @Query("""
             select i
             from Installment i
             where i.status='RETRYING'
             and i.nextRetryAt <= :now
            """)
    List<Installment> findReadyForRetry(
            LocalDateTime now
    );

    List<Installment> findByScheduleId(UUID scheduleId);

    long countByStatus(
            InstallmentStatus status
    );
}
