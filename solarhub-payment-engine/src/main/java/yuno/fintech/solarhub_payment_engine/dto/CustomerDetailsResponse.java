package yuno.fintech.solarhub_payment_engine.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetailsResponse {

    private CustomerDto customer;

    private List<ScheduleDto> schedules;

    private List<InstallmentDto> installments;
}
