import 'package:fantastic_food/user_interface/authentication/authentication_screen.dart';
import 'package:fantastic_food/user_interface/main_components/home.dart';
import 'package:fantastic_food/user_interface/navigation/menu_bloc.dart';
import 'package:fantastic_food/user_interface/navigation/menu_drawer.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class MainViewContainer extends StatefulWidget {
  @override
  _MainViewContainerState createState() => _MainViewContainerState();
}

class _MainViewContainerState extends State<MainViewContainer> {
  Widget mainView() {
    return OrientationBuilder(
      builder: (context, orientation) {
        return SingleChildScrollView(
            child: Container(
                width: MediaQuery.of(context).size.width,
                height: MediaQuery.of(context).size.height,
                child: Scaffold(
                    backgroundColor: Theme.of(context).accentColor,
                    body: Column(
                      children: [
                        BlocBuilder<MenuBloc, MenuStates>(
                          builder: (context, MenuStates state) {
                            return AppBar(
                              title: Text(
                                "Fantastic Food",
                                style: TextStyle(fontSize: 30.0),
                              ),
                            );
                          },
                        ),
                        Expanded(child: BlocBuilder<MenuBloc, MenuStates>(
                          builder: (context, MenuStates state) {
                            return state as Widget;
                          },
                        )),
                      ],
                    ),
                    drawer: MenuDrawer(
                      orientation: orientation,
                    ))));
      },
    );
  }

  Future<String> getLoggedInStatus() async {
    var storage = FlutterSecureStorage();
    return await storage.read(key: "loggedIn");
  }

  @override
  Widget build(BuildContext context) {
    return BlocProvider<MenuBloc>(
        create: (context) => MenuBloc(Home()), child: mainView());
  }
}
