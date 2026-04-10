# Testing Spring Petclinic AngularJS App

## Starting the App

```bash
# Build all modules first (downloads Node/npm automatically for client)
./mvnw clean install -DskipTests

# Start server with default HSQLDB profile
./mvnw spring-boot:run -pl spring-petclinic-server
```

App runs on `http://localhost:8080` by default.

Default profiles: `hsqldb,prod` (in-memory HSQLDB with seed data, production frontend caching).

## Key REST Endpoints

| Endpoint | Method | Description |
|---|---|---|
| `/vets` | GET | List all veterinarians (JSON array) |
| `/owners/list` | GET | List all owners with pets |
| `/owners/{id}` | GET | Single owner detail |
| `/owners` | POST | Create new owner |
| `/owners/{id}` | PUT | Update owner |
| `/petTypes` | GET | List pet types |
| `/owners/{ownerId}/pets` | POST | Add pet to owner |
| `/owners/{ownerId}/pets/{petId}/visits` | GET/POST | List/add visits |
| `/manage` | GET | Actuator endpoints base path |

## Frontend Navigation

AngularJS SPA with hash-based routing (`#!/`):
- `#!/welcome` — Home page (default)
- `#!/vets` — Veterinarians list
- `#!/owners` — Owners list (with search filter)
- `#!/owners/details/{id}` — Owner detail with pets and visits
- `#!/owners/new` — Register new owner

Nav bar has: Home, Owners (dropdown: All, Register), Veterinarians.

## Seed Data Verification

When HSQLDB profile is active, the app loads seed data from:
- `db/hsqldb/schema.sql` — Table definitions
- `db/hsqldb/data.sql` — Sample data

Expected seed data:
- **6 vets**: James Carter, Helen Leary (radiology), Linda Douglas (dentistry, surgery), Rafael Ortega (surgery), Henry Stevens (radiology), Sharon Jenkins
- **10 owners**: George Franklin, Betty Davis, Eduardo Rodriguez, Harold Davis, Peter McTavish, Jean Coleman, Jeff Black, Maria Escobito, David Schroeder, Carlos Estaban
- George Franklin should have pet "Leo" (cat, born 2010-09-07)

## MySQL Profile Testing

```bash
# Start MySQL 8.0 via docker-compose
docker-compose up -d

# Run with MySQL profile
./mvnw spring-boot:run -pl spring-petclinic-server -Dspring-boot.run.profiles=mysql
```

MySQL schema uses `CREATE USER IF NOT EXISTS` + `GRANT` (MySQL 8.0 compatible).

## Known Quirks

- **Gulp 3 + Node 18**: The client module uses Gulp 3.9.1 which is incompatible with Node 12+. Compatibility is maintained via npm `overrides` for `graceful-fs` in `spring-petclinic-client/package.json`. If the client build fails with `ReferenceError: primordials is not defined`, check that the overrides are present.
- **Frontend-maven-plugin requires Maven 3.6+**: The Maven wrapper must be version 3.6+ (currently 3.9.9) for `frontend-maven-plugin:1.15.0`.
- **javax.cache stays as javax**: `ClinicServiceImpl.java` uses `javax.cache.annotation.CacheResult` (JSR-107). This is NOT part of Jakarta namespace migration.
- **`@MockBean` deprecated in 3.3.x**: Test files use `org.springframework.boot.test.mock.mockito.MockBean` which works in 3.3.5 but is removed in 3.4+.
- **Docker build**: Uses Fabric8 docker-maven-plugin. May need `<assembly><descriptorRef>artifact</descriptorRef></assembly>` config and Dockerfile `ADD maven/petclinic.jar` path update for `docker:build` to work.
- **Chrome on Devin VM**: Use `/opt/.devin/chrome/chrome/linux-133.0.6943.126/chrome-linux64/chrome --no-sandbox --no-first-run --disable-gpu --user-data-dir=/tmp/chrome-test` to launch Chrome directly.

## Devin Secrets Needed

No secrets needed for local HSQLDB testing. MySQL testing requires only the docker-compose setup (no external credentials).
