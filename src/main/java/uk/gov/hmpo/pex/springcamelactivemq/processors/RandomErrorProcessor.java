package uk.gov.hmpo.pex.springcamelactivemq.processors;


import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Random;

@Component
@Slf4j
public class RandomErrorProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        Random random = new Random();
        if (!random.nextBoolean()) {
            if(!random.nextBoolean()) {
                log.info("Throwing random exception");
                throw new Exception("error");
            }
            log.info("Throwing IOException");
            throw new IOException("error");
        }

    }

}
