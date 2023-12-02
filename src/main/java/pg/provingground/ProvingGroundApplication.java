package pg.provingground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import pg.provingground.repository.TestDataBuilder;

// 괄호 안의 옵션으로 시큐리티 임시 비활성화. 이후 다시 활성화하세요
@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class ProvingGroundApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProvingGroundApplication.class, args);
    }

}
