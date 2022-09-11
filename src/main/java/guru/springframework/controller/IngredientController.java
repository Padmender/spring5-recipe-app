package guru.springframework.controller;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    private final UnitOfMeasureService unitOfMeasureService;



    public IngredientController(RecipeService recipeService,
                                IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping
    @RequestMapping("recipe/{recipe_id}/ingredient")
    public String showIngredientList(@PathVariable String recipe_id, Model model){
        RecipeCommand recipeCommand=recipeService.findCommandById(Long.valueOf(recipe_id));
        model.addAttribute("recipe",recipeCommand);
        return "recipe/ingredient/list";
    }

    @RequestMapping("recipe/{recipe_id}/ingredient/{ingredient_id}/show")
    public String showIngredient(@PathVariable String recipe_id,
                                 @PathVariable String ingredient_id,Model model){
    model.addAttribute("ingredient",ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipe_id),Long.valueOf(ingredient_id)));
    return "recipe/ingredient/show";
    }

    @RequestMapping("recipe/{recipe_id}/ingredient/{ingredient_id}/update")
    public String updateRecipeIngredient(@PathVariable String recipe_id,
                                         @PathVariable String ingredient_id,Model model){
        IngredientCommand ingredientCommand=ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipe_id),Long.valueOf(ingredient_id));
        model.addAttribute("ingredient",ingredientCommand);
        model.addAttribute("uomList",ingredientCommand.getUom());
        return "recipe/ingredient/ingredientform";
    }


    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command) {

        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
        log.debug("saved recipe id:" + savedCommand.getRecipeId());
        log.debug("saved ingredient id:" + savedCommand.getId());
        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";

    }


    @GetMapping
    @RequestMapping("recipe/{recipeId}/addIngredient")
    public String addIngredientForm(@PathVariable String recipeId,Model model){
        System.out.println("recipe_id in addIngredientForm: ="+recipeId);
        log.info("recipe_id in addIngredientForm: ="+recipeId);

        //make sure we have a good id value
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
        //todo raise exception if null

        IngredientCommand ingredientCommand= new IngredientCommand();
        ingredientCommand.setRecipeId(Long.valueOf(recipeId));
        ingredientCommand.setUom(new UnitOfMeasureCommand());
        model.addAttribute("ingredient",ingredientCommand);
        model.addAttribute("uomList",unitOfMeasureService.findAll());

        return "recipe/ingredient/ingredientform";
    }


    @RequestMapping("recipe/{recipe_id}/ingredient/{ingredient_id}/delete")
    public String deleteRecipeIngredient(@PathVariable String recipe_id,
                                         @PathVariable String ingredient_id,Model model){

        ingredientService.deleteRecipeIdAndIngredientId(Long.valueOf(recipe_id),Long.valueOf(ingredient_id));


        return "redirect:/recipe/"+recipe_id+"/ingredient";

    }

}

