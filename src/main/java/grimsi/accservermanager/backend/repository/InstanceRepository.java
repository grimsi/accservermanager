package grimsi.accservermanager.backend.repository;

import grimsi.accservermanager.backend.documents.Instance;
import grimsi.accservermanager.backend.enums.InstanceState;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstanceRepository extends MongoRepository<Instance, String> {
    Instance findByName(String name);

    List<Instance> findByState(InstanceState state);

    Instance findByContainer(String containerName);
}
