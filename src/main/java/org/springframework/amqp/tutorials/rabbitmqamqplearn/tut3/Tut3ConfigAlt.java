package org.springframework.amqp.tutorials.rabbitmqamqplearn.tut3;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"tut3-alt", "pub-sub-alt", "publish-subscribe-alt"})
@Configuration
public class Tut3ConfigAlt {

    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("tut.fanout");
    }

    @Bean
    public Queue helloQueue() {
        return new Queue("hello", false);
    }

    @Bean
    public Queue helloSpringQueue() {
        return new Queue("hello_spring");
    }

    @Bean
    public Binding binding1(FanoutExchange fanout, Queue helloQueue) {
        return BindingBuilder.bind(helloQueue).to(fanout);
    }

    @Bean
    public Binding binding2(FanoutExchange fanout, Queue helloSpringQueue) {
        return BindingBuilder.bind(helloSpringQueue).to(fanout);
    }

    @Profile("receiver")
    private static class ReceiverConfig {

        @Bean
        public Tut3ReceiverAlt receiver() {
            return new Tut3ReceiverAlt();
        }
    }

    @Profile("sender")
    @Bean
    public Tut3Sender sender() {
        return new Tut3Sender();
    }
}
