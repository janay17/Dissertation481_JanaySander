package com.sander.fantastic_food.controller;

import com.sander.fantastic_food.Allergy;
import com.sander.fantastic_food.facade.AllergyFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/allergies")
public class AllergyController implements Controller<Allergy, Integer>{

    private final AllergyFacade allergyFacade;
    private static final Logger LOGGER = LoggerFactory.getLogger(AllergyController.class);

    public AllergyController(AllergyFacade allergyFacade) {
        this.allergyFacade = allergyFacade;
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<Allergy> getByID(@PathVariable("id") Integer id) {
        Allergy allergy = this.allergyFacade.findById(id);
        return ResponseEntity.ok(allergy);
    }

    @PostMapping
    public ResponseEntity<Allergy> saveNew(@RequestBody Allergy value) {
        Allergy allergy = this.allergyFacade.saveChanges(value);
        return ResponseEntity.ok(allergy);
    }

    @PutMapping
    public ResponseEntity<Allergy> updateExisting(@RequestBody Allergy value) {
        Allergy allergy = this.allergyFacade.saveChanges(value);
        return ResponseEntity.ok(allergy);
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.allergyFacade.deleteById(id));
    }

    @GetMapping
    public ResponseEntity<List<Allergy>> getAll() {
        List<Allergy> allergies = this.allergyFacade.findAll();
        return ResponseEntity.ok(allergies);
    }

    @GetMapping("/search/{search}")
    public ResponseEntity<List<Allergy>> getByAllergyName(@PathVariable("search") String text) {
        List<Allergy> allergies = this.allergyFacade.findByAllergyNameContaining(text);
        return ResponseEntity.ok(allergies);
    }
}
