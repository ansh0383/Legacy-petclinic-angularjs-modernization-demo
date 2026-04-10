# Testing Spring PetClinic Server

## Starting the App

```bash
# Start with HSQLDB (default, in-memory database with seed data)
./mvnw spring-boot:run -pl spring-petclinic-server
# App runs on http://localhost:8080
```

## Architecture

Modular monolith with 4 bounded-context modules:
- **customer/** — Owner entity, OwnerService, OwnerController
- **pet/** — Pet, PetType entities, PetService, PetController
- **vet/** — Vet, Specialty entities, VetService, VetController
- **visit/** — Visit entity, VisitService, VisitController
- **shared/** — BaseEntity, NamedEntity, Person, AbstractResourceController
- **infrastructure/** — CacheConfig, WebConfig, PetclinicProperties, CallMonitoringAspect

Cross-module dependency direction: `visit -> pet -> customer`, `vet -> nothing`.

## REST API Endpoints

| Method | URL | Controller | Notes |
|--------|-----|------------|-------|
| GET | /vets | VetController | Returns JSON array of vets with specialties |
| GET | /owners/list | OwnerController | Returns JSON array of all owners |
| GET | /owners/{id} | OwnerController | Returns owner with nested pets |
| POST | /owners | OwnerController | Creates new owner (requires firstName, lastName, address, city, telephone) |
| PUT | /owners/{id} | OwnerController | Updates owner |
| GET | /petTypes | PetController | Returns all pet types |
| GET | /owners/*/pets/{petId} | PetController | Returns PetDetails DTO (accesses owner via lazy loading) |
| POST | /owners/{ownerId}/pets | PetController | Creates pet for owner |
| PUT | /owners/{ownerId}/pets/{petId} | PetController | Updates pet |
| GET | /owners/{ownerId}/pets/{petId}/visits | VisitController | Returns visits for pet |
| POST | /owners/{ownerId}/pets/{petId}/visits | VisitController | Creates visit for pet |

## Frontend Navigation (AngularJS with hashbang routing)

- Welcome: `http://localhost:8080/#!/welcome`
- Owner list: `http://localhost:8080/#!/owners`
- Owner details: `http://localhost:8080/#!/owners/details/{ownerId}`
- New owner: `http://localhost:8080/#!/owners/new`
- Edit owner: `http://localhost:8080/#!/owners/{ownerId}/edit`
- Visits: `http://localhost:8080/#!/owners/{ownerId}/pets/{petId}/visits`
- Vet list: `http://localhost:8080/#!/vets`

## Seed Data (HSQLDB profile)

- **10 owners**: George Franklin (id=1), Betty Davis (id=2), ... Carlos Estaban (id=10)
- **13 pets**: Leo (id=1, cat, owner 1), Basil (id=2, hamster, owner 2), ...
- **6 vets**: James Carter (none), Helen Leary (radiology), Linda Douglas (dentistry, surgery), Rafael Ortega (surgery), Henry Stevens (radiology), Sharon Jenkins (none)
- **4 visits**: Pet 7 (Samantha) and Pet 8 (Max) each have 2 visits
- **6 pet types**: cat, dog, lizard, snake, bird, hamster

## Key Testing Scenarios

1. **Vet module (independent)**: GET /vets should return 6 vets — tests VetController->VetService->VetRepository
2. **Owner list + details**: GET /owners/list and GET /owners/1 — tests OwnerController and cross-module lazy loading (pets)
3. **Create owner**: POST /owners — tests write path through OwnerService
4. **Visit flow (full cross-module chain)**: Navigate to owner details -> click Add Visit -> submit — tests visit->pet->customer chain
5. **REST API JSON**: Direct GET requests to verify JSON structure

## Running Tests

```bash
# All tests (51 tests across model, service, and controller layers)
./mvnw test -pl spring-petclinic-server
```

## No Authentication Required

The app has no Spring Security configured. All endpoints are publicly accessible.
