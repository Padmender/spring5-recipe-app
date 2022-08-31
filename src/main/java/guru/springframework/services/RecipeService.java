package guru.springframework.services;

import guru.springframework.domain.Recipe;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RecipeService {
    List<Recipe> getRecipes();
    Recipe findById(Long id);

}
