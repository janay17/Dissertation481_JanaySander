package com.sander.fantastic_food.data_model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Mealplan", schema = "dbo")
@AttributeOverride(name = "id", column = @Column(name = "MealplanId"))
@Getter
@Setter
public class MealPlanDataModel extends IdentifiableDataModel{
    private String mealplanName;
    private String mealplanDescription;
}
