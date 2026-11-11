package Architecture.micro.Voiture;

import Architecture.micro.Voiture.entities.Voiture;
import Architecture.micro.Voiture.repositories.VoitureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class VoitureApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoitureApplication.class, args);
    }

    @Bean
    CommandLineRunner initialiserBaseH2(VoitureRepository voitureRepository) {
        return args -> {
            voitureRepository.save(new Voiture(null, "Toyota", "A 25 333", "Corolla", 1L, null));
            voitureRepository.save(new Voiture(null, "Renault", "B 6 3456", "Megane", 1L, null));
            voitureRepository.save(new Voiture(null, "Peugeot", "A 55 4444", "301", 2L, null));
        };
    }
}
