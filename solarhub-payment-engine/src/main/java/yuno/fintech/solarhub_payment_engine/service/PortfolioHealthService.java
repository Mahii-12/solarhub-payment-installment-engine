package yuno.fintech.solarhub_payment_engine.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yuno.fintech.solarhub_payment_engine.constants.InstallmentStatus;
import yuno.fintech.solarhub_payment_engine.dto.DeclineReasonCount;
import yuno.fintech.solarhub_payment_engine.dto.PortfolioHealthResponse;
import yuno.fintech.solarhub_payment_engine.repository.InstallmentRepository;
import yuno.fintech.solarhub_payment_engine.repository.PaymentAttemptRepository;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortfolioHealthService {

    private final InstallmentRepository installmentRepo;
    private final PaymentAttemptRepository attemptRepo;

    public PortfolioHealthResponse getHealth() {

        long paid =
                installmentRepo.countByStatus(
                        InstallmentStatus.PAID
                );

        long total =
                installmentRepo.count();

        double collectionRate =
                (double) paid / total * 100;

        long pendingRetry =
                installmentRepo.countByStatus(
                        InstallmentStatus.RETRYING
                );

        long delinquent =
                installmentRepo.countByStatus(
                        InstallmentStatus.DELINQUENT
                );

        Map<String, Long> breakdown =
                attemptRepo.getFailureBreakdown()
                        .stream()
                        .filter(row -> row[0] != null && row[1] != null)
                        .collect(Collectors.toMap(
                                row -> row[0].toString(),
                                row -> ((Number) row[1]).longValue(),
                                Long::sum
                        ));

        int estimatedRecovery =
                (int) (pendingRetry * 0.70);

        return PortfolioHealthResponse
                .builder()
                .collectionRate(collectionRate)
                .pendingRetryCount(pendingRetry)
                .delinquentCount(delinquent)
                .declineBreakdown(breakdown)
                .estimatedRecovery(estimatedRecovery)
                .build();
    }
}