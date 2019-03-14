package grimsi.accservermanager.backend.repository;

import grimsi.accservermanager.backend.entity.SettingsJson;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingsJsonRepository extends MongoRepository<SettingsJson, String> {
}
