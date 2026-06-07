package yuno.fintech.solarhub_payment_engine.util;

import org.springframework.stereotype.Component;
import yuno.fintech.solarhub_payment_engine.constants.DeclineReason;

import java.time.Duration;

@Component
public class RetryPolicy {

    public Duration nextRetryDelay(DeclineReason reason) {

        return switch (reason) {

            case INSUFFICIENT_FUNDS -> Duration.ofDays(3);

            case ISSUER_TIMEOUT,
                 PROCESSOR_UNAVAILABLE -> Duration.ofHours(2);

            case RATE_LIMIT_EXCEEDED -> Duration.ofHours(6);

            default -> Duration.ZERO;
        };
    }
}