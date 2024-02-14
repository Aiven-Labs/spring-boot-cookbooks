# Connecting your Spring Boot app with Redis

Link to [starter](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.2.2&packaging=jar&jvmVersion=17&groupId=io.aiven&artifactId=cookbook-redis&name=cookbook-redis&description=Cookbook%20to%20connect%20Spring%20Boot%20to%20Redis&packageName=io.aiven.cookbook-redis&dependencies=data-redis)

## Dependencies needed 

You will to add these dependencies : 

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

## Properties needed

```
spring.redis.host=rediss://<USER>:<PASSWORD>@<HOST>:<PORT>
```

## Testing your connection

Create a new class `RedisService.java` : 

```java
package io.aiven.cookbookredis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostConstruct
    public void simpleCommand() {
        redisTemplate.opsForValue().set("mykey", "myValue");
        System.out.println("Value of mykey: " + redisTemplate.opsForValue().get("mykey"));
    }

}
```

Run your app : `mvn spring-boot:run` , in the logs you should see `Value of mykey: myValue`




