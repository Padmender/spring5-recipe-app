package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Recipe findById(Long id){

        Optional<Recipe> recipe=recipeRepo.findById(id);
        if(!recipe.isPresent()){
            new Throwable("Recipe not found !");
        }
       return recipe.get();
    }
}
