import 'package:fantastic_food/domain/allergy.dart';
import 'package:fantastic_food/domain/meal_plan.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class MealPlanService {

  final String serverIp = "10.0.2.2:8080";
  final storage = FlutterSecureStorage();

  Future<List<MealPlan>> getAllMealPlans() async {
    String token = await storage.read(key: "token");

    http.Response response = await http.get(
      Uri.http("$serverIp", "/mealplans"),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
        'Accept': 'application/json',
        'Authorization': 'Bearer $token'
      },
    );
    if(response.statusCode == 200){
      return List<MealPlan>.from(jsonDecode(response.body).map((model)=> MealPlan.fromJson(model)));
    } else return null;
  }
}