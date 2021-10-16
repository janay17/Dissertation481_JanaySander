import 'package:fantastic_food/domain/recipe.dart';
import 'package:fantastic_food/domain/user_profile.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class UserProfileService {
  final String serverIp = "10.0.2.2:8080";
  final storage = FlutterSecureStorage();

  void updateUserData(UserProfile userProfile) async {
    String token = await storage.read(key: "token");
    var body = jsonEncode(userProfile.toJson());

    http.Response response = await http.put(Uri.http("$serverIp", "/profiles"),
        headers: <String, String>{
          'Content-Type': 'application/json; charset=UTF-8',
          'Accept': 'application/json',
          'Authorization': 'Bearer $token'
        },
        body: body);
    if (response.statusCode == 200) {
      UserProfile currentUser = UserProfile.fromJson(jsonDecode(response.body));
      await storage.write(
          key: "current_user", value: json.encode(currentUser.toJson()));
    }
  }

  void changeUserPassword(UserProfile userProfile) async {
    String token = await storage.read(key: "token");
    var body = jsonEncode(userProfile.toJson());

    http.Response response =
        await http.put(Uri.http("$serverIp", "/profiles/changepassword"),
            headers: <String, String>{
              'Content-Type': 'application/json; charset=UTF-8',
              'Accept': 'application/json',
              'Authorization': 'Bearer $token'
            },
            body: body);
    if (response.statusCode == 200) {
      UserProfile currentUser = UserProfile.fromJson(jsonDecode(response.body));
      await storage.write(
          key: "current_user", value: json.encode(currentUser.toJson()));
    }
  }

  void toggleFavourite(UserProfile profile, Recipe recipe) async {
    if (!profile.userFavourites.contains(recipe)) {
      profile.userFavourites.add(recipe);
      updateUserData(profile);
    } else {
      profile.userFavourites.remove(recipe);
      updateUserData(profile);
    }
  }
}
