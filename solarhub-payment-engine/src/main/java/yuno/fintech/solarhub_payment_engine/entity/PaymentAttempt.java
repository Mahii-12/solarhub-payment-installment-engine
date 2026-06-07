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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yuno.fintech.solarhub_payment_engine.constants.DeclineReason;
import yuno.fintech.solarhub_payment_engine.constants.PaymentMethodType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payment_attempts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Installment installment;

    private LocalDateTime attemptedAt;

    private BigDecimal amount;

    private boolean success;

    @Enumerated(EnumType.STRING)
    private PaymentMethodType paymentMethodType;

    @Enumerated(EnumType.STRING)
    private DeclineReason declineReason;
}
