package yuno.fintech.solarhub_payment_engine.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstallmentDto {

    private UUID id;

    private Integer installmentNumber;

    private LocalDate dueDate;

    private BigDecimal amount;

    private String status;

    private Integer retryCount;
}
