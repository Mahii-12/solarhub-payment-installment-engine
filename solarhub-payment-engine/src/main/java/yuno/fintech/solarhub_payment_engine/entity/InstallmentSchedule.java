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
import yuno.fintech.solarhub_payment_engine.constants.CurrencyCode;
import yuno.fintech.solarhub_payment_engine.constants.PaymentMethodType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "installment_schedules")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class InstallmentSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Customer customer;

    private Integer totalInstallments;

    private BigDecimal installmentAmount;

    private LocalDate startDate;

    @Enumerated(EnumType.STRING)
    private CurrencyCode currency;

    @Enumerated(EnumType.STRING)
    private PaymentMethodType paymentMethodType;
}
