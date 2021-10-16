import 'package:fantastic_food/domain/allergy.dart';
import 'package:fantastic_food/domain/meal_plan.dart';
import 'package:fantastic_food/domain/recipe.dart';
import 'package:fantastic_food/domain/user_profile.dart';
import 'package:fantastic_food/main.dart';
import 'package:fantastic_food/services/allergy_service.dart';
import 'package:fantastic_food/services/authentication_service.dart';
import 'package:fantastic_food/services/meal_plan_service.dart';
import 'package:fantastic_food/services/recipe_service.dart';
import 'package:fantastic_food/services/user_profile_service.dart';
import 'package:fantastic_food/user_interface/profile_components/change_image_dialog.dart';
import 'package:fantastic_food/user_interface/profile_components/recipe_allergy.dart';
import 'package:fantastic_food/user_interface/profile_components/recipe_mealplan.dart';
import 'package:fantastic_food/user_interface/styles/textInput.dart';
import 'package:flutter/material.dart';

class EditRecipe extends StatefulWidget {
  EditRecipe({Key key, this.currentRecipe, this.currentUser}) : super(key: key);

  @override
  _EditRecipeState createState() => _EditRecipeState();

  Recipe currentRecipe; 
  final UserProfile currentUser;
}

Recipe selectedRecipe;

Recipe emptyRecipe;

String imageSource;

class _EditRecipeState extends State<EditRecipe> {

  bool isNewRecipe = true;
  final TextEditingController _recipeNameController = TextEditingController();
  final TextEditingController _preparationTimeController = TextEditingController();
  final TextEditingController _instructionsController = TextEditingController();
  

  List<String> _dropdownItems = ["EASY", "MEDIUM", "HARD"];

  List<DropdownMenuItem<String>> _dropdownMenuItems;
  String _selectedDifficulty;

  void initState() {
    super.initState();
    selectedRecipe = widget.currentRecipe;
    _dropdownMenuItems = buildDropDownMenuItems(_dropdownItems);
    if(widget.currentRecipe == null) {
      isNewRecipe = true;
      _selectedDifficulty = _dropdownMenuItems[0].value;
      _recipeNameController.text = "";
      _preparationTimeController.text = "";
      _instructionsController.text = "";
      imageSource = defaultImage;
      emptyRecipe = Recipe.clear();
      emptyRecipe.recipeAllergies = [];
      emptyRecipe.recipeMealPlans = [];
    } else {
      isNewRecipe = false;
      _selectedDifficulty = _dropdownMenuItems[_dropdownItems.indexOf(selectedRecipe.difficulty.trim())].value;
      _recipeNameController.text = selectedRecipe.recipeName;
      _preparationTimeController.text = selectedRecipe.preparationTime.toString();
      _instructionsController.text = selectedRecipe.instructions;
      imageSource = selectedRecipe.recipeImage;
    }
    
  }

  List<DropdownMenuItem<String>> buildDropDownMenuItems(List listItems) {
    List<DropdownMenuItem<String>> items = [];
    for (String listItem in listItems) {
      items.add(
        DropdownMenuItem(
          child: Text(listItem),
          value: listItem,
        ),
      );
    }
    return items;
  }

  void updateRecipe(Recipe selectedRecipe, UserProfile currentUser) async {
    await RecipeService().updateRecipeData(selectedRecipe);
    AuthenticationService().getUserByEmail(currentUser.email);
  }

