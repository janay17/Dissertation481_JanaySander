import 'package:flutter/material.dart';

class ChangePassword {
  showAlertDialog({BuildContext context, String title, String message}) {
    Widget continueButton = TextButton(
      child: Text(
        "OK",
        style: TextStyle(color: Theme.of(context).primaryColor, fontSize: 18.0),
      ),
      onPressed: () {
        Navigator.of(context).pop();
      },
    );

    AlertDialog alert = AlertDialog(
      title: Row(
        children: [
          Icon(
            Icons.vpn_key,
            color: Colors.grey[700],
            size: 30,
          ),
          SizedBox(
            width: 10,
          ),
          Text(
            title,
            style: TextStyle(color: Colors.black),
          )
        ],
      ),
      content: Text(
        message,
        style: TextStyle(color: Colors.black),
      ),
      actions: [
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
