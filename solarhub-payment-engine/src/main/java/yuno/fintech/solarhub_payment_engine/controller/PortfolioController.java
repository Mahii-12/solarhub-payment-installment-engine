package yuno.fintech.solarhub_payment_engine.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yuno.fintech.solarhub_payment_engine.dto.PortfolioHealthResponse;
import yuno.fintech.solarhub_payment_engine.service.PortfolioHealthService;


@RestController
@RequestMapping("/api/portfolio")
@Tag(name = "Portfolio Analytics", description = "Financial health and collection metrics")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioHealthService service;

    @GetMapping("/health")
    public PortfolioHealthResponse health() {

        return service.getHealth();
    }
}
