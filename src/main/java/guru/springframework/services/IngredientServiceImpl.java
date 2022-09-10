package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepo;
import guru.springframework.repositories.UnitOfMeasureRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepo repo;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    private final UnitOfMeasureRepo unitOfMeasureRepository;

    public IngredientServiceImpl(RecipeRepo repo, IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient, UnitOfMeasureRepo unitOfMeasureRepository) {
        this.repo = repo;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
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

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipe = repo.findById(command.getRecipeId());
        if (!recipe.isPresent()) {
            //todo toss error if not found!
            log.error("Recipe not found for id: " + command.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe recipe1 = recipe.get();
            Optional<Ingredient> ingredientOptional = recipe1.getIngredients().stream().filter(
                    ingredient1 -> command.getId() == ingredient1.getId()
            ).findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository
                        .findById(command.getUom().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
            } else {
                //add new Ingredient
                recipe1.addIngredient(ingredientCommandToIngredient.convert(command));
            }

            Recipe savedRecipe = repo.save(recipe1);

            Optional<Ingredient> ingredientObj=savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst();

            if(!ingredientObj.isPresent()){
                //not totally safe... But best guess
                ingredientObj=savedRecipe.getIngredients().stream()
                        .filter(ingredient -> ingredient.getDescription().equals(command.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(command.getUom().getId()))

                        .findFirst();
            }

            //to do check for fail
            return ingredientToIngredientCommand.convert(
                    ingredientObj.get());
        }


    }

}
