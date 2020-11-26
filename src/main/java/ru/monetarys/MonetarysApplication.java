package ru.monetarys;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.monetarys.exceptions.AccountNotFound;
import ru.monetarys.services.clientprofile.ClientProfileServiceImpl;

@SpringBootApplication
@Slf4j
public class MonetarysApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonetarysApplication.class, args);
	}

}
