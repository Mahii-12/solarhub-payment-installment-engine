package yuno.fintech.solarhub_payment_engine.util;

import org.springframework.stereotype.Component;
import yuno.fintech.solarhub_payment_engine.constants.DeclineReason;

@Component
public class DeclineReasonUtil {

    public static boolean isSoft(DeclineReason reason) {

        return switch (reason) {

            case INSUFFICIENT_FUNDS,
                 ISSUER_TIMEOUT,
                 RATE_LIMIT_EXCEEDED,
                 PROCESSOR_UNAVAILABLE -> true;

            default -> false;
        };
    }


}
