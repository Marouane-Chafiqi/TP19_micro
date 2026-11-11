package Architecture.micro.Voiture.controllers;

import Architecture.micro.Voiture.entities.Voiture;
import Architecture.micro.Voiture.models.Client;
import Architecture.micro.Voiture.repositories.VoitureRepository;
import Architecture.micro.Voiture.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VoitureController {

    @Autowired
    VoitureRepository voitureRepository;

    @Autowired
    ClientService clientService;

    @GetMapping("/voitures")
    public List<Voiture> findAll() {
        return voitureRepository.findAll();
    }

    @GetMapping("/voitures/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        Voiture voiture = voitureRepository.findById(id).orElse(null);
        if (voiture == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Voiture not found with ID: " + id);
        }
        voiture.setClient(clientService.clientById(voiture.getClientId()));
        return ResponseEntity.ok(voiture);
    }

    @GetMapping("/voitures/client/{id}")
    public ResponseEntity<List<Voiture>> findByClient(@PathVariable Long id) {
        Client client = clientService.clientById(id);
        List<Voiture> voitures = voitureRepository.findByClientId(id);
        voitures.forEach(v -> v.setClient(client));
        return ResponseEntity.ok(voitures);
    }
}
