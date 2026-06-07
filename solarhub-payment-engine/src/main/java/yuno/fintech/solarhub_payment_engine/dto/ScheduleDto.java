package yuno.fintech.solarhub_payment_engine.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDto {

    private UUID id;

    private Integer totalInstallments;

    private BigDecimal installmentAmount;

    private LocalDate startDate;

    private String currency;

    private String paymentMethodType;
}