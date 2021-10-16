import 'dart:convert';

import 'package:fantastic_food/domain/user_profile.dart';
import 'package:fantastic_food/main.dart';
import 'package:fantastic_food/user_interface/main_components/my_profile.dart';
import 'package:fantastic_food/user_interface/navigation/menu_bloc.dart';
import 'package:fantastic_food/user_interface/styles/textInput.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class Home extends StatefulWidget with MenuStates {
  @override
  _HomeState createState() => _HomeState();
}

class _HomeState extends State<Home> {
  var storage = FlutterSecureStorage();
  UserProfile currentUser;
  double bmi;
  List<String> galleryUrls = [
    "https://www.lifelinescreening.com/-/media/project/life-line-screening/life-line-screening/6-health-education/articles/diet-and-nutrition/fruits-and-vegetables.jpg",
    "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/gettyimages-1036880806.jpg?crop=0.6666666666666666xw:1xh;center,top&resize=640:*",
    "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/chorizo-mozarella-gnocchi-bake-cropped-9ab73a3.jpg?quality=90&resize=700%2C636"
  ];

  Future<UserProfile> getCurrentUser() async {
    await storage.read(key: "current_user").then((value) {
      currentUser = UserProfile.fromJson(jsonDecode(value));
    });
    return currentUser;
  }

  String calculateBMI(String weightText, String heightText) {
    double weight = double.parse(weightText);
    double height = double.parse(heightText);

    bmi = weight / (height * height);
    return bmi.toStringAsFixed(2);
  }

  String bmiStatus() {
    if (bmi < 18.5) return "You are underweight";
    if (bmi >= 18.5 && bmi < 25) return "You are healthy";
    if (bmi >= 25 && bmi < 30) return "You are overweight";
    if (bmi >= 30) return "You are obese";
    return null;
  }

  @override
  Widget build(BuildContext context) {
    getCurrentUser();

    return SingleChildScrollView(
      child: Container(
          child: Column(
        children: [
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 5.0),
            child: SingleChildScrollView(
              scrollDirection: Axis.horizontal,
              child: Container(
                height: 250.0,
                child: Row(children: renderImages()),
              ),
            ),
          ),
          Icon(
            Icons.account_circle,
            color: Theme.of(context).primaryColor,
            size: 150.0,
          ),
          FutureBuilder(
              future: getCurrentUser(),
              builder: (context, snapshot) {
                if (snapshot.connectionState == ConnectionState.done) {
                  if (snapshot.hasError) {
                    return Text(
                      'A connection problem occurred',
                      style: TextStyle(
                          fontSize: 18.0,
                          color: Theme.of(context).primaryColor),
                    );
                  } else if (snapshot.hasData) {
                    currentUser = snapshot.data;
                    return Column(
                      children: [
                        Text(
                          'Your Body Mass Index:',
                          style: TextStyle(
                              color: Theme.of(context).primaryColor,
                              fontSize: 30.0),
                        ),
                        Text(
                          currentUser.weight == null ||
                                  currentUser.height == null
                              ? 'Unknown'
                              : calculateBMI(
                                  currentUser.weight, currentUser.height),
                          style: TextStyle(
                              color: Theme.of(context).highlightColor,
                              fontSize: 45.0),
                        ),
                        Text(
                          currentUser.weight == null ||
                                  currentUser.height == null
                              ? ''
                              : bmiStatus(),
                          style: TextStyle(
                              color: Theme.of(context).primaryColor,
                              fontSize: 40.0),
                        ),
                      ],
                    );
                  } else {
                    return Text("");
                  }
                } else if (snapshot.connectionState ==
                    ConnectionState.waiting) {
                  return Text(
                    'Loading...',
                    style: TextStyle(
                        fontSize: 18.0, color: Theme.of(context).primaryColor),
                  );
                } else {
                  return Text('');
                }
              }),
          Padding(
            padding:
                const EdgeInsets.symmetric(vertical: 20.0, horizontal: 20.0),
            child: ElevatedButton(
              style: buttonDarkStyle,
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => MyProfile()),
                );
              },
              child: Container(
                width: MediaQuery.of(context).size.width,
                child: Padding(
                  padding: const EdgeInsets.only(top: 8, bottom: 12),
                  child: Text(
                    "UPDATE MY WEIGHT",
                    style: darkButtonTextStyle,
                    textAlign: TextAlign.center,
                  ),
                ),
              ),
            ),
          ),
          SizedBox(
            height: 50,
          )
        ],
      )),
    );
  }

  List<Widget> renderImages() {
    var temp = <Widget>[];
    for (var imagePath in galleryUrls) {
      temp.add(Container(
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(10),
          boxShadow: [
            BoxShadow(
              color: Colors.black.withOpacity(0.1),
              spreadRadius: 1,
              blurRadius: 2,
              offset: Offset(0, 3),
            ),
          ],
        ),
        margin: EdgeInsets.all(20),
        child: ClipRRect(
            borderRadius: BorderRadius.circular(20.0),
            child: FadeInImage(
                height: 250.0,
                width: 280.0,
                fit: BoxFit.cover,
                image: NetworkImage(imagePath),
                placeholder: NetworkImage(defaultImage))),
      ));
    }
    return temp;
  }
}
