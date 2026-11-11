# TP Spring Cloud : Eureka, Gateway et OpenFeign

Petit projet de microservices réalisé pour le TP sur Spring Cloud. L'idée : deux services (clients et voitures) qui se découvrent via Eureka, avec une Gateway devant pour avoir un seul point d'entrée.

## Ce qu'il y a dedans

| Module | Port | Rôle |
|---|---|---|
| `eureka-server` | 8761 | Registre : les services s'y enregistrent et s'y retrouvent |
| `service-client` | 8088 | CRUD des clients (base H2 en mémoire) |
| `service-voiture` | 8089 | Gestion des voitures, appelle `SERVICE-CLIENT` avec OpenFeign |
| `gateway` | 8888 | Point d'entrée unique, route vers les services par leur nom (`lb://`) |

## Ce qu'il faut avoir

- JDK 17 ou plus (testé avec le JDK 22)
- IntelliJ IDEA (ou n'importe quel IDE qui gère Maven)
- Une connexion internet au premier lancement, pour que Maven télécharge les dépendances

Versions : Spring Boot 3.3.5 et Spring Cloud 2023.0.3.

Pas besoin de WAMP ni de MySQL : H2 tourne en mémoire dans chaque service.

## Lancer le projet

1. Ouvrir le dossier racine (celui qui contient le `pom.xml`) dans IntelliJ.
2. Attendre que Maven finisse de charger les dépendances.
3. Activer Lombok : *Settings > Build, Execution, Deployment > Compiler > Annotation Processors > Enable annotation processing*.
4. Lancer les applications **dans cet ordre**, et laisser chacune tourner :
   1. `EurekaServerApplication`
   2. `ClientApplication`
   3. `VoitureApplication`
   4. `GateWayApplication`

Attendre le message `Started ...` dans la console avant de passer à la suivante. Si Eureka n'est pas démarré en premier, les autres services affichent des `Connection refused` : ce n'est pas grave en soi, mais ils ne s'enregistrent qu'une fois Eureka disponible.

## Vérifier que tout marche

**Dashboard Eureka** : http://localhost:8761
On doit voir `GATEWAY`, `SERVICE-CLIENT` et `SERVICE-VOITURE` avec le statut UP.

**Directement sur les services**
- http://localhost:8088/clients
- http://localhost:8088/client/1
- http://localhost:8089/voitures
- http://localhost:8089/voitures/1
- http://localhost:8089/voitures/client/1

**Via la Gateway**
- http://localhost:8888/clients
- http://localhost:8888/client/1
- http://localhost:8888/voitures
- http://localhost:8888/voitures/1
- http://localhost:8888/voitures/client/1

À noter : `/voitures` renvoie la liste avec `"client": null`, c'est normal. Le client n'est récupéré (via Feign) que pour `/voitures/{id}` et `/voitures/client/{id}`.

## Routage dynamique (optionnel)

Pour accéder aux services par leur nom, ajouter dans `gateway/src/main/resources/application.properties` :

```properties
spring.cloud.gateway.discovery.locator.enabled=true
```

Redémarrer la Gateway, puis :
- http://localhost:8888/SERVICE-CLIENT/clients
- http://localhost:8888/SERVICE-VOITURE/voitures

## Comment ça circule

```
Navigateur -> Gateway (8888) -> Eureka (résolution du nom) -> LoadBalancer -> service
```

Au démarrage, chaque service s'enregistre auprès d'Eureka avec son `spring.application.name`. La Gateway et le client Feign demandent ensuite à Eureka où se trouve le service, et le LoadBalancer choisit une instance.

##Screenshots

<img width="1020" height="254" alt="Capture d&#39;écran 2026-11-10 225225" src="https://github.com/user-attachments/assets/31760f81-62ae-48fc-b44e-f56be4288ec4" />
<img width="1110" height="499" alt="Capture d&#39;écran 2026-11-10 224306" src="https://github.com/user-attachments/assets/ce848ba2-c5c6-4d64-9238-53924b2c2309" />
<img width="1110" height="499" alt="Capture d&#39;écran 2026-11-10 224306 - Copie" src="https://github.com/user-attachments/assets/033e84af-440f-4f57-8876-089a6b8e27b7" />
<img width="647" height="106" alt="Capture d&#39;écran 2026-11-10 224312" src="https://github.com/user-attachments/assets/de2b6b9e-ec30-47cd-9411-548dbee21537" />
<img width="1152" height="132" alt="Capture d&#39;écran 2026-11-10 224918" src="https://github.com/user-attachments/assets/535c3c53-33ae-4a36-80d2-b805ca532192" />
<img width="681" height="72" alt="Capture d&#39;écran 2026-11-10 224934" src="https://github.com/user-attachments/assets/572ecccc-9467-4ac7-8a47-f21be6a4c847" />
<img width="950" height="222" alt="Capture d&#39;écran 2026-11-10 224955" src="https://github.com/user-attachments/assets/140acbe5-ebeb-4cc7-9f00-c41fab35953e" />
<img width="625" height="96" alt="Capture d&#39;écran 2026-11-10 225111" src="https://github.com/user-attachments/assets/ee63908a-7963-4a68-b791-fa07569f6bdc" />
<img width="236" height="76" alt="Capture d&#39;écran 2026-11-10 225124" src="https://github.com/user-attachments/assets/b61ba832-cb0b-4cea-a6d0-a54e62e5cdde" />
<img width="1154" height="90" alt="Capture d&#39;écran 2026-11-10 225138" src="https://github.com/user-attachments/assets/c34fe61a-2a71-4b07-898e-da9f182bae72" />
<img width="676" height="88" alt="Capture d&#39;écran 2026-11-10 225152" src="https://github.com/user-attachments/assets/927c8c6e-8ac7-4ee1-9b35-f36220c80e5d" />
<img width="1189" height="87" alt="Capture d&#39;écran 2026-11-10 225207" src="https://github.com/user-attachments/assets/7f1c2239-12fb-4a2a-b1f1-c291daae90ea" />
