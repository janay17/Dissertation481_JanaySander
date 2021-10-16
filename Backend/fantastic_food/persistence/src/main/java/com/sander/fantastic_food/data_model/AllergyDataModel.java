package com.sander.fantastic_food.data_model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Allergy", schema = "dbo")
@AttributeOverride(name = "id", column = @Column(name = "AllergyId"))
@Getter
@Setter
public class AllergyDataModel extends IdentifiableDataModel{
    private String allergyName;
    private String allergyDescription;
}
