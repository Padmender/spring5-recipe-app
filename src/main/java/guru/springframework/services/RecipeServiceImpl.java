package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepo recipeRepo;

    public RecipeServiceImpl(RecipeRepo recipeRepo) {
        this.recipeRepo = recipeRepo;
    }

    @Override
    public List<Recipe> getRecipes() {
        List<Recipe> actualList = new ArrayList<Recipe>();
        recipeRepo.findAll().iterator().forEachRemaining(actualList::add);
        return actualList;
    }
}
