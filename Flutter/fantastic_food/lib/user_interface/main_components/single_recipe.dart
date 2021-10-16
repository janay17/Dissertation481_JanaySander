import 'dart:convert';

import 'package:fantastic_food/domain/allergy.dart';
import 'package:fantastic_food/domain/meal_plan.dart';
import 'package:fantastic_food/domain/recipe.dart';
import 'package:fantastic_food/domain/user_profile.dart';
import 'package:fantastic_food/main.dart';
import 'package:fantastic_food/services/user_profile_service.dart';
import 'package:fantastic_food/user_interface/styles/textInput.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:share/share.dart';

class SingleRecipe extends StatefulWidget {
  const SingleRecipe({Key key, this.currentRecipe, this.currentUser})
      : super(key: key);

  @override
  _SingleRecipeState createState() => _SingleRecipeState();

  final Recipe currentRecipe;
  final UserProfile currentUser;
}

class _SingleRecipeState extends State<SingleRecipe> {
  var allergensBuffer = new StringBuffer();
  var mealPlanBuffer = new StringBuffer();

  void populateAllergensAndMealPlans() {
    allergensBuffer.clear();
    for (Allergy allergy in widget.currentRecipe.recipeAllergies) {
      allergensBuffer.write(allergy.allergyName + "\n");
    }
    mealPlanBuffer.clear();
    for (MealPlan mealPlan in widget.currentRecipe.recipeMealPlans) {
      mealPlanBuffer.write(mealPlan.mealplanName + "\n");
    }
  }

