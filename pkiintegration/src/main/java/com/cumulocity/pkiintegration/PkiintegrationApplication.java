package com.cumulocity.pkiintegration;

import org.springframework.boot.SpringApplication;

import com.cumulocity.microservice.autoconfigure.MicroserviceApplication;
import com.cumulocity.microservice.context.annotation.EnableContextSupport;

//@SpringBootApplication
@EnableContextSupport
@MicroserviceApplication
public class PkiintegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(PkiintegrationApplication.class, args);
	}

}
