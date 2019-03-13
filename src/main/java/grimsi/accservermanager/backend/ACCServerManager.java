package grimsi.accservermanager.backend;

import grimsi.accservermanager.backend.configuration.ApplicationConfiguration;
import grimsi.accservermanager.backend.dto.UserDto;
import grimsi.accservermanager.backend.repository.UserRepository;
import grimsi.accservermanager.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfiguration.class)
public class ACCServerManager implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ACCServerManager.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApplicationConfiguration config;
    @Autowired
    private UserService userService;

    public static void main(String[] args) throws Exception {
        new SpringApplication(ACCServerManager.class).run(args);
    }

    @Override
    public void run(String... arg0) throws Exception {
        if (arg0.length > 0 && arg0[0].equals("exitcode")) {
            throw new ExitException();
        }

        userRepository.deleteAll();
        userService.registerUser(new UserDto(config.getUsername(), config.getPassword()));
        log.info("Created user with username '" + config.getUsername() + "' and pre-defined password.");
    }

    class ExitException extends RuntimeException implements ExitCodeGenerator {
        private static final long serialVersionUID = 1L;

        @Override
        public int getExitCode() {
            return 10;
        }

    }
}