  @override
  Widget build(BuildContext context) {
    bool isFavourite =
        widget.currentUser.userFavourites.contains(widget.currentRecipe);
    populateAllergensAndMealPlans();
    return SingleChildScrollView(
        child: Container(
            width: MediaQuery.of(context).size.width,
            height: MediaQuery.of(context).size.height,
            child: Scaffold(
              backgroundColor: Theme.of(context).accentColor,
              appBar: AppBar(
                actions: [
                  Padding(
                    padding: const EdgeInsets.all(10.0),
                    child: GestureDetector(
                      child: Icon(
                          isFavourite ? Icons.favorite : Icons.favorite_outline,
                          color: isFavourite
                              ? Colors.red
                              : Theme.of(context).highlightColor),
                      onTap: () {
                        setState(() {
                          UserProfileService().toggleFavourite(
                              widget.currentUser, widget.currentRecipe);
                        });
                      },
                    ),
                  ),
                  Padding(
                    padding: const EdgeInsets.all(10.0),
                    child: GestureDetector(
                      child: Icon(Icons.share),
                      onTap: () async {
                        final RenderBox box = context.findRenderObject();
                        await Share.share(
                            '${widget.currentRecipe.recipeName}\nInstructions: \n${widget.currentRecipe.instructions}',
                            subject:
                                "Fantastic Food: ${widget.currentRecipe.recipeName}",
                            sharePositionOrigin:
                                box.localToGlobal(Offset.zero) & box.size);
                      },
                    ),
                  )
                ],
                title: Text("Recipe", style: TextStyle(fontSize: 30.0)),
              ),
              body: SingleChildScrollView(
                child: Column(
                  children: [
                    Container(
                      decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(10),
                        boxShadow: [
                          BoxShadow(
                            color: Colors.black.withOpacity(0.5),
                            spreadRadius: 1,
                            blurRadius: 2,
                            offset: Offset(0, 3),
                          ),
                        ],
                      ),
                      child: ClipRRect(
                        borderRadius: BorderRadius.circular(20.0),
                        child: FadeInImage(
                          height: 250.0,
                          width: MediaQuery.of(context).size.width,
                          fit: BoxFit.cover,
                          image: NetworkImage(widget.currentRecipe.recipeImage),
                          placeholder: NetworkImage(defaultImage),
                          imageErrorBuilder: (context, error, stacktrace) {
                            return ClipRRect(
                                borderRadius: BorderRadius.circular(20.0),
                                child: FadeInImage(
                                    height: 250.0,
                                    width: MediaQuery.of(context).size.width,
                                    fit: BoxFit.cover,
                                    image: NetworkImage(defaultImage),
                                    placeholder: NetworkImage(defaultImage)));
                          },
                        ),
                      ),
                    ),
                    Text(
                      widget.currentRecipe.recipeName,
                      style: TextStyle(
                          fontSize: 30, color: Theme.of(context).primaryColor),
                    ),
                    Padding(
                      padding: const EdgeInsets.symmetric(vertical: 8.0),
                      child: Row(children: [
                        Spacer(),
                        Icon(
                          Icons.star,
                          color: Theme.of(context).primaryColor,
                        ),
                        widget.currentRecipe.difficulty.trim() == "MEDIUM" ||
                                widget.currentRecipe.difficulty.trim() == "HARD"
                            ? Icon(
                                Icons.star,
                                color: Theme.of(context).primaryColor,
                              )
                            : Container(),
                        widget.currentRecipe.difficulty.trim() == "HARD"
                            ? Icon(
                                Icons.star,
                                color: Theme.of(context).primaryColor,
                              )
                            : Container(),
                        SizedBox(
                          width: 10.0,
                        ),
                        Text(
                          widget.currentRecipe.difficulty,
                          style: singleRecipeHeading,
                        ),
                        Spacer()
                      ]),
                    ),
                    Text(
                      "Preparation: ${widget.currentRecipe.preparationTime} mins",
                      style: singleRecipeHeading,
                    ),
                    SizedBox(
                      height: 30,
                    ),
                    Text(
                      "Suitable Meal Plans:",
                      style: singleRecipeHeading,
                    ),
                    Container(
                      padding: EdgeInsets.only(top: 10),
                      margin: EdgeInsets.symmetric(horizontal: 10.0),
                      width: MediaQuery.of(context).size.width,
                      decoration: BoxDecoration(
                          color: Theme.of(context).primaryColor,
                          border:
                              Border.all(color: Theme.of(context).primaryColor),
                          borderRadius: BorderRadius.circular(8.0)),
                      child: Text(
                        mealPlanBuffer.toString(),
                        style: TextStyle(fontSize: 18.0),
                        textAlign: TextAlign.center,
                      ),
                    ),
                    SizedBox(
                      height: 20,
                    ),
                    Text(
                      "Contains Allergens:",
                      style: singleRecipeHeading,
                    ),
                    Container(
                      padding: EdgeInsets.only(top: 10),
                      margin: EdgeInsets.symmetric(horizontal: 10.0),
                      width: MediaQuery.of(context).size.width,
                      decoration: BoxDecoration(
                          color: Theme.of(context).primaryColor,
                          border:
                              Border.all(color: Theme.of(context).primaryColor),
                          borderRadius: BorderRadius.circular(8.0)),
                      child: Text(
                        allergensBuffer.toString(),
                        style: TextStyle(fontSize: 18.0),
                        textAlign: TextAlign.center,
                      ),
                    ),
                    SizedBox(
                      height: 20,
                    ),
                    Text(
                      "Instructions:",
                      style: singleRecipeHeading,
                    ),
                    Container(
                      padding: EdgeInsets.only(
                          top: 10, left: 10, right: 10, bottom: 10),
                      margin: EdgeInsets.symmetric(horizontal: 10.0),
                      width: MediaQuery.of(context).size.width,
                      decoration: BoxDecoration(
                          color: Theme.of(context).primaryColor,
                          border:
                              Border.all(color: Theme.of(context).primaryColor),
                          borderRadius: BorderRadius.circular(8.0)),
                      child: Text(
                        widget.currentRecipe.instructions,
                        style: TextStyle(fontSize: 18.0),
                        textAlign: TextAlign.center,
                      ),
                    ),
                    SizedBox(
                      height: 20,
                    )
                  ],
                ),
              ),
            )));
  }
}
