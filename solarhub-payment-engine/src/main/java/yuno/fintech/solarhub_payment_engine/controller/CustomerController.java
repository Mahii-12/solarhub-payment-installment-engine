package yuno.fintech.solarhub_payment_engine.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yuno.fintech.solarhub_payment_engine.dto.CustomerDetailsResponse;
import yuno.fintech.solarhub_payment_engine.dto.CustomerDto;
import yuno.fintech.solarhub_payment_engine.service.CustomerService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customers", description = "Customer management and profile APIs")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public List<CustomerDto> getAll() {

        return customerService.getAll();
    }

    @GetMapping("/{id}")
    public CustomerDetailsResponse getCustomer(
            @PathVariable UUID id) {

        return customerService.getDetails(id);
    }
}
