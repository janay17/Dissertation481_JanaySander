import 'package:fantastic_food/domain/user_profile.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:http/http.dart' as http;
import 'dart:async';
import 'dart:convert';

class AuthenticationService {

  final String serverIp = "10.0.2.2:8080";
  final storage = FlutterSecureStorage();

  Future<String> logIn(String email, String password) async {

    var body = jsonEncode({'email': email, 'passwordHash': password});

    http.Response response = await http.post(
      Uri.http("$serverIp", "/api/login"),
      body: body
    );
    if(response.statusCode == 200){
      return response.body;
    } else return null;  
  }
  
  Future<UserProfile> signUp(String email, String password) async {

    var body = jsonEncode({'email': email, 'passwordHash': password});

    http.Response response = await http.post(
       Uri.http("$serverIp", "/profiles"),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: body
    );
    if(response.statusCode == 200){
      return UserProfile.fromJson(jsonDecode(response.body));
    } else return null;  
  }

  void getUserByEmail(String email) async {

    String token = await storage.read(key: "token");

    http.Response response = await http.get(
      Uri.http("$serverIp", "/profiles/email/$email"),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
        'Accept': 'application/json',
        'Authorization': 'Bearer $token'
      },
    );
    if(response.statusCode == 200){
      UserProfile currentUser = UserProfile.fromJson(jsonDecode(response.body));
      await storage.write(key: "current_user", value: json.encode(currentUser.toJson()));
    }  
  }
}