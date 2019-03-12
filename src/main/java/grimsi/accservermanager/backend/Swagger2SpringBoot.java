package grimsi.accservermanager.backend;

import grimsi.accservermanager.backend.configuration.ApplicationConfiguration;
import grimsi.accservermanager.backend.dto.UserDto;
import grimsi.accservermanager.backend.repository.UserRepository;
import grimsi.accservermanager.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableConfigurationProperties(ApplicationConfiguration.class)
public class Swagger2SpringBoot implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationConfiguration config;

    @Autowired
    private UserService userService;

    @Override
    public void run(String... arg0) throws Exception {
        if (arg0.length > 0 && arg0[0].equals("exitcode")) {
            throw new ExitException();
        }

        userRepository.deleteAll();
        userService.registerUser(new UserDto(config.getUsername(), config.getPassword()));
    }

    public static void main(String[] args) throws Exception {
        new SpringApplication(Swagger2SpringBoot.class).run(args);
    }

    class ExitException extends RuntimeException implements ExitCodeGenerator {
        private static final long serialVersionUID = 1L;

        @Override
        public int getExitCode() {
            return 10;
        }

    }
}
