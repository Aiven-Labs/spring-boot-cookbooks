package io.aiven.kafkacookbook.cookbookkafka;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class CookbookKafkaApplication {

	private static final Logger logger = LoggerFactory.getLogger(CookbookKafkaApplication.class);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

    public static final String TOPIC = "spring";
	public static void main(String[] args) {
		SpringApplication.run(CookbookKafkaApplication.class, args);
	}

	@KafkaListener(topics = TOPIC, groupId = "aiven")
    public void receive(ConsumerRecord<String, String> consumerRecord) {
        logger.info("Received payload: '{}'", consumerRecord.toString());
    }

	@Bean
	public CommandLineRunner runner() {
		return args -> {
			kafkaTemplate.send(TOPIC, "Hello, Spring Kafka!");
		};
	} 
}
