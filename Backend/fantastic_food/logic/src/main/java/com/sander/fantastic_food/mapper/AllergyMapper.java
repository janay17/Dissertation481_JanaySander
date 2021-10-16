package com.sander.fantastic_food.mapper;

import com.sander.fantastic_food.Allergy;
import com.sander.fantastic_food.data_model.AllergyDataModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AllergyMapper {
    AllergyMapper INSTANCE = Mappers.getMapper(AllergyMapper.class);

    Allergy toAllergy(AllergyDataModel dataModel);

    AllergyDataModel toAllergyDataModel(Allergy allergy);

    List<Allergy> toAllergies(List<AllergyDataModel> dataModels);
}
