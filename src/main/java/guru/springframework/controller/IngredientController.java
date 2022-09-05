package guru.springframework.controller;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;


    public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @RequestMapping("recipe/{recipe_id}/ingredient")
    public String showIngredientList(@PathVariable String recipe_id, Model model){

        RecipeCommand recipeCommand=recipeService.findCommandById(Long.valueOf(recipe_id));

model.addAttribute("recipe",recipeCommand);
        return "recipe/ingredient/list";
    }

    @RequestMapping("recipe/{recipe_id}/ingredient/{ingredient_id}/show")
    public String showIngredient(@PathVariable String recipe_id, @PathVariable String ingredient_id,Model model){
    model.addAttribute("ingredient",ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipe_id),Long.valueOf(ingredient_id)));
    return "recipe/ingredient/show";
    }

    @RequestMapping("recipe/{recipe_id}/ingredient/{ingredient_id}/update")
    public String updateAndSaveIngredient(@PathVariable String recipe_id, @PathVariable String ingredient_id,Model model){
        model.addAttribute("ingredient",ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipe_id),Long.valueOf(ingredient_id)));
        return "recipe/ingredient/show";
    }

}

