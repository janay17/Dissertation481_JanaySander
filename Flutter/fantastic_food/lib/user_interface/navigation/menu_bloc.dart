import 'package:fantastic_food/user_interface/main_components/explore_recipes.dart';
import 'package:fantastic_food/user_interface/main_components/favourite_recipes.dart';
import 'package:fantastic_food/user_interface/main_components/home.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

enum MenuEvents {
  HomeEvent,
  ExploreRecipesEvent,
  FavouriteRecipesEvent,
  MyProfileEvent
}

abstract class MenuStates {}

class MenuBloc extends Bloc<MenuEvents, MenuStates> {
  MenuBloc(MenuStates initialState) : super(initialState);

  @override
  Stream<MenuStates> mapEventToState(MenuEvents event) async* {
    switch (event) {
      case MenuEvents.HomeEvent:
        yield Home();
        break;
      case MenuEvents.ExploreRecipesEvent:
        yield ExploreRecipes();
        break;
      case MenuEvents.FavouriteRecipesEvent:
        yield FavouriteRecipes();
        break;
      case MenuEvents.MyProfileEvent:
        yield null;
        break;
    }
  }
}
