package ba.unsa.etf.gateway.repository;

import ba.unsa.etf.gateway.grpc.model.SystemEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemEventRepository extends JpaRepository<SystemEvent, Long> {
}