package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepo;
import guru.springframework.repositories.RecipeRepo;
import guru.springframework.repositories.UnitOfMeasureRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Component
public class DataLoader implements CommandLineRunner {

    private final UnitOfMeasureRepo unitOfMeasureRepo;
    private final CategoryRepo categoryRepo;

    private final RecipeRepo recipeRepo;




    public DataLoader(UnitOfMeasureRepo unitOfMeasureRepo, CategoryRepo categoryRepo, RecipeRepo recipeRepo) {
        this.unitOfMeasureRepo = unitOfMeasureRepo;
        this.categoryRepo = categoryRepo;
        this.recipeRepo = recipeRepo;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        log.info("Loading bootstrap....");
        extracted();
        log.info("bootstrap completed.");
    }

    private void extracted() {
        Optional<UnitOfMeasure> uomEach= unitOfMeasureRepo.findByDescription("Each");
        if(!uomEach.isPresent()){
            new RuntimeException("Each description not found");
        }
        Optional<UnitOfMeasure> uomTeaspoon= unitOfMeasureRepo.findByDescription("Teaspoon");
        if(!uomTeaspoon.isPresent()){
            new RuntimeException("Teaspoon description not found");
        }
        Optional<UnitOfMeasure> uomTablespoon= unitOfMeasureRepo.findByDescription("Tablespoon");
        if(!uomTablespoon.isPresent()){
            new RuntimeException("Tablespoon description not found");
        }
        Optional<UnitOfMeasure> uomPinch= unitOfMeasureRepo.findByDescription("Pinch");
        if(!uomPinch.isPresent()){
            new RuntimeException("Pinch description not found");
        }
        Optional<UnitOfMeasure> uomTablespoons= unitOfMeasureRepo.findByDescription("Tablespoons");
        if(!uomTablespoons.isPresent()){
            new RuntimeException("Tablespoons description not found");
        }

        Optional<Category> categoryAmerican= categoryRepo.findByCategoryName("American");
        if(!categoryAmerican.isPresent()){
            new RuntimeException("American category not found");
        }
        Optional<Category> categoryMexican= categoryRepo.findByCategoryName("Mexican");
        if(!categoryMexican.isPresent()){
            new RuntimeException("Mexican category not found");
        }
        Optional<Category> categoryItalian= categoryRepo.findByCategoryName("Italian");
        if(!categoryItalian.isPresent()){
            new RuntimeException("Italian category not found");
        }


        Recipe recipe=new Recipe();
        recipe.setDescription("Best Guacamole");
        recipe.setPrepTime(10);
        recipe.setCookTime(8);
        recipe.setServings(2);
        recipe.setSource("simply recipes");
        recipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        recipe.setDirections("Cut the avocado:\n" +
                "Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n" +
                "\n" +
                "How to make guacamole - scoring avocado\n" +
                "Mash the avocado flesh:\n" +
                "Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n" +
                "\n" +
                "How to make guacamole - smashing avocado with fork\n" +
                "Add the remaining ingredients to taste:\n" +
                "Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "\n" +
                "Add the chopped onion, cilantro, black pepper, and chilis. Chili peppers vary individually in their spiciness. So, start with a half of one chili pepper and add more to the guacamole to your desired degree of heat.\n" +
                "\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "\n" +
                "Serve immediately:\n" +
                "If making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.)\n" +
                "\n" +
                "Garnish with slices of red radish or jigama strips. Serve with your choice of store-bought tortilla chips or make your own homemade tortilla chips.\n" +
                "\n" +
                "Refrigerate leftover guacamole up to 3 days.\n" +
                "\n" +
                "Note: Chilling tomatoes hurts their flavor. So, if you want to add chopped tomato to your guacamole, add it just before serving.");
        Notes notes =new Notes();
        notes.setNotes("All you really need to make guacamole is ripe avocados and salt. After that, a little lime or lemon juice—a splash of acidity—will help balance the richness of the avocado. If you want, add chopped cilantro, chilis, onion, and/or tomato.");
        recipe.setNotes(notes);

        recipe.addIngredients(new Ingredient("ripe avocados",new BigDecimal(2),uomEach.get()));
        recipe.addIngredients(new Ingredient("kosher salt, plus more to taste",new BigDecimal(1.4),uomTeaspoon.get()));
        recipe.addIngredients(new Ingredient("fresh lime or lemon juice",new BigDecimal(1),uomTablespoons.get()));
        recipe.addIngredients(new Ingredient("minced red onion or thinly sliced green onion",new BigDecimal(2),uomTablespoons.get()));
        recipe.addIngredients(new Ingredient("cilantro (leaves and tender stems), finely chopped",new BigDecimal(2),uomTablespoons.get()));
        recipe.addIngredients(new Ingredient("serrano (or jalapeño) chilis, stems and seeds removed, minced",new BigDecimal(1),uomEach.get()));
        recipe.addIngredients(new Ingredient("ripe tomato, chopped (optional)",new BigDecimal(2),uomEach.get()));
        recipe.setDifficulty(Difficulty.EASY);
        recipe.getCategories().add(categoryMexican.get());
        recipe.getCategories().add(categoryItalian.get());

        recipeRepo.save(recipe);
    }
}
