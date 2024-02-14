# Connecting your Spring Boot app with Postgresql


Link to [starter](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.2.2&packaging=jar&jvmVersion=17&groupId=io.aiven&artifactId=cookbook-postgresql&name=cookbook-postgresql&description=Cookbook%20to%20connect%20Spring%20Boot%20to%20Postgresql&packageName=io.aiven.cookbook-postgresql&dependencies=postgresql,data-jdbc)


## Dependencies needed 

You will to add these dependencies : 

```xml
<dependency>
	<groupId>org.postgresql</groupId>
	<artifactId>postgresql</artifactId>
	<scope>runtime</scope>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jdbc</artifactId>
 </dependency>
```

## Properties needed

```
spring.datasource.url=jdbc:postgresql://<HOST>:<PORT>/<DB_NAME>?ssl=require
spring.datasource.username=<USER>
spring.datasource.password=<PASSWORD>
spring.datasource.driver-class-name=org.postgresql.Driver
```

## Testing your connection

Create a new class `MyConnection.java` : 

```java
package io.aiven.cookbookpostgresql;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class MyConnection {
    
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void simpleQuery() {
        try (Connection connection = dataSource.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT CURRENT_TIMESTAMP;");
            while (rs.next()) {
                System.out.println("Connection working, it is now : " + rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }  
    }
}
```

Run your app : `mvn spring-boot:run` , in the logs you should see `Connection working, it is now <current timestamp>`.