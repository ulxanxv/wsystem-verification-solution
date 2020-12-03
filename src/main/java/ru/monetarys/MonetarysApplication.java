package ru.monetarys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import ru.monetarys.messages.TransferSender;

@EnableJpaAuditing
@SpringBootApplication
public class MonetarysApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(MonetarysApplication.class, args);
		TransferSender transferSender = run.getBean("transferSender", TransferSender.class);
		transferSender.sendMessage();
	}

}
