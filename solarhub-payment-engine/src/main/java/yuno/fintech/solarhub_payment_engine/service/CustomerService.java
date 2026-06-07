package yuno.fintech.solarhub_payment_engine.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yuno.fintech.solarhub_payment_engine.dto.CustomerDetailsResponse;
import yuno.fintech.solarhub_payment_engine.dto.CustomerDto;
import yuno.fintech.solarhub_payment_engine.entity.Customer;
import yuno.fintech.solarhub_payment_engine.repository.CustomerRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepo;

    public List<CustomerDto> getAll() {

        return customerRepo.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    public CustomerDetailsResponse getDetails(
            UUID customerId) {

        Customer customer =
                customerRepo.findById(customerId)
                        .orElseThrow();

        return CustomerDetailsResponse
                .builder()
                .customer(map(customer))
                .build();
    }

    private CustomerDto map(
            Customer customer) {

        return CustomerDto.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .country(customer.getCountry())
                .riskScore(customer.getRiskScore())
                .build();
    }
}
