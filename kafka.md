# Connecting your Spring Boot app with Kafka

Link to [starter](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.2.2&packaging=jar&jvmVersion=17&groupId=io.aiven.kafka-cookbook&artifactId=cookbook-kafka&name=cookbook-kafka&description=Cookbook%20for%20connecting%20Spring%20Boot%20to%20Kafka&packageName=io.aiven.kafka-cookbook.cookbook-kafka&dependencies=kafka)

## Dependencies needed 

You will to add these dependencies : 

```xml
   <dependency>
      <groupId>org.springframework.kafka</groupId>
      <artifactId>spring-kafka</artifactId>
    </dependency>
```

## Properties needed

```
spring.kafka.bootstrap-servers=<YOUR_KAFKA_SERVER>:<PORT>
```

## SSL Connection

You need to generate a `truststore` and a `keystore` , you can follow these [instructions](https://aiven.io/docs/products/kafka/howto/keystore-truststore).

If you are using `Aiven for Apache Kafka®` you can also use the Aiven CLI tool `avn` : 

```
avn service user-kafka-java-creds <replace-with-your-kafka-service-name>  --username avnadmin -d src/main/resources/client-certs --password safePassword123
```

Make sure to create a folder named `client-certs` inside `src/main/resources`.

Then add the following properties : 

```
spring.kafka.security.protocol=ssl
spring.kafka.ssl.trust-store-location: classpath:/client-certs/client.truststore.jks
spring.kafka.ssl.trust-store-password=safePassword123
spring.kafka.ssl.key-store-location=classpath:/client-certs/client.keystore.p12
spring.kafka.ssl.key-store-password=safePassword123
```

## Testing your connection 

Update the main class like this : 

```java
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
```

Run your app : `mvn spring-boot:run` , in the logs you should see ` Received payload: 'ConsumerRecord(topic = spring, partition = 0, leaderEpoch = 0, offset = 1, CreateTime = 1708079965395, serialized key size = -1, serialized value size = 20, headers = RecordHeaders(headers = [], isReadOnly = false), key = null, value = Hello, Spring Kafka!)'`.