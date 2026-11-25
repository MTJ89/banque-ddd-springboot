# Banque DDD — Spring Boot / Java 21

Projet pédagogique qui illustre une séparation DDD + architecture hexagonale :
- **Domaine** : `Account` (Aggregate Root), `Money` (Value Object), exceptions et port repository.
- **Application** : trois cas d'utilisation indépendants (versement, retrait, consultation du solde).
- **Infrastructure** : API REST, repository mémoire et configuration Spring.

## Prérequis
- JDK 21
- Maven 3.9+

## Lancer
```bash
mvn clean test
mvn spring-boot:run
```

Un compte de démonstration est créé au démarrage :
`11111111-1111-1111-1111-111111111111` avec un solde initial de **1 000,00 EUR**.

## API

### Consulter le solde
```bash
curl http://localhost:8080/api/comptes/11111111-1111-1111-1111-111111111111/solde
```

### Effectuer un versement
```bash
curl -X POST http://localhost:8080/api/comptes/11111111-1111-1111-1111-111111111111/versements \
  -H "Content-Type: application/json" -d '{"montant": 150.00}'
```

### Effectuer un retrait
```bash
curl -X POST http://localhost:8080/api/comptes/11111111-1111-1111-1111-111111111111/retraits \
  -H "Content-Type: application/json" -d '{"montant": 40.00}'
```

Les opérations de versement et retrait renvoient le nouveau solde :
```json
{ "solde": 1110.00, "devise": "EUR" }
```

## Règles métier
- Le montant d'une opération doit être strictement positif.
- Un retrait ne peut pas dépasser le solde disponible.
- Les montants sont manipulés avec `BigDecimal`, jamais avec `double`.
- Les règles métier sont dans l'agrégat `Account`, pas dans le contrôleur.
- Le repository est un port du domaine ; son implémentation mémoire est un adaptateur d'infrastructure.

## Arborescence
```text
fr.demo.banque
├── domain
│   ├── model          # Account, Money
│   ├── exception
│   └── port           # AccountRepository
├── application
│   └── usecase        # Deposit, Withdraw, GetBalance
└── infrastructure
    ├── config
    ├── persistence   # adaptateur mémoire
    └── web           # contrôleur REST + gestion erreurs
```

> Pour une application réelle, remplacer le repository mémoire par un adaptateur JPA, ajouter une base de données, une gestion transactionnelle persistante, l'authentification, l'idempotence des opérations et un journal/audit des mouvements.
