package ee.ituk.api.resources;

import ee.ituk.api.resources.domain.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

}
