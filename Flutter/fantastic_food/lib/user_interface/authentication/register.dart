import 'package:fantastic_food/services/authentication_service.dart';
import 'package:fantastic_food/user_interface/styles/textInput.dart';
import 'package:flutter/material.dart';

class Register extends StatefulWidget {
  final Function changeRegisterStatus;

  const Register({Key key, this.changeRegisterStatus}) : super(key: key);

  @override
  _RegisterState createState() => _RegisterState();
}

class _RegisterState extends State<Register> {
  bool obscureText = true;
  final _formkey = GlobalKey<FormState>();
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  final TextEditingController _confirmController = TextEditingController();

  void togglePasswordView() {
    setState(() {
      obscureText = !obscureText;
    });
  }

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Center(
        child: Column(
          children: [
            Padding(
              padding: const EdgeInsets.symmetric(vertical: 20.0),
              child: Row(
                children: [
                  Padding(
                    padding: const EdgeInsets.only(left: 20),
                    child: GestureDetector(
                      child: Icon(
                        Icons.arrow_back,
                        color: Theme.of(context).primaryColor,
                        size: 30,
                      ),
                      onTap: () {
                        widget.changeRegisterStatus();
                      },
                    ),
                  ),
                  Spacer(),
                  Padding(
                    padding: const EdgeInsets.only(right: 30),
                    child: Text(
                      'Sign up',
                      style: TextStyle(
                        color: Theme.of(context).primaryColor,
                        fontSize: 40.0,
                      ),
                    ),
                  ),
                  Spacer()
                ],
              ),
            ),
            Form(
                key: _formkey,
                child: Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 20.0),
                  child: Column(
                    children: [
                      TextFormField(
                          controller: _emailController,
                          cursorColor: Theme.of(context).highlightColor,
                          validator: (val) =>
                              !RegExp(r"^[a-zA-Z0-9.a-zA-Z0-9.!#$%&'*+-/=?^_`{|}~]+@[a-zA-Z0-9]+\.[a-zA-Z]+")
                                      .hasMatch(val)
                                  ? 'Invalid email address provided.'
                                  : null,
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
                              errorStyle: TextStyle(
                                  color: Theme.of(context).primaryColor,
                                  fontSize: 15.0))),
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
                          errorStyle: TextStyle(
                              color: Theme.of(context).primaryColor,
                              fontSize: 15.0),
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
                          ),
                        ),
                        validator: (val) => val.length < 8
                            ? 'Password must be at least 8 characters in length.'
                            : null,
                      ),
                      SizedBox(
                        height: 10,
                      ),
                      TextFormField(
                        controller: _confirmController,
                        obscureText: true,
                        decoration: textInputDecoration.copyWith(
                            prefixIcon: Icon(
                              Icons.vpn_key,
                              color: Theme.of(context).highlightColor,
                            ),
                            labelText: 'Confirm Password',
                            labelStyle: TextStyle(
                              fontSize: 20.0,
                              color: Colors.white,
                            ),
                            errorStyle: TextStyle(
                                color: Theme.of(context).primaryColor,
                                fontSize: 15.0)),
                        validator: (val) => val != _passwordController.text
                            ? 'Passwords do not match.'
                            : null,
                      ),
                      SizedBox(
                        height: 30.0,
                      ),
                      Padding(
                        padding: const EdgeInsets.symmetric(
                          vertical: 20.0,
                        ),
                        child: ElevatedButton(
                          style: buttonLightStyle,
                          onPressed: () async {
                            if (_emailController.text.isNotEmpty &&
                                _passwordController.text.isNotEmpty &&
                                _confirmController.text.isNotEmpty) {
                              if (_formkey.currentState.validate()) {
                                var newUser = await AuthenticationService()
                                    .signUp(_emailController.text,
                                        _passwordController.text);
                                if (newUser != null) {
                                  final snackBar = SnackBar(
                                    content: Text(
                                        'You have successfully signed up! Please login'),
                                    action: SnackBarAction(
                                      label: 'OK',
                                      onPressed: () {},
                                    ),
                                  );
                                  ScaffoldMessenger.of(context)
                                      .showSnackBar(snackBar);
                                  setState(() {
                                    widget.changeRegisterStatus();
                                  });
                                } else {
                                  final snackBar = SnackBar(
                                    content: Text(
                                        'The user sign in failed. Please try again later.'),
                                    action: SnackBarAction(
                                      label: 'OK',
                                      onPressed: () {},
                                    ),
                                  );
                                  ScaffoldMessenger.of(context)
                                      .showSnackBar(snackBar);
                                }
                              }
                            } else {
                              final snackBar = SnackBar(
                                content: Text('Please fill in all fields.'),
                                action: SnackBarAction(
                                  label: 'OK',
                                  onPressed: () {},
                                ),
                              );
                              ScaffoldMessenger.of(context)
                                  .showSnackBar(snackBar);
                            }
                          },
                          child: Container(
                            width: MediaQuery.of(context).size.width,
                            child: Padding(
                              padding:
                                  const EdgeInsets.only(top: 8, bottom: 12),
                              child: Text(
                                "SIGN UP",
                                style: lightButtonTextStyle,
                                textAlign: TextAlign.center,
                              ),
                            ),
                          ),
                        ),
                      ),
                      SizedBox(
                        height: 70.0,
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
