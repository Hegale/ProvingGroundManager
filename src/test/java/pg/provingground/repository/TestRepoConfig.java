package pg.provingground.repository;

import jakarta.persistence.EntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestRepoConfig {
    @Bean
    public CarRentalRepositoryImpl carRentalRepository(EntityManager em) {
        return new CarRentalRepositoryImpl(em);
    }
}