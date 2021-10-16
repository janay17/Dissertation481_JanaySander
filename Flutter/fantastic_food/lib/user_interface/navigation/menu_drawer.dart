import 'package:fantastic_food/user_interface/main_components/my_profile.dart';
import 'package:fantastic_food/user_interface/navigation/menu_bloc.dart';
import 'package:fantastic_food/user_interface/styles/textInput.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_phoenix/flutter_phoenix.dart';

class MenuDrawer extends StatefulWidget {
  @override
  _MenuDrawerState createState() => _MenuDrawerState();

  final Orientation orientation;

  const MenuDrawer({Key key, this.orientation}) : super(key: key);
}

class _MenuDrawerState extends State<MenuDrawer> {
  @override
  Widget build(BuildContext context) {
    return Drawer(
        child: SingleChildScrollView(
            scrollDirection: Axis.vertical,
            child: Container(
              color: Theme.of(context).primaryColor,
              padding: EdgeInsets.only(top: 23.0, bottom: 32.0),
              height: (widget.orientation == Orientation.portrait)
                  ? MediaQuery.of(context).size.height
                  : MediaQuery.of(context).size.width,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Container(
                    height: 220.0,
                    width: double.infinity,
                    decoration: BoxDecoration(
                        image: DecorationImage(
                            image: AssetImage('assets/images/logo.png'),
                            fit: BoxFit.cover)),
                  ),
                  Container(
                    padding: EdgeInsets.only(left: 25),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Row(
                          children: [
                            Spacer(),
                            Padding(
                              padding: const EdgeInsets.only(
                                  top: 20, right: 20, bottom: 60),
                              child: GestureDetector(
                                child: Icon(
                                  Icons.arrow_back,
                                  color: Theme.of(context).highlightColor,
                                  size: 30,
                                ),
                                onTap: () {
                                  Navigator.of(context).pop();
                                },
                              ),
                            ),
                          ],
                        ),
                        TextButton.icon(
                          icon: Icon(
                            Icons.home,
                            color: Theme.of(context).highlightColor,
                          ),
                          label: Text(
                            'Home',
                            style: menuStyle,
                          ),
                          onPressed: () {
                            Navigator.of(context).pop();
                            BlocProvider.of<MenuBloc>(context)
                                .add(MenuEvents.HomeEvent);
                          },
                        ),
                        TextButton.icon(
                          icon: Icon(
                            Icons.search,
                            color: Theme.of(context).highlightColor,
                          ),
                          label: Text(
                            'Explore Recipes',
                            style: menuStyle,
                          ),
                          onPressed: () {
                            Navigator.of(context).pop();
                            BlocProvider.of<MenuBloc>(context)
                                .add(MenuEvents.ExploreRecipesEvent);
                          },
                        ),
                        TextButton.icon(
                          icon: Icon(
                            Icons.favorite_outline,
                            color: Theme.of(context).highlightColor,
                          ),
                          label: Text(
                            'Favourite Recipes',
                            style: menuStyle,
                          ),
                          onPressed: () {
                            Navigator.of(context).pop();
                            BlocProvider.of<MenuBloc>(context)
                                .add(MenuEvents.FavouriteRecipesEvent);
                          },
                        ),
                        TextButton.icon(
                          icon: Icon(
                            Icons.person_outline,
                            color: Theme.of(context).highlightColor,
                          ),
                          label: Text(
                            'My Profile',
                            style: menuStyle,
                          ),
                          onPressed: () {
                            Navigator.of(context).pop();
                            // BlocProvider.of<MenuBloc>(context).add(MenuEvents.MyProfileEvent);
                            Navigator.push(
                              context,
                              MaterialPageRoute(
                                  builder: (context) => MyProfile()),
                            );
                          },
                        ),
                        SizedBox(
                          height: 20,
                        ),
                        TextButton(
                            onPressed: () async {
                              Phoenix.rebirth(context);
                            },
                            child: Text(
                              'Sign out',
                              style: menuStyle,
                            ))
                      ],
                    ),
                  )
                ],
              ),
            )));
  }
}
