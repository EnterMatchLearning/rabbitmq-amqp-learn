package org.springframework.amqp.tutorials.rabbitmqamqplearn.tut2;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"tut2-alt", "work-queues-alt"})
@Configuration
public class Tut2ConfigAlt {

    @Bean
    public Queue hello() {
        return new Queue("hello_spring");
    }

    @Profile("receiver")
    private static class ReceiverConfig {

        @Bean
        public Tut2ReceiverAlt receiver() {
            return new Tut2ReceiverAlt();
        }
    }

    @Profile("sender")
    @Bean
    public Tut2Sender sender() {
        return new Tut2Sender();
    }
}
