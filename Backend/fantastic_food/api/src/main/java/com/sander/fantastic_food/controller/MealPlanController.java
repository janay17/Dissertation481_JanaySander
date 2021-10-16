package com.sander.fantastic_food.controller;

import com.sander.fantastic_food.MealPlan;
import com.sander.fantastic_food.facade.MealPlanFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mealplans")
public class MealPlanController implements Controller<MealPlan, Integer>{

    private final MealPlanFacade mealPlanFacade;
    private static Logger LOGGER = LoggerFactory.getLogger(MealPlanController.class);

    public MealPlanController(MealPlanFacade mealPlanFacade) {
        this.mealPlanFacade = mealPlanFacade;
    }

    @Override
    @GetMapping("/{id:\\d+}")
    public ResponseEntity<MealPlan> getByID(@PathVariable("id") Integer id) {
        MealPlan mealPlan = this.mealPlanFacade.findById(id);
        return ResponseEntity.ok(mealPlan);
    }

    @Override
    @PostMapping
    public ResponseEntity<MealPlan> saveNew(@RequestBody MealPlan value) {
        MealPlan mealPlan = this.mealPlanFacade.saveChanges(value);
        return ResponseEntity.ok(mealPlan);
    }

    @Override
    @PutMapping
    public ResponseEntity<MealPlan> updateExisting(@RequestBody MealPlan value) {
        MealPlan mealPlan = this.mealPlanFacade.saveChanges(value);
        return ResponseEntity.ok(mealPlan);
    }

    @Override
    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.mealPlanFacade.deleteById(id));
    }

    @GetMapping
    public ResponseEntity<List<MealPlan>> getAll() {
        List<MealPlan> mealPlans = this.mealPlanFacade.findAll();
        return ResponseEntity.ok(mealPlans);
    }

    @GetMapping("/search/{search}")
    public ResponseEntity<List<MealPlan>> getByMealPlanName(@PathVariable("search") String text) {
        List<MealPlan> mealPlans = this.mealPlanFacade.findByMealplanNameContaining(text);
        return ResponseEntity.ok(mealPlans);
    }
}
