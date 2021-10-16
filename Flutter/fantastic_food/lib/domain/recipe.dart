import 'package:fantastic_food/domain/allergy.dart';
import 'package:fantastic_food/domain/meal_plan.dart';

class Recipe {
  int id;
  String recipeName;
  String instructions;
  int preparationTime;
  String difficulty;
  String recipeImage;
  List<Allergy> recipeAllergies;
  List<MealPlan> recipeMealPlans;

  Recipe(this.id, this.recipeName, this.instructions, this.preparationTime, this.difficulty,
    this.recipeImage, this.recipeAllergies, this.recipeMealPlans);

  Recipe.clear();

  factory Recipe.fromJson(dynamic json) {
    return Recipe(json['id'] as int, json['recipeName'] as String, json['instructions'] as String, 
        json['preparationTime'] as int, json['difficulty'] as String, json['recipeImage'] as String,
        List<Allergy>.from(json['recipeAllergies'].map((model)=> Allergy.fromJson(model))),
        List<MealPlan>.from(json['recipeMealPlans'].map((model)=> MealPlan.fromJson(model))));
  }

   Map<String, dynamic> toJson() {
    return {
      "id": this.id,
      "recipeName": this.recipeName,
      "instructions": this.instructions,
      "preparationTime": this.preparationTime,
      "difficulty": this.difficulty,
      "recipeImage": this.recipeImage,
      "recipeAllergies": this.recipeAllergies,
      "recipeMealPlans": this.recipeMealPlans
    };
  }

  @override
  bool operator ==(Object other) {
    return other is Recipe && other.id == id;
  }

  int get hashcode => id.hashCode^recipeName.hashCode;
}