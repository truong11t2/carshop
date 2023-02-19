package learn.carbackend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "owners")
public interface OwnerRepository extends JpaRepository<OwnerEntity, Long> {

}
