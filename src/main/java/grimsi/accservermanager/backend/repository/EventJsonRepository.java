package grimsi.accservermanager.backend.repository;

import grimsi.accservermanager.backend.entity.EventJson;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventJsonRepository extends MongoRepository<EventJson, String> {
}
