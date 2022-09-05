package guru.springframework.repositories;

import guru.springframework.domain.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//@Repository
public interface CategoryRepo extends CrudRepository<Category,Long> {
    public Optional<Category> findByDescription(String catName);

}
