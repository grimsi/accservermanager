package grimsi.accservermanager.backend;

import grimsi.accservermanager.backend.configuration.ApplicationConfiguration;
import grimsi.accservermanager.backend.dto.UserDto;
import grimsi.accservermanager.backend.repository.ConfigRepository;
import grimsi.accservermanager.backend.repository.InstanceRepository;
import grimsi.accservermanager.backend.repository.UserRepository;
import grimsi.accservermanager.backend.service.FileSystemService;
import grimsi.accservermanager.backend.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfiguration.class)
public class ACCServerManager implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ACCServerManager.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileSystemService fileSystemService;
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

        fileSystemService.initFileSystem();
        log.info("Initialized filesystem.");

        userRepository.deleteAll();
        userService.registerUser(new UserDto(config.getUsername(), config.getPassword()));
        log.info("Created user with username '" + config.getUsername() + "' and pre-defined password.");
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    class ExitException extends RuntimeException implements ExitCodeGenerator {
        private static final long serialVersionUID = 1L;

        @Override
        public int getExitCode() {
            return 10;
        }

    }

}
