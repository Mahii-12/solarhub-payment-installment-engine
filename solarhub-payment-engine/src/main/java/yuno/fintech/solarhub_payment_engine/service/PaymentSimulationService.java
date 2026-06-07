package yuno.fintech.solarhub_payment_engine.service;

import org.springframework.stereotype.Service;
import yuno.fintech.solarhub_payment_engine.constants.DeclineReason;
import yuno.fintech.solarhub_payment_engine.dto.PaymentResult;
import yuno.fintech.solarhub_payment_engine.entity.Installment;

import java.util.Random;

@Service
public class PaymentSimulationService {

    private static final Random random =
            new Random();

    public PaymentResult processRetry(
            Installment installment) {

        int value =
                random.nextInt(100);

        if (value < 30) {

            return PaymentResult.successResult();
        }

        if (value < 80) {

            return PaymentResult.softFailureResult(
                    randomSoftReason()
            );
        }

        return PaymentResult.hardFailureResult(
                randomHardReason()
        );
    }

    public static DeclineReason randomSoftReason() {

        DeclineReason[] reasons = {

                DeclineReason.INSUFFICIENT_FUNDS,

                DeclineReason.ISSUER_TIMEOUT,

                DeclineReason.RATE_LIMIT_EXCEEDED,

                DeclineReason.PROCESSOR_UNAVAILABLE
        };

        return reasons[
                random.nextInt(
                        reasons.length)
                ];
    }

    public static DeclineReason randomHardReason() {

        DeclineReason[] reasons = {

                DeclineReason.CARD_EXPIRED,

                DeclineReason.INVALID_ACCOUNT,

                DeclineReason.FRAUD_SUSPECTED,

                DeclineReason.DO_NOT_HONOR
        };

        return reasons[
                random.nextInt(
                        reasons.length)
                ];
    }
}