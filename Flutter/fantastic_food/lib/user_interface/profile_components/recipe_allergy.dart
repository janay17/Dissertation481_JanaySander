import 'package:fantastic_food/domain/allergy.dart';
import 'package:fantastic_food/domain/recipe.dart';
import 'package:fantastic_food/services/recipe_service.dart';
import 'package:fantastic_food/user_interface/profile_components/edit_recipe.dart';
import 'package:flutter/material.dart';


class AllergyTile extends StatefulWidget {
  const AllergyTile({Key key, this.isNewRecipe, this.allergy}) : 
      super(key: key);

  @override
  _AllergyTileState createState() => _AllergyTileState();
  final bool isNewRecipe;
  final Allergy allergy; 
}

class _AllergyTileState extends State<AllergyTile> { 

  @override
  Widget build(BuildContext context) {
    bool isNewRecipe = widget.isNewRecipe;
    Allergy allergy = widget.allergy;

    bool checked = isNewRecipe? emptyRecipe.recipeAllergies == null? false: 
          emptyRecipe.recipeAllergies.contains(allergy) 
          : selectedRecipe.recipeAllergies.contains(allergy);

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
                    isNewRecipe? emptyRecipe.recipeAllergies.add(allergy):selectedRecipe.recipeAllergies.add(allergy);
                  } else {
                    isNewRecipe? emptyRecipe.recipeAllergies.remove(allergy):selectedRecipe.recipeAllergies.remove(allergy);
                  }
                  if(!isNewRecipe) {
                    RecipeService().updateRecipeData(selectedRecipe);
                  }
                });
              },
            ),
            Text(allergy.allergyName, style: TextStyle(color: Theme.of(context).primaryColor, fontSize: 25),)
          ],
        ),
        onTap: () {
          bool val = !checked;
          setState(() {
              if(val == true) {
                isNewRecipe? emptyRecipe.recipeAllergies.add(allergy):selectedRecipe.recipeAllergies.add(allergy);
              } else {
                isNewRecipe? emptyRecipe.recipeAllergies.remove(allergy):selectedRecipe.recipeAllergies.remove(allergy);
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