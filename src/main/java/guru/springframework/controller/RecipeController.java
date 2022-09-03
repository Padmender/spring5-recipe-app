package guru.springframework.controller;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipeController {

    private final RecipeService recipeService;


    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/{id}/show")
    public String showRecipe(@PathVariable String id, Model model){
      Recipe recipeObj= recipeService.findById(Long.valueOf(id));
      model.addAttribute("recipe",recipeObj);
      return "recipe/show";
    }

    @RequestMapping("recipe/new")
    public String newView(Model model){
        model.addAttribute("recipe",new RecipeCommand());
        return "recipe/recipeform";
    }

    @RequestMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        RecipeCommand  recipeCommand= recipeService.findCommandById(Long.valueOf(id));
       model.addAttribute("recipe",recipeCommand);
      return "recipe/recipeform";
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command){
        RecipeCommand recipeCommand=recipeService.saveRecipeCommand(command);
        return "redirect:/recipe/"+recipeCommand.getId()+"/show";
    }

    @RequestMapping("recipe/{id}/delete")
    public String deleteRecipe(@PathVariable String id,Model model){
        recipeService.deleteById(Long.valueOf(id));
        model.addAttribute("recipes",recipeService.getRecipes());
        return "index";
    }
}
