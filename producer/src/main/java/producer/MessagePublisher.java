package producer;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * Publishes a String message to RabbitMQ.
 */
public class MessagePublisher {

    private RabbitTemplate rabbitTemplate;

    private TopicExchange topicExchange;

    MessagePublisher(RabbitTemplate rabbitTemplate, TopicExchange topicExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.topicExchange = topicExchange;
    }

    public void publishMessage(String message, String routingKey) {
        rabbitTemplate.convertAndSend(topicExchange.getName(), routingKey, message);
    }

}