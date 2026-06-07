package yuno.fintech.solarhub_payment_engine.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yuno.fintech.solarhub_payment_engine.constants.Country;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private Country country;

    private Integer riskScore;
}
