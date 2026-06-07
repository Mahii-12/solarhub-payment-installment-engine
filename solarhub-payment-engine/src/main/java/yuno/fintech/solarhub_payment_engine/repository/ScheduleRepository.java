package yuno.fintech.solarhub_payment_engine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yuno.fintech.solarhub_payment_engine.entity.InstallmentSchedule;

import java.util.UUID;

@Repository
public interface ScheduleRepository extends JpaRepository<InstallmentSchedule, UUID> {
}
