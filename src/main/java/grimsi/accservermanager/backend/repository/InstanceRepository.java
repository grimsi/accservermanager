package grimsi.accservermanager.backend.repository;

import grimsi.accservermanager.backend.entity.Instance;
import grimsi.accservermanager.backend.enums.InstanceState;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstanceRepository extends MongoRepository<Instance, String> {
    Optional<List<Instance>> findAllByName(String name);

    Optional<List<Instance>> findAllByConfig_Id(String configId);

    Optional<List<Instance>> findAllByState(InstanceState state);

    Optional<Instance> findByContainer(String containerName);
}
