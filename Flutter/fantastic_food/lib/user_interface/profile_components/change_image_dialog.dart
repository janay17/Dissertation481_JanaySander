import 'package:fantastic_food/user_interface/profile_components/edit_recipe.dart';
import 'package:flutter/material.dart';

class ChangeImage {
  showAlertDialog({BuildContext context}) {

    
    final TextEditingController _imageController = TextEditingController();

    Widget continueButton = TextButton(
      child: Text("OK",  style: TextStyle(color: Theme.of(context).primaryColor, fontSize: 18.0),),
      onPressed:  () async {
        imageSource = _imageController.text;
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
      title: Container(
        width: MediaQuery.of(context).size.width/2,
        child: Text('Paste the recipe image address', style: TextStyle(color: Colors.black),)
      ),
      content: TextField(
        controller: _imageController,
        style: TextStyle(color: Colors.black),
        decoration: InputDecoration(hintText: "Image address",
            border: new UnderlineInputBorder(
            borderSide: new BorderSide(
              color: Theme.of(context).primaryColor
            )
          )
        ),
      ),
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