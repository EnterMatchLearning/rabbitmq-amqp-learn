package org.springframework.amqp.tutorials.rabbitmqamqplearn.tut5;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

public class Tut5Sender {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private TopicExchange topic;

    AtomicInteger index = new AtomicInteger(0);
    AtomicInteger count = new AtomicInteger(0);
    AtomicInteger dots = new AtomicInteger(0);

    private final String[] keys = {"quick.orange.rabbit",
            "lazy.orange.elephant",
            "quick.orange.fox",
            "lazy.brown.fox",
            "lazy.pink.rabbit",
            "quick.brown.fox"};

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        StringBuilder builder = new StringBuilder("Hello to ");
        if (dots.getAndIncrement() == 3) {
            dots.set(1);
        }
        if (index.incrementAndGet() == keys.length) {
            index.set(0);
        }
        builder.append(keys[index.get()]);
        builder.append(".".repeat(Math.max(0, dots.get())));
        builder.append(count.incrementAndGet());
        String message = builder.toString();
        template.convertAndSend(topic.getName(), keys[index.get()], message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}
