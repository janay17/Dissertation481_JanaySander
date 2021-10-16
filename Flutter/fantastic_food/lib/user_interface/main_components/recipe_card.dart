import 'package:fantastic_food/domain/recipe.dart';
import 'package:fantastic_food/domain/user_profile.dart';
import 'package:fantastic_food/main.dart';
import 'package:fantastic_food/services/user_profile_service.dart';
import 'package:fantastic_food/user_interface/main_components/single_recipe.dart';
import 'package:flutter/material.dart';

class RecipeCard extends StatefulWidget {
  const RecipeCard({Key key, this.recipe, this.currentUser}) : super(key: key);

  @override
  _RecipeCardState createState() => _RecipeCardState();

  final Recipe recipe;
  final UserProfile currentUser;
}

class _RecipeCardState extends State<RecipeCard> {
  @override
  Widget build(BuildContext context) {
    bool favourite = false;
    List<int> favouriteIds = [];
    for (Recipe recipe in widget.currentUser.userFavourites) {
      favouriteIds.add(recipe.id);
    }
    if (favouriteIds.contains(widget.recipe.id)) {
      favourite = true;
    }

    return GestureDetector(
      child: Container(
        padding: EdgeInsets.symmetric(horizontal: 10),
        width: MediaQuery.of(context).size.width / 2,
        child: Column(
          children: [
            ClipRRect(
              borderRadius: BorderRadius.circular(10.0),
              child: FadeInImage(
                height: 150.0,
                width: MediaQuery.of(context).size.width,
                fit: BoxFit.cover,
                image: NetworkImage(widget.recipe.recipeImage),
                placeholder: NetworkImage(defaultImage),
                imageErrorBuilder: (context, error, stacktrace) {
                  return ClipRRect(
                    borderRadius: BorderRadius.circular(10.0),
                    child: FadeInImage(
                      height: 150.0,
                      width: MediaQuery.of(context).size.width,
                      fit: BoxFit.cover,
                      image: NetworkImage(defaultImage),
                      placeholder: NetworkImage(defaultImage),
                    ),
                  );
                },
              ),
            ),
            Row(
              children: [
                Padding(
                  padding:
                      const EdgeInsets.symmetric(vertical: 20, horizontal: 10),
                  child: GestureDetector(
                    child: Icon(
                      favourite ? Icons.favorite : Icons.favorite_outline,
                      color: Theme.of(context).primaryColor,
                      size: 25,
                    ),
                    onTap: () {
                      setState(() {
                        UserProfileService()
                            .toggleFavourite(widget.currentUser, widget.recipe);
                      });
                    },
                  ),
                ),
                Container(
                  width: MediaQuery.of(context).size.width / 3,
                  child: Text(
                    widget.recipe.recipeName,
                    style: TextStyle(
                        color: Theme.of(context).primaryColor, fontSize: 20),
                  ),
                )
              ],
            )
          ],
        ),
      ),
      onTap: () {
        Navigator.push(
          context,
          MaterialPageRoute(
              builder: (context) => SingleRecipe(
                    currentRecipe: widget.recipe,
                    currentUser: widget.currentUser,
                  )),
        );
      },
    );
  }
}
