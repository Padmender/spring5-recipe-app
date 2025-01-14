package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;

public interface IngredientService {
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipe_Id, Long ingredient_Id);

    IngredientCommand saveIngredientCommand(IngredientCommand command);

    void deleteRecipeIdAndIngredientId(Long recipe, Long ingredient_id);
}
