package Architecture.micro.Client;

import Architecture.micro.Client.entities.Client;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import Architecture.micro.Client.repository.ClientRepository;

@EnableDiscoveryClient
@SpringBootApplication
public class ClientApplication {
	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);

	}
	@Bean
    CommandLineRunner initialiserBaseH2(ClientRepository clientRepository) {
		return args -> {
			clientRepository.save(new Client(null, "Rabab SELIMANI", 23f));
			clientRepository.save(new Client(null, "Amal RAMI", 22f));
			clientRepository.save(new Client(null, "Samir SAFI", 22f));
		};
	}
}