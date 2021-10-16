import 'package:flutter/material.dart';
import 'package:hexcolor/hexcolor.dart';

var textInputDecoration = InputDecoration(
  fillColor: HexColor("#1E1F33"),
  filled: true,
  labelStyle: TextStyle(fontSize: 20.0, color: Colors.white,),
  enabledBorder: OutlineInputBorder(
    borderSide: BorderSide(color: HexColor("#1E1F33"), width: 1.0),
    borderRadius: const BorderRadius.all(
      const Radius.circular(10.0),
    ),
  ),
  focusedBorder:  OutlineInputBorder(
    borderSide: BorderSide(color: HexColor("#1E1F33"), width: 1.0),
    borderRadius: const BorderRadius.all(
      const Radius.circular(8.0),
    ),
  )
);

var searchInputDecoration = InputDecoration(
  fillColor: HexColor("#40B488"),
  filled: true,
  labelStyle: TextStyle(fontSize: 20.0, color: HexColor("#1E1F33"),),
  enabledBorder: OutlineInputBorder(
    borderSide: BorderSide(color: HexColor("#1E1F33"), width: 2.0),
    borderRadius: const BorderRadius.all(
      const Radius.circular(40.0),
    ),
  ),
  focusedBorder:  OutlineInputBorder(
    borderSide: BorderSide(color: HexColor("#1E1F33"), width: 2.0),
    borderRadius: const BorderRadius.all(
      const Radius.circular(40.0),
    ),
  )
);


var buttonLightStyle = ButtonStyle(
  shape: MaterialStateProperty.all<RoundedRectangleBorder>(
    RoundedRectangleBorder(
      borderRadius: BorderRadius.circular(40.0),
      side: BorderSide(color: HexColor("#1E1F33"), width: 2.0),
    ),
  ),
  backgroundColor: MaterialStateProperty.all<Color>(HexColor("#40B488")),
);


var buttonDarkStyle = ButtonStyle(
  shape: MaterialStateProperty.all<RoundedRectangleBorder>(
    RoundedRectangleBorder(
      borderRadius: BorderRadius.circular(40.0),
      side: BorderSide(color: HexColor("#1E1F33"), width: 2.0),
    ),
  ),
  backgroundColor: MaterialStateProperty.all<Color>(HexColor("#1E1F33")),
);

var lightButtonTextStyle = TextStyle(
  color: HexColor("#1E1F33"),
  fontSize: 30.0,
);

var darkButtonTextStyle = TextStyle(
  color: HexColor("#FFFFFF"),
  fontSize: 30.0,
);

var menuStyle = TextStyle(
  fontSize: 25.0,
  color: HexColor("#FFFFFF")
);

var singleRecipeHeading = TextStyle(
  color: HexColor("#1E1F33"),
  fontSize: 22
);