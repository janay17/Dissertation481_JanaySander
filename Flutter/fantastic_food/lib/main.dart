import 'package:fantastic_food/user_interface/authentication/authentication_screen.dart';
import 'package:flutter/material.dart';
import 'package:flutter_phoenix/flutter_phoenix.dart';
import 'package:hexcolor/hexcolor.dart';

void main() {
  runApp(Phoenix(child: Main()));
}

class Main extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: Authentication(),
      theme: ThemeData(
        primaryColor: HexColor("#1E1F33"),
        accentColor: HexColor("#40B488"),
        highlightColor: HexColor("#FFFFFF"),
        brightness: Brightness.dark,
        fontFamily: 'Sanista'
      ),
    );
  }
}

String defaultImage = "https://evolutioneat.com/wp-content/uploads/2017/04/healthy_food_concept_with_question_mark.jpg";