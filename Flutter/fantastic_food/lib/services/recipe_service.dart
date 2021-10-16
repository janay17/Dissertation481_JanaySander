import 'package:fantastic_food/domain/allergy.dart';
import 'package:fantastic_food/domain/meal_plan.dart';
import 'package:fantastic_food/domain/recipe.dart';
import 'package:fantastic_food/domain/user_profile.dart';
import 'package:fantastic_food/services/authentication_service.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:http/http.dart' as http;
import 'dart:async';
import 'dart:convert';

class RecipeService {

  final String serverIp = "10.0.2.2:8080";
  final storage = FlutterSecureStorage();

  Future<List<Recipe>> searchAllRecipes(String searchText, int page, bool basedOnPreferences) async {

    String token = await storage.read(key: "token");
    if(searchText.isEmpty){
      searchText = '*';
    }

    http.Response response = await http.get(
      Uri.http("$serverIp", "/recipes/search/$searchText/$page"),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
        'Accept': 'application/json',
        'Authorization': 'Bearer $token'
      },
    );
    if(response.statusCode == 200){

      List<Recipe> allRecipes = List<Recipe>.from(jsonDecode(response.body).map((model)=> Recipe.fromJson(model)));
      if(!basedOnPreferences) {
        return allRecipes;
      } else {
        UserProfile currentUser =   UserProfile.fromJson(jsonDecode(await storage.read(key: "current_user")));
        List<Recipe> newRecipes = [];
        for(Recipe recipe in allRecipes) {
          bool containsAllergens = false;
          for(Allergy allergy in currentUser.userAllergies) {
            if(recipe.recipeAllergies.contains(allergy)) {
              containsAllergens = true;
            }
          }
          bool matchMealPlans = false;
          for(MealPlan mealplan in currentUser.userMealPlans) {
            if(recipe.recipeMealPlans.contains(mealplan)) {
              matchMealPlans = true;
            }
          }
          if(!containsAllergens && matchMealPlans) {
            newRecipes.add(recipe);
          }
        }
        return newRecipes;
      }
    } else return null;  
  }

  Future<Recipe> updateRecipeData(Recipe recipe) async {

    String token = await storage.read(key: "token");
    var body = jsonEncode(recipe.toJson());

    http.Response response = await http.put(
      Uri.http("$serverIp", "/recipes"),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
        'Accept': 'application/json',
        'Authorization': 'Bearer $token'
      },
      body: body
    );
    if(response.statusCode == 200){
      Recipe newRecipe = Recipe.fromJson(jsonDecode(response.body));
      return newRecipe;
    }  
    return null;
  }

   Future<Recipe> saveNewRecipe(Recipe recipe) async {

    String token = await storage.read(key: "token");
    var body = jsonEncode(recipe.toJson());

    http.Response response = await http.post(
      Uri.http("$serverIp", "/recipes"),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
        'Accept': 'application/json',
        'Authorization': 'Bearer $token'
      },
      body: body
    );
    if(response.statusCode == 200){
      Recipe newRecipe = Recipe.fromJson(jsonDecode(response.body));
      return newRecipe;
    }  
    return null;
  }

  
   Future<bool> deleteRecipe(int recipeID, String email) async {

    String token = await storage.read(key: "token");

    http.Response response = await http.delete(
      Uri.http("$serverIp", "/recipes/$recipeID"),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
        'Accept': 'application/json',
        'Authorization': 'Bearer $token'
      },
    );
    if(response.statusCode == 200){
      AuthenticationService().getUserByEmail(email);
      return true;
    }  
    return false;
  }
}
