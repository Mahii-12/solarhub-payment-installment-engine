package yuno.fintech.solarhub_payment_engine.dto;


import yuno.fintech.solarhub_payment_engine.constants.DeclineReason;


public record PaymentResult(
        boolean success,
        DeclineReason reason
) {

    public static PaymentResult successResult() {
        return new PaymentResult(true, null);
    }

    public static PaymentResult softFailureResult(
            DeclineReason reason) {
        return new PaymentResult(false, reason);
    }

    public static PaymentResult hardFailureResult(
            DeclineReason reason) {
        return new PaymentResult(false, reason);
    }
}