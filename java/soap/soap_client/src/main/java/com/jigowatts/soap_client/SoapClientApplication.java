package com.jigowatts.soap_client;

import com.jigowatts.soap_client.service.CountryClient;
import com.jigowatts.soap_client.service.UserClient;
import com.jigowatts.soap_client.wsdl.GetCountryResponse;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class SoapClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoapClientApplication.class, args);
	}

	@Bean
	CommandLineRunner lookup(CountryClient quoteClient) {
		return args -> {
			String country = "United Kingdom";

			if (args.length > 0) {
				country = args[0];
			}
			GetCountryResponse response = quoteClient.getCountry(country);
			var aCountry = response.getCountry();
			var capital = aCountry.getCapital();
			var currency = aCountry.getCurrency();
			var population = aCountry.getPopulation();
			var ccTLD = aCountry.getCctld();

			log.info("Country:{} Capital:{} Currency:{} Population:{} ccTLD:{}", country, capital, currency, population,
					ccTLD);
		};
	}

	@Bean
	String getUserName(UserClient client) {
		var userId = 1;
		var response = client.getUser(userId);
		var aUser = response.getUser();
		var userName = aUser.getName();
		log.info("UserID:{} UserName:{}", Integer.toString(userId), userName);
		return userName;
	}
}
