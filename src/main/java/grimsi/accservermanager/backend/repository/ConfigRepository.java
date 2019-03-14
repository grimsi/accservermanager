package grimsi.accservermanager.backend.repository;

import grimsi.accservermanager.backend.entity.Config;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigRepository extends MongoRepository<Config, String> {
    @Override
    <S extends Config> S save(S s);

    Config findByName(String name);
}
