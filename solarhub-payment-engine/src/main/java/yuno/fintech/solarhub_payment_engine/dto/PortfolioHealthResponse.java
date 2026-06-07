package yuno.fintech.solarhub_payment_engine.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class PortfolioHealthResponse {

    private Double collectionRate;

    private Long pendingRetryCount;

    private Long delinquentCount;

    private Map<String, Long> declineBreakdown;

    private Integer estimatedRecovery;

    private Map<String, Double> paymentMethodSuccessRates;
}
