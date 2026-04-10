package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles({"hsqldb", "prod"})
@Transactional
public class ClinicServiceIntegrationTests extends AbstractClinicServiceTests {

    @Test
    @Override
    public void shouldFindSingleOwnerWithPet() {
        super.shouldFindSingleOwnerWithPet();
    }
}
