package com.jp.dbbasedsession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import io.github.cdimascio.dotenv.Dotenv;


@SpringBootApplication
public class DbbasedsessionApplication {

    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.load(); // loads .env from project root

        System.setProperty("SPRING_DATASOURCE_URL", dotenv.get("SPRING_DATASOURCE_URL"));
        System.setProperty("SPRING_DATASOURCE_USERNAME", dotenv.get("SPRING_DATASOURCE_USERNAME"));
        System.setProperty("SPRING_DATASOURCE_PASSWORD", dotenv.get("SPRING_DATASOURCE_PASSWORD"));
        System.setProperty("SPRING_SECURITY_USER_NAME", dotenv.get("SPRING_SECURITY_USER_NAME"));
        System.setProperty("SPRING_SECURITY_USER_PASSWORD", dotenv.get("SPRING_SECURITY_USER_PASSWORD"));

        SpringApplication.run(DbbasedsessionApplication.class, args);
        System.out.println("Hello World!!! !!!! ! ! !  ! ! !");
    }

}
