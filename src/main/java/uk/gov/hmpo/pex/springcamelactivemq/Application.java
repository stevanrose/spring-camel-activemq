package uk.gov.hmpo.pex.springcamelactivemq;

import org.apache.camel.LoggingLevel;
import org.apache.camel.processor.RedeliveryPolicy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public RedeliveryPolicy redeliveryPolicy() {
		RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
		redeliveryPolicy.setRedeliveryDelay(5000);
		redeliveryPolicy.setMaximumRedeliveries(2);
		redeliveryPolicy.setRetryAttemptedLogLevel(LoggingLevel.WARN);
		redeliveryPolicy.setRetriesExhaustedLogLevel(LoggingLevel.WARN);
		return redeliveryPolicy;
	}
}
