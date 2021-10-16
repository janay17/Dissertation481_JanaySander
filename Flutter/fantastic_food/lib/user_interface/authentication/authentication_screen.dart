import 'package:fantastic_food/user_interface/authentication/login.dart';
import 'package:fantastic_food/user_interface/authentication/register.dart';
import 'package:flutter/material.dart';

class Authentication extends StatefulWidget {
  @override
  _AuthenticationState createState() => _AuthenticationState();
  bool isLogin = true;
}

class _AuthenticationState extends State<Authentication> {
  void toggleSignIn() {
    setState(() {
      widget.isLogin = !widget.isLogin;
    });
  }

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Container(
        height: MediaQuery.of(context).size.height,
        width: MediaQuery.of(context).size.width,
        child: Scaffold(
          backgroundColor: Theme.of(context).accentColor,
          appBar: AppBar(
            toolbarHeight: 255,
            title: Column(
              children: [
                Container(
                  child: Image(
                    image: AssetImage("assets/images/logo_stretch.png"),
                    height: 190.0,
                    width: MediaQuery.of(context).size.width,
                    fit: BoxFit.fitWidth,
                  ),
                ),
                Text(
                  "Fantastic Food",
                  style: TextStyle(fontSize: 40.0),
                )
              ],
            ),
          ),
          body: widget.isLogin
              ? Login(
                  changeLoginStatus: toggleSignIn,
                )
              : Register(changeRegisterStatus: toggleSignIn),
        ),
      ),
    );
  }
}
