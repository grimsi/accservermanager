package grimsi.accservermanager.backend.repository;

import grimsi.accservermanager.backend.entity.Instance;
import grimsi.accservermanager.backend.enums.InstanceState;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstanceRepository extends MongoRepository<Instance, String> {
    Optional<Instance> findByName(String name);

    Optional<List<Instance>> findAllByEvent_Id(String eventId);

    Optional<List<Instance>> findAllByConfiguration_TcpPortOrConfiguration_UdpPort(int tcpPort, int udpPort);

    Optional<List<Instance>> findAllByState(InstanceState state);

    Optional<Instance> findByContainer(String containerId);
}
