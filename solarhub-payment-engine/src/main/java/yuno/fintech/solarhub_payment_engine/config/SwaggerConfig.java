package yuno.fintech.solarhub_payment_engine.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI solarHubOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("SolarHub Payment Installment Engine API")
                        .description("""
                                Backend system for managing installment-based solar financing payments.
                                
                                Features:
                                - Installment schedule management
                                - Smart retry engine for failed payments
                                - Portfolio health analytics
                                - Payment attempt tracking
                                - Risk scoring & recovery insights
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("SolarHub Engineering Team")
                                .email("engineering@solarhub.com")
                        )
                        .license(new License()
                                .name("Internal Use Only")
                        )
                );
    }
}