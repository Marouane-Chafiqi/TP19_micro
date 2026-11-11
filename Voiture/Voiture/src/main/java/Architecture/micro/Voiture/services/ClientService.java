package Architecture.micro.Voiture.services;

import Architecture.micro.Voiture.models.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "SERVICE-CLIENT")
public interface ClientService {

    @GetMapping("/clients/{id}")
    Client clientById(@PathVariable("id") Long id);
}
