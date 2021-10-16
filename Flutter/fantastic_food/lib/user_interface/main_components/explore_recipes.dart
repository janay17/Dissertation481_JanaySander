import 'dart:convert';

import 'package:fantastic_food/domain/recipe.dart';
import 'package:fantastic_food/domain/user_profile.dart';
import 'package:fantastic_food/services/recipe_service.dart';
import 'package:fantastic_food/user_interface/main_components/recipe_card.dart';
import 'package:fantastic_food/user_interface/navigation/menu_bloc.dart';
import 'package:fantastic_food/user_interface/styles/textInput.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:need_resume/need_resume.dart';

class ExploreRecipes extends StatefulWidget with MenuStates {
  @override
  _ExploreRecipesState createState() => _ExploreRecipesState();
}

class _ExploreRecipesState extends ResumableState<ExploreRecipes> {
  bool basedOnPreferences = false;
  String _searchText = "";
  int _pageNumber = 0;
  var storage = FlutterSecureStorage();
  UserProfile currentUser;

  void getCurrentUser() async {
    await storage.read(key: "current_user").then((value) {
      currentUser = UserProfile.fromJson(jsonDecode(value));
    });
  }

  @override
  void initState() {
    getCurrentUser();
    super.initState();
  }

  @override
  void onResume() {
    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Container(
        child: Column(
          children: [
            Form(
              child: Column(
                children: [
                  Padding(
                    padding:
                        const EdgeInsets.only(left: 20, right: 20, top: 10),
                    child: TextFormField(
                      cursorColor: Theme.of(context).primaryColor,
                      style: TextStyle(
                          color: Theme.of(context).primaryColor, fontSize: 18),
                      decoration: searchInputDecoration.copyWith(
                        prefixIcon: Icon(
                          Icons.search,
                          color: Theme.of(context).primaryColor,
                        ),
                        labelText: 'Search',
                        labelStyle: TextStyle(
                          fontSize: 20.0,
                          color: Theme.of(context).primaryColor,
                        ),
                      ),
                      onChanged: (value) {
                        setState(() {
                          _searchText = value;
                        });
                      },
                    ),
                  ),
                  Row(
                    children: [
                      Spacer(),
                      Checkbox(
                        value: basedOnPreferences,
                        fillColor: MaterialStateProperty.all<Color>(
                            Theme.of(context).primaryColor),
                        onChanged: (val) {
                          setState(() {
                            basedOnPreferences = val;
                          });
                        },
                      ),
                      GestureDetector(
                        child: Text(
                          "Based on my meal plans and allergies",
                          style: TextStyle(
                              color: Theme.of(context).primaryColor,
                              fontSize: 18),
                        ),
                        onTap: () {
                          setState(() {
                            basedOnPreferences = !basedOnPreferences;
                          });
                        },
                      ),
                      Spacer()
                    ],
                  )
                ],
              ),
            ),
            SingleChildScrollView(
              child: FutureBuilder(
                future: RecipeService().searchAllRecipes(
                    _searchText, _pageNumber, basedOnPreferences),
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
                      List<Recipe> recipes = snapshot.data;
                      if (recipes.length == 0) {
                        return Text(
                          'No results found',
                          style: TextStyle(
                              fontSize: 18.0,
                              color: Theme.of(context).primaryColor),
                        );
                      } else
                        return Container(
                          height: 500,
                          width: MediaQuery.of(context).size.width,
                          child: GridView.count(
                              crossAxisCount: 2,
                              shrinkWrap: true,
                              // physics: NeverScrollableScrollPhysics(),
                              childAspectRatio: 0.8,
                              children: List.generate(recipes.length, (index) {
                                return RecipeCard(
                                  currentUser: currentUser,
                                  recipe: recipes[index],
                                );
                              })),
                        );
                    } else {
                      return Text("");
                    }
                  } else if (snapshot.connectionState ==
                      ConnectionState.waiting) {
                    return Text(
                      'Loading...',
                      style: TextStyle(
                          fontSize: 18.0,
                          color: Theme.of(context).primaryColor),
                    );
                  } else {
                    return Text('');
                  }
                },
              ),
            ),
            Align(
              alignment: Alignment.bottomCenter,
              child: Row(
                children: [
                  Spacer(),
                  GestureDetector(
                    child: Icon(
                      Icons.skip_previous,
                      color: Theme.of(context).primaryColor,
                    ),
                    onTap: () {
                      setState(() {
                        _pageNumber -= 1;
                      });
                    },
                  ),
                  Spacer(),
                  Text(
                    "Page ${_pageNumber + 1}",
                    style: TextStyle(
                        color: Theme.of(context).primaryColor, fontSize: 18),
                  ),
                  Spacer(),
                  GestureDetector(
                    child: Icon(
                      Icons.skip_next,
                      color: Theme.of(context).primaryColor,
                    ),
                    onTap: () {
                      setState(() {
                        _pageNumber += 1;
                      });
                    },
                  ),
                  Spacer()
                ],
              ),
            )
          ],
        ),
      ),
    );
  }
}
