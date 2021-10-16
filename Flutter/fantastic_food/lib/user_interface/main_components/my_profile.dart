import 'package:fantastic_food/user_interface/navigation/menu_bloc.dart';
import 'package:fantastic_food/user_interface/profile_components/my_allergies.dart';
import 'package:fantastic_food/user_interface/profile_components/my_details.dart';
import 'package:fantastic_food/user_interface/profile_components/my_mealplans.dart';
import 'package:fantastic_food/user_interface/profile_components/my_recipes.dart';
import 'package:flutter/material.dart';

class MyProfile extends StatefulWidget with MenuStates {
  @override
  _MyProfileState createState() => _MyProfileState();
}

class _MyProfileState extends State<MyProfile> {
  Widget currentBody = MyDetails();
  int _selectedIndex = 0;

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
        child: Container(
            width: MediaQuery.of(context).size.width,
            height: MediaQuery.of(context).size.height,
            child: Scaffold(
                backgroundColor: Theme.of(context).accentColor,
                appBar: AppBar(
                  title: Text("My Profile", style: TextStyle(fontSize: 30.0)),
                ),
                bottomNavigationBar: BottomNavigationBar(
                  type: BottomNavigationBarType.fixed,
                  backgroundColor: Theme.of(context).primaryColor,
                  selectedItemColor: Colors.white,
                  unselectedItemColor: Colors.white.withOpacity(.60),
                  currentIndex: _selectedIndex,
                  selectedFontSize: 14,
                  unselectedFontSize: 14,
                  onTap: (value) {
                    setState(() {
                      switch (value) {
                        case 0:
                          currentBody = MyDetails();
                          break;
                        case 1:
                          currentBody = MyAllergies();
                          break;
                        case 2:
                          currentBody = MyMealPlans();
                          break;
                        case 3:
                          currentBody = MyRecipes();
                          break;
                        default:
                          currentBody = MyDetails();
                      }
                      _selectedIndex = value;
                    });
                  },
                  items: [
                    BottomNavigationBarItem(
                      label: "My Details",
                      icon: Icon(Icons.person),
                    ),
                    BottomNavigationBarItem(
                      label: "Allergies",
                      icon: Icon(Icons.restaurant_menu),
                    ),
                    BottomNavigationBarItem(
                      label: "Meal Plans",
                      icon: Icon(Icons.fastfood),
                    ),
                    BottomNavigationBarItem(
                      label: "My Recipes",
                      icon: Icon(Icons.library_books),
                    ),
                  ],
                ),
                body: currentBody)));
  }
}
