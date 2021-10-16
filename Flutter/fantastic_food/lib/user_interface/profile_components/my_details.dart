import 'dart:convert';

import 'package:fantastic_food/domain/user_profile.dart';
import 'package:fantastic_food/services/user_profile_service.dart';
import 'package:fantastic_food/user_interface/styles/textInput.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class MyDetails extends StatefulWidget {
  @override
  _MyDetailsState createState() => _MyDetailsState();
}

class _MyDetailsState extends State<MyDetails> {

  var storage = FlutterSecureStorage();
  UserProfile currentUser;
  bool obscureText = true;
  final TextEditingController _weightController = TextEditingController();
  final TextEditingController _heightController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  final TextEditingController _confirmController = TextEditingController();


  void getCurrentUser() async {
    await storage.read(key: "current_user").then((value) {
      currentUser = UserProfile.fromJson(jsonDecode(value));
      originalValues();
    });
  }

  void originalValues() {
    _weightController.text = currentUser.weight ?? "";
    _heightController.text = currentUser.height ?? "";
    _passwordController.text = "";
    _confirmController.text = "";
  }

  
  void togglePasswordView() {
    setState(() {
      obscureText = !obscureText;
    });
  }

  @override
  Widget build(BuildContext context) {
    getCurrentUser();

    return SingleChildScrollView(
        child: Column(
          children: [
            Row(
              children: [
                Spacer(),
                Padding(
                  padding: const EdgeInsets.only(left: 90),
                  child: Icon(Icons.account_circle, color: Theme.of(context).primaryColor, size: 100.0,),
                ),
                Spacer(),
                GestureDetector(
                  child: Icon(Icons.undo, color: Theme.of(context).primaryColor, size: 40,),
                  onTap: () {
                    originalValues();
                  },
                ),
                Spacer()
              ],
            ),
            Container(
              height: 200,
              width: MediaQuery.of(context).size.width,
              decoration: BoxDecoration(
                border: Border.all(color: Theme.of(context).primaryColor, width: 2),
                borderRadius: BorderRadius.circular(40)
              ),
              margin: EdgeInsets.symmetric(horizontal: 10),
              padding: EdgeInsets.only(top: 5),
              child: Column(
                children: [
                  Text("Optional Information:", style: TextStyle(color: Theme.of(context).primaryColor, fontSize: 25),),
                  Row(
                    children: [
                      Flexible(
                        child: Padding(
                          padding: const EdgeInsets.only(left: 30, top: 10, right: 20),
                          child: TextFormField(
                            controller: _weightController,
                            keyboardType: TextInputType.number,
                            cursorColor: Theme.of(context).highlightColor,
                            decoration: textInputDecoration.copyWith(
                              labelText: 'Weight',
                              labelStyle: TextStyle(fontSize: 20.0, color: Colors.white,),
                            ),
                          ),
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.only(right: 20),
                        child: Text("kg", style: TextStyle(color: Theme.of(context).primaryColor, fontSize: 25),),
                      )
                    ],
                  ),
                  Row(
                    children: [
                      Flexible(
                        child: Padding(
                          padding: const EdgeInsets.only(left: 30, top: 10, right: 20),
                          child: TextFormField(
                            controller: _heightController,
                            keyboardType: TextInputType.number,
                            cursorColor: Theme.of(context).highlightColor,
                            decoration: textInputDecoration.copyWith(
                              labelText: 'Height',
                              labelStyle: TextStyle(fontSize: 20.0, color: Colors.white,),
                            ),
                          ),
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.only(right: 20),
                        child: Text("m", style: TextStyle(color: Theme.of(context).primaryColor, fontSize: 25),),
                      )
                    ],
                  )
                ],
              ),
            ),
            Container(
              height: 215,
              width: MediaQuery.of(context).size.width,
              decoration: BoxDecoration(
                border: Border.all(color: Theme.of(context).primaryColor, width: 2),
                borderRadius: BorderRadius.circular(40)
              ),
              margin: EdgeInsets.symmetric(horizontal: 10, vertical: 10),
              padding: EdgeInsets.only(top: 5),
              child: Padding(
                padding: const EdgeInsets.symmetric(horizontal: 30),
                child: Column(
                  children: [
                    Padding(
                      padding: const EdgeInsets.only(bottom: 10),
                      child: Text("Sign in details:", style: TextStyle(color: Theme.of(context).primaryColor, fontSize: 25),),
                    ),
                    SizedBox(height: 10,),
                    TextFormField(
                      obscureText: obscureText,
                      controller: _passwordController,
                      decoration: textInputDecoration.copyWith(
                        prefixIcon: Icon(Icons.vpn_key, color: Theme.of(context).highlightColor,),
                        labelText: 'Password',
                        labelStyle: TextStyle(fontSize: 20.0, color: Colors.white,),
                        suffixIcon: GestureDetector(
                          child: obscureText?Icon(Icons.visibility,color: Theme.of(context).highlightColor,) 
                              : Icon(Icons.visibility_off,color: Theme.of(context).highlightColor,),
                          onTap: () {
                            togglePasswordView();
                          },
                        )
                      ),
                    ),
                    SizedBox(height: 10,),
                    TextFormField(
                      controller: _confirmController,
                      obscureText: obscureText,
                      decoration: textInputDecoration.copyWith(
                        prefixIcon: Icon(Icons.vpn_key, color: Theme.of(context).highlightColor,),
                        labelText: 'Confirm Password',
                        labelStyle: TextStyle(fontSize: 20.0, color: Colors.white,),
                      ),
                    ),
                  ],
                ),
              ),
            )
          ],
        )
    );
  }

  @override
  void deactivate() {
    if(_passwordController.text.isEmpty) {
      currentUser.weight = _weightController.text.isEmpty? null: _weightController.text;
      currentUser.height = _heightController.text.isEmpty? null: _heightController.text;
      UserProfileService().updateUserData(currentUser);
    } else {
      if(_passwordController.text.length >= 8 && _passwordController.text == _confirmController.text) {
        currentUser.weight = _weightController.text.isEmpty? null: _weightController.text;
        currentUser.height = _heightController.text.isEmpty? null: _heightController.text;
        currentUser.passwordHash = _passwordController.text;
        UserProfileService().changeUserPassword(currentUser);
      }
    }
    super.deactivate();
  }
}