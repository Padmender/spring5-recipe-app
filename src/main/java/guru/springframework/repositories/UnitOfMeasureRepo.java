package guru.springframework.repositories;

import guru.springframework.domain.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitOfMeasureRepo extends CrudRepository<UnitOfMeasure,Long> {

    public Optional<UnitOfMeasure> findByDescription(String descriptionName);
}
