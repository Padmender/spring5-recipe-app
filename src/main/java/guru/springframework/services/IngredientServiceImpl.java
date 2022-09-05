package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepo repo;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(RecipeRepo repo, IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.repo = repo;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipe_Id, Long ingredient_Id) {
    Optional<Recipe> recipeOptional= repo.findById(recipe_Id);
    if(!recipeOptional.isPresent()){
       log.error("No Recipe exist for the given recipeId."+recipe_Id);
    }
     Optional<IngredientCommand> ingredientCommand=recipeOptional.get().getIngredients().stream()
                .filter(ingredient -> ingredient_Id == ingredient.getId())
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

    if(!ingredientCommand.isPresent()){
        log.error("No Ingredient exist for the given ingredient_id."+ingredient_Id);
    }
        return ingredientCommand.get();
    }
}
