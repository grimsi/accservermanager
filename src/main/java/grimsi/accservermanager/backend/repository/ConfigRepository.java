package grimsi.accservermanager.backend.repository;

import grimsi.accservermanager.backend.entity.Config;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigRepository extends MongoRepository<Config, String> {
    Config findByName(String name);
}
