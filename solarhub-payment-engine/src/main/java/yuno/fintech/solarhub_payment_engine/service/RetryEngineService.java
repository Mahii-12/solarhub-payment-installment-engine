package yuno.fintech.solarhub_payment_engine.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yuno.fintech.solarhub_payment_engine.constants.InstallmentStatus;
import yuno.fintech.solarhub_payment_engine.dto.PaymentResult;
import yuno.fintech.solarhub_payment_engine.dto.RetrySummary;
import yuno.fintech.solarhub_payment_engine.entity.Installment;
import yuno.fintech.solarhub_payment_engine.entity.PaymentAttempt;
import yuno.fintech.solarhub_payment_engine.repository.InstallmentRepository;
import yuno.fintech.solarhub_payment_engine.repository.PaymentAttemptRepository;
import yuno.fintech.solarhub_payment_engine.util.DeclineReasonUtil;
import yuno.fintech.solarhub_payment_engine.util.RetryPolicy;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class RetryEngineService {

    private final InstallmentRepository installmentRepo;

    private final PaymentAttemptRepository attemptRepo;

    private final RetryPolicy retryPolicy;

    private final PaymentSimulationService simulator;

    public RetrySummary processRetries() {

        var dueInstallments =
                installmentRepo.findReadyForRetry(
                        LocalDateTime.now()
                );

        int processed = 0;

        for(var installment : dueInstallments) {

            retryInstallment(installment);

            processed++;
        }

        return new RetrySummary(processed);
    }

    private void retryInstallment(
            Installment installment
    ) {

        if(installment.getRetryCount() >= 5) {

            installment.setStatus(
                    InstallmentStatus.DELINQUENT
            );

            return;
        }

        if(installment.getDueDate()
                .plusDays(10)
                .isBefore(LocalDate.now())) {

            installment.setStatus(
                    InstallmentStatus.DELINQUENT
            );

            return;
        }

        var result =
                simulator.processRetry(
                        installment
                );

        saveAttempt(result, installment);

        if(result.success()) {

            installment.setStatus(
                    InstallmentStatus.PAID
            );

            return;
        }

        if(!DeclineReasonUtil.isSoft(
                result.reason()
        )) {

            installment.setStatus(
                    InstallmentStatus.DELINQUENT
            );

            return;
        }

        installment.setRetryCount(
                installment.getRetryCount()+1
        );

        installment.setStatus(
                InstallmentStatus.RETRYING
        );

        installment.setNextRetryAt(
                LocalDateTime.now()
                        .plus(
                                retryPolicy.nextRetryDelay(
                                        result.reason()
                                )
                        )
        );
    }

    private void saveAttempt(
            PaymentResult result,
            Installment installment) {

        PaymentAttempt attempt =
                new PaymentAttempt();

        attempt.setInstallment(
                installment
        );

        attempt.setAttemptedAt(
                LocalDateTime.now()
        );

        attempt.setAmount(
                installment.getAmount()
        );

        attempt.setSuccess(
                result.success()
        );

        attempt.setDeclineReason(
                result.reason()
        );

        attempt.setPaymentMethodType(
                installment.getSchedule()
                        .getPaymentMethodType()
        );

        attemptRepo.save(attempt);
    }
}
