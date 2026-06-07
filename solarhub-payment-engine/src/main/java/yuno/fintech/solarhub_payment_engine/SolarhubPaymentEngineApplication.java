package yuno.fintech.solarhub_payment_engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SolarhubPaymentEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolarhubPaymentEngineApplication.class, args);
	}

}
