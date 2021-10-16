import 'dart:convert';

import 'package:fantastic_food/domain/recipe.dart';
import 'package:fantastic_food/domain/user_profile.dart';
import 'package:fantastic_food/main.dart';
import 'package:fantastic_food/user_interface/profile_components/delete_recipe_dialog.dart';
import 'package:fantastic_food/user_interface/profile_components/edit_recipe.dart';
import 'package:fantastic_food/user_interface/styles/textInput.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class MyRecipes extends StatefulWidget {
  @override
  _MyRecipesState createState() => _MyRecipesState();
}

class _MyRecipesState extends State<MyRecipes> {

  var storage = FlutterSecureStorage();
  UserProfile currentUser;
  String _searchText = "";

  Future<List<Recipe>> getCurrentUserRecipes(String searchText) async {
    List<Recipe> results = [];
    return await storage.read(key: "current_user").then((value) {
      currentUser = UserProfile.fromJson(jsonDecode(value));
      if(searchText == "") {
        return currentUser.userRecipes;
      } else {
        for(Recipe recipe in currentUser.userRecipes) {
          if(recipe.recipeName.toLowerCase().contains(_searchText.toLowerCase())) {
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
        child: Column(
          children: [
            Form(
              child: Column(
                children: [
                  Row(
                    children: [
                      Padding(
                        padding: const EdgeInsets.only(left: 20, right: 10, top: 10),
                        child: GestureDetector(
                          child: Icon(Icons.refresh, color: Theme.of(context).primaryColor, size: 30.0,),
                          onTap: () {
                           setState(() {});
                          },
                        ),
                      ),
                      Flexible(
                        child: Padding(
                          padding: const EdgeInsets.only(left: 20, right: 20, top: 10),
                          child: TextFormField(
                            cursorColor: Theme.of(context).primaryColor,
                            style: TextStyle(color: Theme.of(context).primaryColor, fontSize: 18),
                            decoration: searchInputDecoration.copyWith(
                              prefixIcon: Icon(Icons.search, color: Theme.of(context).primaryColor,),
                              labelText: 'Search',
                              labelStyle: TextStyle(fontSize: 20.0, color: Theme.of(context).primaryColor,),
                            ),
                            onChanged: (value) {
                              setState(() {
                                _searchText = value;
                              });
                            },
                          ),
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.only(right: 20, top: 10),
                        child: GestureDetector(
                          child: Icon(Icons.add, color: Theme.of(context).primaryColor, size: 30.0,),
                          onTap: () {
                            Navigator.push(
                              context,
                              MaterialPageRoute(builder: (context) => EditRecipe(currentRecipe: null, currentUser: currentUser,)),
                            );
                          },
                        ),
                      )
                    ],
                  ),
                ]
              )
            ),
            SingleChildScrollView(
              child: Container(
                height: 550,
                child: FutureBuilder(
                    future: getCurrentUserRecipes(_searchText),
                    builder: (context, snapshot){
                      if(snapshot.connectionState == ConnectionState.done){
                        if(snapshot.hasError){
                          return Text('A connection problem occurred', style: TextStyle(fontSize: 18.0,color: Theme.of(context).primaryColor),);
                        }
                        else if(snapshot.hasData) {
                        List<Recipe> recipes = snapshot.data;
                        if(recipes.length == 0){
                          return Text('No results found', style: TextStyle(fontSize: 18.0,color: Theme.of(context).primaryColor),);
                        } else
                          return ListView.builder(
                            itemCount: recipes.length,
                            itemBuilder: (context, index) {
                              return Column(
                                children: [
                                  Row(
                                    children: [
                                      Spacer(),
                                      Container(
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
                                        margin: EdgeInsets.only(top: 20, bottom: 20, right: 25),
                                        child: ClipRRect(
                                        borderRadius: BorderRadius.circular(20.0),
                                          child: FadeInImage(
                                              height: 150.0,
                                              width: 280.0,
                                              fit: BoxFit.cover,
                                              image: NetworkImage(recipes[index].recipeImage), 
                                              placeholder: NetworkImage(defaultImage),
                                              imageErrorBuilder: (context, error, stacktrace) {
                                                return Container(
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
                                                margin: EdgeInsets.only(top: 20, bottom: 20, right: 25),
                                                child: ClipRRect(
                                                borderRadius: BorderRadius.circular(20.0),
                                                  child: FadeInImage(
                                                      height: 150.0,
                                                      width: 280.0,
                                                      fit: BoxFit.cover,
                                                      image: NetworkImage(defaultImage), 
                                                      placeholder: NetworkImage(defaultImage),
                                                    )
                                                  ),
                                                );
                                              },
                                            )
                                          ),
                                        ),
                                        Column(
                                          children: [
                                            Padding(
                                              padding: const EdgeInsets.only(bottom: 25.0),
                                              child: GestureDetector(
                                                child: Icon(Icons.edit, color: Theme.of(context).primaryColor, size: 30.0,),
                                                onTap: () {
                                                  Navigator.push(
                                                    context,
                                                    MaterialPageRoute(builder: (context) => EditRecipe(currentRecipe: recipes[index],
                                                        currentUser: currentUser,)),
                                                  );
                                                },
                                              ),
                                            ),
                                            GestureDetector(
                                              child: Icon(Icons.delete, color: Theme.of(context).primaryColor, size: 30.0,),
                                              onTap: () {
                                                DeleteRecipe().showAlertDialog(context: context, 
                                                  title: 'Delete Recipe', 
                                                  message: 'Are you sure you want to delete ${recipes[index].recipeName}?'
                                                  ,recipeID: recipes[index].id, email: currentUser.email);
                                              },
                                            )
                                          ],
                                        ),
                                        Spacer()
                                    ],
                                  ),
                                  Padding(
                                    padding: const EdgeInsets.only(bottom: 10),
                                    child: Text(recipes[index].recipeName, 
                                      style: TextStyle(color: Theme.of(context).primaryColor, fontSize: 20.0),
                                    ),
                                  )
                                ],
                              );
                            }
                          );
                        } else {
                          return Text("");
                        }
                      } else if(snapshot.connectionState == ConnectionState.waiting){
                        return Text('Loading...', style: TextStyle(fontSize: 18.0,color: Theme.of(context).primaryColor),);
                      } else {
                        return Text('');
                      }
                    },
                ),
              ),
            ),
            
          ]
        )
      )
    );
  }
}