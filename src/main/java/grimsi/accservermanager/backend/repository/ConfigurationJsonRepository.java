package grimsi.accservermanager.backend.repository;

import grimsi.accservermanager.backend.entity.ConfigurationJson;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationJsonRepository extends MongoRepository<ConfigurationJson, String> {
}
