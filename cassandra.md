# Connecting your Spring Boot app with Cassandra

Link to [starter](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.2.2&packaging=jar&jvmVersion=17&groupId=io.aiven&artifactId=cookbook-cassandra&name=cookbook-cassandra&description=Cookbook%20to%20connect%20Spring%20Boot%20to%20Cassandra&packageName=io.aiven.cookbook-cassandra&dependencies=data-cassandra)

## Dependencies needed 

You will to add these dependencies : 

```xml
   <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-cassandra</artifactId>
    </dependency>
```

## Properties needed

```
spring.cassandra.schema-action=create_if_not_exists
spring.cassandra.local-datacenter=<local-datacenter>
spring.cassandra.keyspace-name=<keyspace>
spring.cassandra.contact-points=<your host>
spring.cassandra.port=<your port>
spring.cassandra.username=<user>
spring.cassandra.password=<yourpassword>
```

### Using SSL

### Create Truststore

You need to create a Java Trustore based on the CA. Download the CA file from the Cassandra provider and then run : 

```
keytool -import -file ca.pem -alias CA -keystore client.truststore.jks

```
You will be prompted to enter a password, choose whatever you want and remember it because you will need it in the `application.properties`. 
Copy `client.truststore.jks` in `src/main/resources`. 

### Update `application.properties`

```
spring.ssl.bundle.jks.mybundle.truststore.location=classpath:client.truststore.jks
spring.ssl.bundle.jks.mybundle.truststore.password=<truststorepassword>
spring.cassandra.ssl.enabled=true
spring.cassandra.ssl.bundle=mybundle
```

## Keyspace

Make sure you have keyspace , i.e with `csql` : 

```
CREATE KEYSPACE aiven_keyspace
        WITH REPLICATION = { 
          'class' : 'SimpleStrategy', 
          'replication_factor' : 2 
      };
```

## Testing your connection

Create a class `Book.java` : 

```java
package io.aiven.cookbookcassandra;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class Book {

    @Id
    private String id;
    private String title;
    private String author;

    public Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    
}
```

An interface `BookRepository.java` : 

```java
package io.aiven.cookbookcassandra;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface BookRepository extends CassandraRepository<Book, String> {
}
```

And Finally in your main class add this method (don't forget the imports) : 

```java
@Bean
	CommandLineRunner commandLineRunner(BookRepository bookRepository) {
		return args -> {
			bookRepository.save(new Book("1", "Book 1", "Author 1"));
			Book book = bookRepository.findById("1").get();
			System.out.println(book.getTitle() + " " + book.getAuthor());
		};
	}
```

Run your app : `mvn spring-boot:run` , in the logs you should see `Book 1 Author 1`
