import 'package:fantastic_food/services/recipe_service.dart';
import 'package:flutter/material.dart';

class DeleteRecipe {

  showAlertDialog({BuildContext context, String title, String message, int recipeID, String email}) {

    Widget continueButton = TextButton(
      child: Text("OK",  style: TextStyle(color: Theme.of(context).primaryColor, fontSize: 18.0),),
      onPressed:  () async {
        await RecipeService().deleteRecipe(recipeID, email);
        Navigator.of(context).pop();
      },
    );

    Widget cancelButton = TextButton(
      child: Text("CANCEL",  style: TextStyle(color: Theme.of(context).primaryColor, fontSize: 18.0),),
      onPressed:  () {
        Navigator.of(context).pop();
      },
    );

    AlertDialog alert = AlertDialog(
      title: Row(
        children: [
          Icon(Icons.delete, color: Colors.grey[700], size: 30,),
          SizedBox(width: 10,),
          Text(title, style: TextStyle(color: Colors.black),)
        ],
      ),
      content: Text(message, style: TextStyle(color: Colors.black),),
      actions: [
        cancelButton,
        continueButton,
      ],
      backgroundColor: Colors.white,
    );

    showDialog(
      context: context,
      builder: (BuildContext context) {
        return alert;
      },
    );
  }
}