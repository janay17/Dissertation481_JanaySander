package com.sander.fantastic_food.controller;

import com.sander.fantastic_food.Allergy;
import com.sander.fantastic_food.MealPlan;
import com.sander.fantastic_food.Recipe;
import com.sander.fantastic_food.facade.RecipeFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController implements Controller<Recipe, Integer> {

    private final RecipeFacade recipeFacade;

    public RecipeController(RecipeFacade recipeFacade) {
        this.recipeFacade = recipeFacade;
    }

    @Override
    @GetMapping("/{id:\\d+}")
    public ResponseEntity<Recipe> getByID(@PathVariable("id") Integer id) {
        Recipe recipe = this.recipeFacade.findById(id);
        return ResponseEntity.ok(recipe);
    }

    @Override
    @PostMapping
    public ResponseEntity<Recipe> saveNew(@RequestBody Recipe value) {
        Recipe recipe = this.recipeFacade.saveChanges(value);
        return ResponseEntity.ok(recipe);
    }

    @Override
    @PutMapping
    public ResponseEntity<Recipe> updateExisting(@RequestBody Recipe value) {
        Recipe recipe = this.recipeFacade.saveChanges(value);
        return ResponseEntity.ok(recipe);
    }

    @Override
    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.recipeFacade.deleteById(id));
    }

    @GetMapping("/search/{search}/{page}")
    public ResponseEntity<List<Recipe>> getRecipesBySearch(@PathVariable("search") String text,
                                                           @PathVariable("page") Integer page) {
        List<Recipe> recipes = this.recipeFacade.findAll(text, page);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/allergies/{page}")
    public ResponseEntity<List<Recipe>> getRecipesByAllergy(@PathVariable("page") Integer page
                                                            ,@RequestBody Allergy allergy) {
        List<Recipe> recipes = this.recipeFacade.findByAllergy(allergy, page);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/mealplans/{page}")
    public ResponseEntity<List<Recipe>> getRecipesByMealPlan(@PathVariable("page") Integer page
                                                            ,@RequestBody MealPlan mealPlan) {
        List<Recipe> recipes = this.recipeFacade.findByMealPlan(mealPlan, page);
        return ResponseEntity.ok(recipes);
    }
}
