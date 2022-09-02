package guru.springframework.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    @Lob
    private String directions;

    @Lob
    private byte[] image;

    @OneToOne(cascade =CascadeType.ALL) // cascade type all: recipe is the owner if recipe delete then notes should be auto delete
    private Notes notes;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "recipe")
    private Set<Ingredient> ingredients =new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @ManyToMany()
    @JoinTable(
            name = "Recipe_Category",
            joinColumns  ={@JoinColumn(name = "recipe_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    private Set<Category> categories =new HashSet<>();

    public void setNotes(Notes notes) {
        this.notes = notes;
        notes.setRecipe(this);
    }

    public Recipe addIngredients(Ingredient ingredient){
       ingredient.setRecipe(this);
       this.getIngredients().add(ingredient);
        return this;
    }


}

