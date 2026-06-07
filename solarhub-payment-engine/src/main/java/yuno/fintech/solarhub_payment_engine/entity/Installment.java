package yuno.fintech.solarhub_payment_engine.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yuno.fintech.solarhub_payment_engine.constants.InstallmentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "installments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Installment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private InstallmentSchedule schedule;

    private Integer installmentNumber;

    private LocalDate dueDate;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private InstallmentStatus status;

    private Integer retryCount;

    private LocalDateTime nextRetryAt;

    private LocalDate delinquentAt;
}