# Connecting your Spring Boot app with Clickhouse

Link to [starter](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.2.2&packaging=jar&jvmVersion=17&groupId=io.aiven&artifactId=cookbook-clickhouse&name=cookbook-clickhouse&description=Cookbook%20to%20connect%20Spring%20Boot%20to%20Clickhouse&packageName=io.aiven.cookbook-clickhouse)

## Dependencies needed 

You will to add these dependencies : 

```xml
<dependency>
	<groupId>com.clickhouse</groupId>
	<artifactId>clickhouse-jdbc</artifactId>
	<version>0.6.0</version>
	<classifier>all</classifier>
</dependency>
<dependency>
	<groupId>org.slf4j</groupId>
	<artifactId>slf4j-api</artifactId>
	<version>2.0.9</version>
</dependency>
```
## Properties needed

Those are custom properties that will be declared later in the code. 

```
clickhouse.url=jdbc:ch://<HOST>:<PORT>>?ssl=true&sslmode=STRICT
clickhouse.username=<USER>
clickhouse.password=<PASSWORD>
```

## Testing the connection

Update the main class : 

```java
package io.aiven.cookbookclickhouse;

import java.sql.Statement;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.clickhouse.jdbc.ClickHouseConnection;
import com.clickhouse.jdbc.ClickHouseDataSource;

@SpringBootApplication
public class CookbookClickhouseApplication {

	@Value("${clickhouse.url}")
	private String clickhouseUrl;

	@Value("${clickhouse.username}")
	private String clickhouseUsername;

	@Value("${clickhouse.password}")
	private String clickhousePassword;

	public static void main(String[] args) {
		SpringApplication.run(CookbookClickhouseApplication.class, args);
	}

	@Bean
	public CommandLineRunner run() {
		return args -> {
			String connString = clickhouseUrl;
			ClickHouseDataSource database = new ClickHouseDataSource(connString);
			ClickHouseConnection connection = database.getConnection(clickhouseUsername, clickhousePassword);
			Statement statement = connection.createStatement();
			ResultSet result_set = statement.executeQuery("SELECT 1 AS one");
			while (result_set.next()) {
				System.out.println("RESULT : " + result_set.getInt("one"));
			}
		};
	}

}
```

Run your app : `mvn spring-boot:run` , in the logs you should see `RESULT : 1`
