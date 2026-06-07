package yuno.fintech.solarhub_payment_engine.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yuno.fintech.solarhub_payment_engine.dto.RetrySummary;
import yuno.fintech.solarhub_payment_engine.service.RetryEngineService;

@RestController
@RequestMapping("/api/retries")
@Tag(name = "Retry Engine", description = "Handles smart retry logic for failed payments")
@RequiredArgsConstructor
public class RetryController {

    private final RetryEngineService retryEngine;

    @PostMapping("/process")
    public RetrySummary process() {

        return retryEngine.processRetries();
    }
}
