package uk.gov.hmpo.pex.springcamelactivemq.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.RedeliveryPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.hmpo.pex.springcamelactivemq.processors.RandomErrorProcessor;

import java.io.IOException;

@Component
public class NotificationRouteBuilder extends RouteBuilder {

    @Autowired
    private RandomErrorProcessor randomErrorProcessor;

    @Override
    public void configure() throws Exception {

        onException(IOException.class)
                .maximumRedeliveries(5)
                .maximumRedeliveryDelay(5000)
                .retryAttemptedLogLevel(LoggingLevel.WARN)
                .retriesExhaustedLogLevel(LoggingLevel.WARN)
                .useOriginalMessage()
                .handled(true)
                .to("log:error")
                .to("activemq:error");

        errorHandler(deadLetterChannel("activemq:deadLetter")
                .useOriginalMessage());

        from("activemq:input")
                .process(randomErrorProcessor)
                .to("log:success")
                .to("activemq:success");

        from("timer:bar")
                .setBody(constant("Hello from Camel"))
                .to("activemq:input");

    }
}
