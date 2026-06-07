package yuno.fintech.solarhub_payment_engine.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import yuno.fintech.solarhub_payment_engine.service.RetryEngineService;

@Component
@RequiredArgsConstructor
public class RetryScheduler {

    private final RetryEngineService retryEngine;

    @Scheduled(fixedDelay = 300000)
    public void execute() {

        retryEngine.processRetries();
    }
}
