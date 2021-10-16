import 'dart:convert';

import 'package:fantastic_food/domain/recipe.dart';
import 'package:fantastic_food/domain/user_profile.dart';
import 'package:fantastic_food/user_interface/main_components/recipe_card.dart';
import 'package:fantastic_food/user_interface/navigation/menu_bloc.dart';
import 'package:fantastic_food/user_interface/styles/textInput.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class FavouriteRecipes extends StatefulWidget with MenuStates {
  @override
  _FavouriteRecipesState createState() => _FavouriteRecipesState();
}

class _FavouriteRecipesState extends State<FavouriteRecipes> {
  String _searchText = "";
  var storage = FlutterSecureStorage();
  UserProfile currentUser;

  Future<List<Recipe>> getCurrentUserFavourites(String searchText) async {
    List<Recipe> results = [];
    return await storage.read(key: "current_user").then((value) {
      currentUser = UserProfile.fromJson(jsonDecode(value));
      if (searchText == "") {
        return currentUser.userFavourites;
      } else {
        for (Recipe recipe in currentUser.userFavourites) {
          if (recipe.recipeName
              .toLowerCase()
              .contains(_searchText.toLowerCase())) {
            results.add(recipe);
          }
        }
        return results;
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
        child: Container(
            child: Column(children: [
      Form(
          child: Column(children: [
        Padding(
          padding: const EdgeInsets.only(left: 20, right: 20, top: 10),
          child: TextFormField(
            cursorColor: Theme.of(context).primaryColor,
            style:
                TextStyle(color: Theme.of(context).primaryColor, fontSize: 18),
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
      ])),
      SingleChildScrollView(
        child: FutureBuilder(
          future: getCurrentUserFavourites(_searchText),
          builder: (context, snapshot) {
            if (snapshot.connectionState == ConnectionState.done) {
              if (snapshot.hasError) {
                return Text(
                  'A connection problem occurred',
                  style: TextStyle(
                      fontSize: 18.0, color: Theme.of(context).primaryColor),
                );
              } else if (snapshot.hasData) {
                List<Recipe> recipes = snapshot.data;
                if (recipes.length == 0) {
                  return Text(
                    'No results found',
                    style: TextStyle(
                        fontSize: 18.0, color: Theme.of(context).primaryColor),
                  );
                } else
                  return Container(
                    height: 500,
                    width: MediaQuery.of(context).size.width,
                    child: GridView.count(
                        crossAxisCount: 2,
                        shrinkWrap: true,
                        childAspectRatio: 0.8,
                        children: List.generate(recipes.length, (index) {
                          return RecipeCard(
                            recipe: recipes[index],
                            currentUser: currentUser,
                          );
                        })),
                  );
              } else {
                return Text("");
              }
            } else if (snapshot.connectionState == ConnectionState.waiting) {
              return Text(
                'Loading...',
                style: TextStyle(
                    fontSize: 18.0, color: Theme.of(context).primaryColor),
              );
            } else {
              return Text('');
            }
          },
        ),
      ),
    ])));
  }
}
