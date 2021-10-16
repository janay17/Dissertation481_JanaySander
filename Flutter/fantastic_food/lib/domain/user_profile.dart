import 'package:fantastic_food/domain/allergy.dart';
import 'package:fantastic_food/domain/meal_plan.dart';
import 'package:fantastic_food/domain/recipe.dart';

class UserProfile {
  int id;
  String email;
  String passwordHash;
  String weight;
  String height;
  List<Allergy> userAllergies;
  List<MealPlan> userMealPlans;
  List<Recipe> userFavourites;
  List<Recipe> userRecipes;

  UserProfile(this.id, this.email, this.passwordHash, this.weight, this.height, 
    this.userAllergies, this.userMealPlans, this.userFavourites, this.userRecipes);

  UserProfile.byLogin({this.email, this.passwordHash});

  factory UserProfile.fromJson(dynamic json) {

    bool anyAllergies = false;
    bool anyMealPlans = false;
    bool anyFavourites = false;
    bool anyRecipes = false;

    if(json['userAllergies'] != null){
      anyAllergies = true;
    }
    if(json['userMealPlans'] != null) {
      anyMealPlans = true;
    }
    if(json['userFavourites'] != null){
      anyFavourites = true;
    }
    if(json['userRecipes'] != null) {
      anyRecipes = true;
    }

    return UserProfile(json['id'] as int, json['email'] as String, json['passwordHash'] as String, 
        json['weight'] as String, json['height'] as String, 
        anyAllergies? List<Allergy>.from(json['userAllergies'].map((model)=> Allergy.fromJson(model))): null,
        anyMealPlans? List<MealPlan>.from(json['userMealPlans'].map((model)=> MealPlan.fromJson(model))): null,
        anyFavourites? List<Recipe>.from(json['userFavourites'].map((model)=> Recipe.fromJson(model))): null,
        anyRecipes? List<Recipe>.from(json['userRecipes'].map((model)=> Recipe.fromJson(model))): null);
  }


  Map<String, dynamic> toJson() {
    return {
      "id": this.id,
      "email": this.email,
      "passwordHash": this.passwordHash,
      "weight": this.weight,
      "height": this.height,
      "userAllergies": this.userAllergies,
      "userMealPlans": this.userMealPlans,
      "userFavourites": this.userFavourites,
      "userRecipes": this.userRecipes
    };
  }
}