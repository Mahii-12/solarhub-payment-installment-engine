package yuno.fintech.solarhub_payment_engine.constants;

public enum DeclineReason {

    INSUFFICIENT_FUNDS,
    ISSUER_TIMEOUT,
    RATE_LIMIT_EXCEEDED,
    PROCESSOR_UNAVAILABLE,

    CARD_EXPIRED,
    CARD_LOST_STOLEN,
    INVALID_ACCOUNT,
    FRAUD_SUSPECTED,
    DO_NOT_HONOR
}
