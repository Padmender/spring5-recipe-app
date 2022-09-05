package guru.springframework.repositories;

import guru.springframework.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

//@Repository
public interface RecipeRepo extends CrudRepository<Recipe,Long> {


}
