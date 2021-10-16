package com.sander.fantastic_food.facade;

import com.sander.fantastic_food.Allergy;
import com.sander.fantastic_food.data_model.AllergyDataModel;
import com.sander.fantastic_food.mapper.AllergyMapper;
import com.sander.fantastic_food.repository.AllergyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AllergyFacade implements CRUD<Allergy, Integer>{

    private final AllergyRepository allergyRepository;
    Logger LOGGER = LoggerFactory.getLogger(AllergyFacade.class);

    @Autowired
    public AllergyFacade(AllergyRepository allergyRepository) {
        this.allergyRepository = allergyRepository;
    }

    @Override
    public Allergy findById(Integer id) {
        Optional<AllergyDataModel> dataModel = this.allergyRepository.findById(id);
        if (dataModel.isPresent()) {
            return AllergyMapper.INSTANCE.toAllergy(dataModel.get());
        } else {
            LOGGER.warn("Allergy not found by given ID, {}", id);
            return null;
        }
    }

    @Override
    public List<Allergy> findAll() {
        List<AllergyDataModel> dataModels = this.allergyRepository.findAll();
        if (!dataModels.isEmpty()) {
            return AllergyMapper.INSTANCE.toAllergies(dataModels);
        } else {
            LOGGER.warn("No allergies found in database");
            return null;
        }
    }

    @Override
    public Allergy saveChanges(Allergy value) {
        if(value != null){
            return AllergyMapper.INSTANCE.toAllergy(allergyRepository.save(AllergyMapper.INSTANCE.toAllergyDataModel(value)));
        } else {
            LOGGER.warn("No allergy changes were saved");
            return null;
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        allergyRepository.deleteById(id);
        if(findById(id) != null) {
            LOGGER.warn("The allergy was not deleted");
            return false;
        } else {
            return true;
        }
    }

    public List<Allergy> findByAllergyNameContaining(String allergyName) {
        List<AllergyDataModel> dataModels = this.allergyRepository.findByAllergyNameContaining(allergyName);
        if (!dataModels.isEmpty()) {
            return AllergyMapper.INSTANCE.toAllergies(dataModels);
        } else {
            LOGGER.warn("No allergies found in database");
            return null;
        }
    }
}
