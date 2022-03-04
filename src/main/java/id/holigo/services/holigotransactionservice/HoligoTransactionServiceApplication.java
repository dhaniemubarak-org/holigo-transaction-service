package id.holigo.services.holigotransactionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class HoligoTransactionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HoligoTransactionServiceApplication.class, args);
	}

}
