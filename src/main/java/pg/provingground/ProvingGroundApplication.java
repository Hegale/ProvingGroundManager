package pg.provingground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pg.provingground.repository.TestDataBuilder;

@SpringBootApplication
public class ProvingGroundApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProvingGroundApplication.class, args);
    }

}
