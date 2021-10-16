import 'dart:io';

import 'package:confirm_dialog/confirm_dialog.dart';
import 'package:fantastic_food/services/authentication_service.dart';
import 'package:fantastic_food/user_interface/authentication/change_password_dialog.dart';
import 'package:fantastic_food/user_interface/main_components/main_view_container.dart';
import 'package:fantastic_food/user_interface/styles/textInput.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class Login extends StatefulWidget {
  final Function changeLoginStatus;

  const Login({Key key, this.changeLoginStatus}) : super(key: key);

  @override
  _LoginState createState() => _LoginState();
}

class _LoginState extends State<Login> {
  bool obscureText = true;
  final storage = FlutterSecureStorage();
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();

  void togglePasswordView() {
    setState(() {
      obscureText = !obscureText;
    });
  }

  @override
  void initState() {
    super.initState();
    _emailController.text = 'janaysander1712@gmail.com';
    _passwordController.text = 'test1234';
  }

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Center(
        child: Column(
          children: [
            Padding(
              padding: const EdgeInsets.symmetric(vertical: 20.0),
              child: Text(
                'Login',
                style: TextStyle(
                  color: Theme.of(context).primaryColor,
                  fontSize: 40.0,
                ),
              ),
            ),
            Form(
                child: Padding(
              padding: const EdgeInsets.symmetric(horizontal: 20.0),
              child: Column(
                children: [
                  TextFormField(
                    controller: _emailController,
                    cursorColor: Theme.of(context).highlightColor,
                    decoration: textInputDecoration.copyWith(
                      prefixIcon: Icon(
                        Icons.email,
                        color: Theme.of(context).highlightColor,
                      ),
                      labelText: 'Email Address',
                      labelStyle: TextStyle(
                        fontSize: 20.0,
                        color: Colors.white,
                      ),
                    ),
                  ),
                  SizedBox(
                    height: 10,
                  ),
                  TextFormField(
                    controller: _passwordController,
                    obscureText: obscureText,
                    decoration: textInputDecoration.copyWith(
                        prefixIcon: Icon(
                          Icons.vpn_key,
                          color: Theme.of(context).highlightColor,
                        ),
                        labelText: 'Password',
                        labelStyle: TextStyle(
                          fontSize: 20.0,
                          color: Colors.white,
                        ),
                        suffixIcon: GestureDetector(
                          child: obscureText
                              ? Icon(
                                  Icons.visibility,
                                  color: Theme.of(context).highlightColor,
                                )
                              : Icon(
                                  Icons.visibility_off,
                                  color: Theme.of(context).highlightColor,
                                ),
                          onTap: () {
                            togglePasswordView();
                          },
                        )),
                  ),
                  Padding(
                    padding: const EdgeInsets.symmetric(
                      vertical: 20.0,
                    ),
                    child: ElevatedButton(
                      style: buttonLightStyle,
                      onPressed: () async {
                        var email = _emailController.text;
                        var password = _passwordController.text;
                        var token;
                        try {
                          token = await AuthenticationService()
                              .logIn(email, password);
                          if (token != null) {
                            AuthenticationService().getUserByEmail(email);
                            storage.write(key: "token", value: token);
                            Navigator.push(
                              context,
                              MaterialPageRoute(
                                  builder: (context) => MainViewContainer()),
                            );
                          } else {
                            final snackBar = SnackBar(
                              content:
                                  Text('Invalid email address or password'),
                              action: SnackBarAction(
                                label: 'OK',
                                onPressed: () {},
                              ),
                            );
                            ScaffoldMessenger.of(context)
                                .showSnackBar(snackBar);
                          }
                        } catch (SocketException) {
                          final snackBar = SnackBar(
                            content: Text('A connection error ocurred.'),
                            action: SnackBarAction(
                              label: 'OK',
                              onPressed: () {},
                            ),
                          );
                          ScaffoldMessenger.of(context).showSnackBar(snackBar);
                        }
                      },
                      child: Container(
                        width: MediaQuery.of(context).size.width,
                        child: Padding(
                          padding: const EdgeInsets.only(top: 8, bottom: 12),
                          child: Text(
                            "LOGIN",
                            style: lightButtonTextStyle,
                            textAlign: TextAlign.center,
                          ),
                        ),
                      ),
                    ),
                  ),
                  GestureDetector(
                      child: Text("Forgot your password?",
                          style: TextStyle(
                            fontSize: 20.0,
                            color: Theme.of(context).primaryColor,
                          )),
                      onTap: () {
                        ChangePassword().showAlertDialog(
                            context: context,
                            title: 'Forgot Password',
                            message:
                                'An email will be sent to change your password.');
                      }),
                  Padding(
                    padding: const EdgeInsets.symmetric(
                      vertical: 20.0,
                    ),
                    child: ElevatedButton(
                      style: buttonLightStyle,
                      onPressed: () {
                        widget.changeLoginStatus();
                      },
                      child: Container(
                        width: MediaQuery.of(context).size.width,
                        child: Padding(
                          padding: const EdgeInsets.only(top: 8, bottom: 12),
                          child: Text(
                            "SIGN UP",
                            style: lightButtonTextStyle,
                            textAlign: TextAlign.center,
                          ),
                        ),
                      ),
                    ),
                  ),
                ],
              ),
            ))
          ],
        ),
      ),
    );
  }
}
