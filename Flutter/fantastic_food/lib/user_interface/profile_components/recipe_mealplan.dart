import 'package:fantastic_food/domain/meal_plan.dart';
import 'package:fantastic_food/services/recipe_service.dart';
import 'package:fantastic_food/user_interface/profile_components/edit_recipe.dart';
import 'package:flutter/material.dart';

class MealPlanTile extends StatefulWidget {
  const MealPlanTile({Key key, this.isNewRecipe, this.mealPlan}) :
     super(key: key);

  @override
  _MealPlanTileState createState() => _MealPlanTileState();

  final bool isNewRecipe;
  final MealPlan mealPlan; 
}

class _MealPlanTileState extends State<MealPlanTile> {
  @override
  Widget build(BuildContext context) {

    bool isNewRecipe = widget.isNewRecipe;
    MealPlan mealPlan = widget.mealPlan;

    bool checked = isNewRecipe? emptyRecipe.recipeMealPlans == null? false: 
                  emptyRecipe.recipeMealPlans.contains(mealPlan) 
          : selectedRecipe.recipeMealPlans.contains(mealPlan);

    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 50),
      child: GestureDetector(
        child: Row(
          children: [
            Checkbox(
              value: checked, 
              fillColor: MaterialStateProperty.all<Color>(Theme.of(context).primaryColor),
              onChanged: (val) {
                setState(() {
                  if(val == true) {
                    isNewRecipe? emptyRecipe.recipeMealPlans.add(mealPlan):selectedRecipe.recipeMealPlans.add(mealPlan);
                  } else {
                    isNewRecipe? emptyRecipe.recipeMealPlans.remove(mealPlan):selectedRecipe.recipeMealPlans.remove(mealPlan);
                  }
                  if(!isNewRecipe) {
                    RecipeService().updateRecipeData(selectedRecipe);
                  }
                  
                });
              },
            ),
            Text(mealPlan.mealplanName, style: TextStyle(color: Theme.of(context).primaryColor, fontSize: 25),)
          ],
        ),
        onTap: () {
          bool val = !checked;
          setState(() {
            if(val == true) {
              isNewRecipe? emptyRecipe.recipeMealPlans.add(mealPlan):selectedRecipe.recipeMealPlans.add(mealPlan);
            } else {
              isNewRecipe? emptyRecipe.recipeMealPlans.remove(mealPlan):selectedRecipe.recipeMealPlans.remove(mealPlan);
            }
            if(!isNewRecipe) {
              RecipeService().updateRecipeData(selectedRecipe);
            }
          });
        },
      ),
    );
  }
}