  void saveNewRecipe(Recipe emptyRecipe, UserProfile currentUser) async {
    Recipe newRecipe = await RecipeService().saveNewRecipe(emptyRecipe);
    currentUser.userRecipes.add(newRecipe);
    UserProfileService().updateUserData(currentUser);
  } 

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Container(
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height,
        child: Scaffold(
          backgroundColor: Theme.of(context).accentColor,
          appBar: AppBar(
            title: Text(isNewRecipe? "New Recipe": "Edit Recipe",style: TextStyle(fontSize: 30.0)),
          ),
          body: SingleChildScrollView(
            child: Container(
              margin: EdgeInsets.symmetric(horizontal: 20),
              child: Form(
                child: Column(
                  children: [
                    Row(
                      children: [
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
                                image: NetworkImage(imageSource), 
                                placeholder: NetworkImage(defaultImage),
                                imageErrorBuilder: (context, error, stacktrace) {
                                  return ClipRRect(
                                    borderRadius: BorderRadius.circular(20.0),
                                      child: FadeInImage(
                                          height: 150.0,
                                          width: 280.0,
                                          fit: BoxFit.cover,
                                          image: NetworkImage(defaultImage), 
                                          placeholder: NetworkImage(defaultImage),
                                      )
                                  );
                                },
                              )
                            ),
                          ),
                        Column(
                          children: [
                            Padding(
                              padding: const EdgeInsets.symmetric(vertical: 10),
                              child: GestureDetector(
                                child: Icon(Icons.undo, size: 30, color: Theme.of(context).primaryColor,),
                                onTap: () {
                                  setState(() {
                                    imageSource = isNewRecipe? "": selectedRecipe.recipeImage;
                                    _instructionsController.text = isNewRecipe? "": selectedRecipe.instructions;
                                    _recipeNameController.text = isNewRecipe? "": selectedRecipe.recipeName;
                                    _preparationTimeController.text = isNewRecipe? "": selectedRecipe.preparationTime.toString();
                                  });
                                },
                              ),
                            ),
                            Padding(
                              padding: const EdgeInsets.symmetric(vertical: 10),
                              child: GestureDetector(
                                child: Icon(Icons.image, size: 30, color: Theme.of(context).primaryColor,),
                                onTap: () {
                                  setState(() {
                                    ChangeImage().showAlertDialog(context: context);
                                  });
                                },
                              ),
                            ),
                            isNewRecipe? Padding(
                              padding: const EdgeInsets.symmetric(vertical: 10),
                              child: GestureDetector(
                                child: Icon(Icons.delete, size: 30, color: Theme.of(context).primaryColor,),
                                onTap: () {
                                  imageSource = isNewRecipe? "": selectedRecipe.recipeImage;
                                  _instructionsController.text = isNewRecipe? "": selectedRecipe.instructions;
                                  _recipeNameController.text = isNewRecipe? "": selectedRecipe.recipeName;
                                  _preparationTimeController.text = isNewRecipe? "": selectedRecipe.preparationTime.toString();
                                  Navigator.of(context).pop();
                                },
                              ),
                            ): Container(),
                          ],
                        )
                      ],
                    ),
                    TextFormField(
                      controller: _recipeNameController,
                      cursorColor: Theme.of(context).highlightColor,
                      decoration: textInputDecoration.copyWith(
                        labelText: 'Recipe Name',
                        labelStyle: TextStyle(fontSize: 20.0, color: Colors.white,),
                      ),
                    ),

                    Padding(
                      padding: const EdgeInsets.symmetric(vertical: 10),
                      child: Row(
                        children: [
                          Text("Difficulty", style: TextStyle(fontSize: 23.0, color: Theme.of(context).primaryColor),),
                          Spacer(),
                          Container(
                            width: 200,
                            height: 60.0,
                            padding: const EdgeInsets.only(left: 10.0, right: 10.0),
                            decoration: BoxDecoration(
                                borderRadius: BorderRadius.circular(10.0),
                                color: Theme.of(context).primaryColor,
                                border: Border.all()),
                            child: DropdownButtonHideUnderline(
                              child: DropdownButton<String>(
                                value: _selectedDifficulty,
                                items: _dropdownMenuItems,
                                onChanged: (value) {
                                  setState(() {
                                    _selectedDifficulty = value;
                                  });
                                }
                              ),
                            ),
                          ),
                        ],
                      ),
                    ),

                    
                    Row(
                      children: [
                        Text("Preparation", style: TextStyle(fontSize: 23.0, color: Theme.of(context).primaryColor),),
                        Spacer(),
                        Container(
                          width: 130,
                          
                            child: Padding(
                              padding: const EdgeInsets.only(right: 10),
                              child: TextFormField(
                                controller: _preparationTimeController,
                                keyboardType: TextInputType.number,
                                cursorColor: Theme.of(context).highlightColor,
                                decoration: textInputDecoration.copyWith(
                                  labelText: 'Time',
                                  labelStyle: TextStyle(fontSize: 20.0, color: Colors.white,),
                                ),
                              ),
                            
                            
                          ),
                        ),
                        Container(
                          width: 70,
                          child: Padding(
                            padding: const EdgeInsets.only(right: 10),
                            child: Text("mins", style: TextStyle(color: Theme.of(context).primaryColor, fontSize: 25),),
                          ),
                        )
                      ],
                    ),
                    Padding(
                      padding: const EdgeInsets.only(top: 20, bottom: 10),
                      child: Text("Meal Plans", style: TextStyle(color: Theme.of(context).primaryColor, fontSize: 25),),
                    ),
                    
                    getMealPlans(),

                    Padding(
                      padding: const EdgeInsets.only(top: 20, bottom: 10),
                      child: Text("Allergens", style: TextStyle(color: Theme.of(context).primaryColor, fontSize: 25),),
                    ),

                    getAllergens(),

                  Padding(
                    padding: const EdgeInsets.only(top: 10),
                    child: TextFormField(
                      controller: _instructionsController,
                      style: TextStyle(fontSize: 20),
                      textAlign: TextAlign.center,
                      cursorColor: Theme.of(context).highlightColor,
                      decoration: textInputDecoration.copyWith(
                        labelText: 'Instructions',
                        labelStyle: TextStyle(fontSize: 20.0, color: Colors.white,),
                      ),
                      minLines: 6,
                      maxLines: 100,
                    ),
                  ),
                  SizedBox(height: 25,)
                  ],
                ),
              ),
            ),
          ),
        )
      )
    );
  }


  Widget getAllergens() {
    return Container(
      width: MediaQuery.of(context).size.width,
      decoration: BoxDecoration(
        border: Border.all(color: Theme.of(context).primaryColor, width: 2),
        borderRadius: BorderRadius.circular(40)
      ),
      margin: EdgeInsets.symmetric(horizontal: 10),
      padding: EdgeInsets.only(top: 5),
      child: Container(
      height: 400,
      child: FutureBuilder(
        future: AllergyService().getAllAllergies(),
        builder: (context, snapshot){
          if(snapshot.connectionState == ConnectionState.done){
            if(snapshot.hasError){
              return Text('A connection problem occurred', style: TextStyle(fontSize: 18.0,color: Theme.of(context).primaryColor),);
            }
            else if(snapshot.hasData) {
              List<Allergy> allergies = snapshot.data;
              if(allergies.length == 0){
                return Text('No results found', style: TextStyle(fontSize: 18.0,color: Theme.of(context).primaryColor),);
              } else
                return ListView.builder(
                  itemCount: allergies.length,
                  physics: NeverScrollableScrollPhysics(),
                  itemBuilder: (context, index) {
                    
                    return AllergyTile(allergy: allergies[index], isNewRecipe: isNewRecipe, );
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
      )
    );
  }
  

  Widget getMealPlans() {
    return Container(
      width: MediaQuery.of(context).size.width,
      decoration: BoxDecoration(
        border: Border.all(color: Theme.of(context).primaryColor, width: 2),
        borderRadius: BorderRadius.circular(40)
      ),
      margin: EdgeInsets.symmetric(horizontal: 10),
      padding: EdgeInsets.only(top: 5),
      child: Container(
      height: 210,
      child: FutureBuilder(
        future: MealPlanService().getAllMealPlans(),
        builder: (context, snapshot){
          if(snapshot.connectionState == ConnectionState.done){
            if(snapshot.hasError){
              return Text('A connection problem occurred', style: TextStyle(fontSize: 18.0,color: Theme.of(context).primaryColor),);
            }
            else if(snapshot.hasData) {
              List<MealPlan> mealplans = snapshot.data;
              if(mealplans.length == 0){
                return Text('No results found', style: TextStyle(fontSize: 18.0,color: Theme.of(context).primaryColor),);
              } else
                return ListView.builder(
                  itemCount: mealplans.length,
                  physics: NeverScrollableScrollPhysics(),
                  itemBuilder: (context, index) {
                   return MealPlanTile(isNewRecipe: isNewRecipe, mealPlan: mealplans[index],);
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
      )
    );
  }


  @override
  void deactivate() {
    if(_recipeNameController.text.isEmpty) {
     
    } else {
      if(_instructionsController.text.isEmpty) {
        
      } else {
      
        int _time = 0;
        if(_preparationTimeController.text.isNotEmpty) {
          _time = int.parse(_preparationTimeController.text);
        }
        if(!isNewRecipe) {
          selectedRecipe.recipeImage = imageSource;
          selectedRecipe.difficulty = _selectedDifficulty;
          selectedRecipe.recipeName = _recipeNameController.text;
          selectedRecipe.instructions = _instructionsController.text;
          selectedRecipe.preparationTime = _time;
          updateRecipe(selectedRecipe, widget.currentUser);
        } else {
          emptyRecipe.recipeImage = imageSource;
          emptyRecipe.difficulty = _selectedDifficulty;
          emptyRecipe.recipeName = _recipeNameController.text;
          emptyRecipe.instructions = _instructionsController.text;
          emptyRecipe.preparationTime = _time;
          saveNewRecipe(emptyRecipe, widget.currentUser);
        }
      }
    } 
    super.deactivate();
      
  }   
    
}
