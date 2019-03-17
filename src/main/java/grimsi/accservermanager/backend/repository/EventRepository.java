package grimsi.accservermanager.backend.repository;

import grimsi.accservermanager.backend.entity.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EventRepository extends MongoRepository<Event, String> {
    /*@Override
    <S extends Event> S save(S s);*/

    Optional<Event> findByName(String name);
}
