package yuno.fintech.solarhub_payment_engine.seed;

import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import yuno.fintech.solarhub_payment_engine.constants.Country;
import yuno.fintech.solarhub_payment_engine.constants.CurrencyCode;
import yuno.fintech.solarhub_payment_engine.constants.DeclineReason;
import yuno.fintech.solarhub_payment_engine.constants.InstallmentStatus;
import yuno.fintech.solarhub_payment_engine.constants.PaymentMethodType;
import yuno.fintech.solarhub_payment_engine.entity.Customer;
import yuno.fintech.solarhub_payment_engine.entity.Installment;
import yuno.fintech.solarhub_payment_engine.entity.InstallmentSchedule;
import yuno.fintech.solarhub_payment_engine.entity.PaymentAttempt;
import yuno.fintech.solarhub_payment_engine.repository.CustomerRepository;
import yuno.fintech.solarhub_payment_engine.repository.InstallmentRepository;
import yuno.fintech.solarhub_payment_engine.repository.PaymentAttemptRepository;
import yuno.fintech.solarhub_payment_engine.repository.ScheduleRepository;
import yuno.fintech.solarhub_payment_engine.service.PaymentSimulationService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class TestDataGenerator implements CommandLineRunner {

    private final CustomerRepository customerRepo;
    private final ScheduleRepository scheduleRepo;
    private final InstallmentRepository installmentRepo;
    private final PaymentAttemptRepository attemptRepo;

    private final Faker faker = new Faker();

    @Override
    public void run(String... args) {

        if (customerRepo.count() > 0) {
            return;
        }

        createCustomers(50);

        createSchedules(200);

        createAttempts(800);
    }

    private void createCustomers(int count) {

        for (int i = 0; i < count; i++) {

            Customer customer =
                    Customer.builder()
                            .firstName(
                                    faker.name().firstName())
                            .lastName(
                                    faker.name().lastName())
                            .email(
                                    faker.internet().emailAddress())
                            .country(randomCountry())
                            .riskScore(
                                    ThreadLocalRandom.current()
                                            .nextInt(0, 100))
                            .build();

            customerRepo.save(customer);
        }
    }

    private void createSchedules(int count) {

        List<Customer> customers =
                customerRepo.findAll();

        for (int i = 0; i < count; i++) {

            Customer customer =
                    customers.get(
                            ThreadLocalRandom.current()
                                    .nextInt(customers.size())
                    );

            InstallmentSchedule schedule =
                    InstallmentSchedule.builder()
                            .customer(customer)
                            .currency(
                                    randomCurrency(
                                            customer.getCountry()))
                            .paymentMethodType(
                                    randomMethod())
                            .totalInstallments(
                                    randomPlan())
                            .installmentAmount(
                                    randomAmount())
                            .startDate(
                                    LocalDate.now()
                                            .minusMonths(
                                                    ThreadLocalRandom
                                                            .current()
                                                            .nextInt(12)
                                            ))
                            .build();

            scheduleRepo.save(schedule);

            createInstallments(schedule);
        }
    }

    private void createAttempts(int count) {

        List<Installment> installments =
                installmentRepo.findAll();

        for (int i = 0; i < count; i++) {

            Installment installment =
                    installments.get(
                            ThreadLocalRandom.current()
                                    .nextInt(
                                            installments.size())
                    );

            PaymentAttempt attempt =
                    randomAttempt(installment);

            attemptRepo.save(attempt);
        }
    }

    private Country randomCountry() {
        return Country.values()[
                ThreadLocalRandom.current()
                        .nextInt(
                                Country.values().length)
                ];
    }

    private CurrencyCode randomCurrency(
            Country country) {

        return switch (country) {

            case MEXICO -> CurrencyCode.MXN;

            case COLOMBIA -> CurrencyCode.COP;

            case PERU -> CurrencyCode.PEN;
        };
    }

    private PaymentMethodType randomMethod() {

        return PaymentMethodType.values()[
                ThreadLocalRandom.current()
                        .nextInt(
                                PaymentMethodType.values().length)
                ];
    }

    private Integer randomPlan() {

        Integer[] plans = {12, 24, 36};

        return plans[
                ThreadLocalRandom.current()
                        .nextInt(plans.length)
                ];
    }

    private BigDecimal randomAmount() {

        return BigDecimal.valueOf(
                ThreadLocalRandom.current()
                        .nextDouble(50, 400)
        );
    }

    private void createInstallments(
            InstallmentSchedule schedule) {

        for (int i = 1;
             i <= schedule.getTotalInstallments();
             i++) {

            Installment installment =
                    Installment.builder()
                            .schedule(schedule)
                            .installmentNumber(i)
                            .amount(schedule.getInstallmentAmount())
                            .dueDate(
                                    schedule.getStartDate()
                                            .plusMonths(i - 1)
                            )
                            .retryCount(0)
                            .status(
                                    InstallmentStatus.PENDING
                            )
                            .build();

            installmentRepo.save(installment);
        }
    }

    private PaymentAttempt randomAttempt(
            Installment installment) {

        int probability =
                ThreadLocalRandom.current()
                        .nextInt(100);

        boolean success = probability < 65;

        PaymentAttempt attempt =
                new PaymentAttempt();

        attempt.setInstallment(installment);
        attempt.setAttemptedAt(
                LocalDateTime.now()
                        .minusDays(
                                ThreadLocalRandom.current()
                                        .nextInt(30))
        );

        attempt.setAmount(
                installment.getAmount()
        );

        attempt.setPaymentMethodType(
                installment.getSchedule()
                        .getPaymentMethodType()
        );

        attempt.setSuccess(success);

        if (!success) {

            attempt.setDeclineReason(
                    randomDeclineReason()
            );
        }

        return attempt;
    }

    private DeclineReason randomDeclineReason() {

        int value =
                ThreadLocalRandom.current()
                        .nextInt(100);

        if (value < 70) {

            return PaymentSimulationService.randomSoftReason();
        }

        return PaymentSimulationService.randomHardReason();
    }
}
