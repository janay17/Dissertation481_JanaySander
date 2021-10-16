import 'dart:convert';

import 'package:fantastic_food/domain/meal_plan.dart';
import 'package:fantastic_food/domain/user_profile.dart';
import 'package:fantastic_food/services/meal_plan_service.dart';
import 'package:fantastic_food/services/user_profile_service.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class MyMealPlans extends StatefulWidget {
  @override
  _MyMealPlansState createState() => _MyMealPlansState();
}

class _MyMealPlansState extends State<MyMealPlans> {

  var storage = FlutterSecureStorage();
  UserProfile currentUser;

  void getCurrentUser() async {
    await storage.read(key: "current_user").then((value) {
      currentUser = UserProfile.fromJson(jsonDecode(value));
    });
  }

  @override
  void initState() {
    getCurrentUser();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Column(
        children: [
          Row(
            children: [
              Spacer(),
                Icon(Icons.account_circle, color: Theme.of(context).primaryColor, size: 100.0,),
              Spacer(),
            ],
          ),
          Container(
              width: MediaQuery.of(context).size.width,
              decoration: BoxDecoration(
                border: Border.all(color: Theme.of(context).primaryColor, width: 2),
                borderRadius: BorderRadius.circular(40)
              ),
              margin: EdgeInsets.symmetric(horizontal: 10),
              padding: EdgeInsets.only(top: 5),
              child: Column(
                children: [
                  Padding(
                    padding: const EdgeInsets.only(left: 10, right: 10, bottom: 10),
                    child: Text("Select your meal plan preferences:", 
                    style: TextStyle(color: Theme.of(context).primaryColor, fontSize: 25),
                    textAlign: TextAlign.center,),
                  ),
                  Container(
                    height: 230,
                    child: FutureBuilder(
                      future: MealPlanService().getAllMealPlans(),
                      builder: (context, snapshot){
                        if(snapshot.connectionState == ConnectionState.done){
                          if(snapshot.hasError){
                            return Text('A connection problem occurred', style: TextStyle(fontSize: 18.0,color: Theme.of(context).primaryColor),);
                          }
                          else if(snapshot.hasData) {
                            List<MealPlan> mealplans = snapshot.data;
                            if(mealplans.length == 0){
                              return Text('No results found', style: TextStyle(fontSize: 18.0,color: Theme.of(context).primaryColor),);
                            } else
                              return ListView.builder(
                                itemCount: mealplans.length,
                                physics: NeverScrollableScrollPhysics(),
                                itemBuilder: (context, index) {
                                  return MealPlanCheckbox(currentUser: currentUser, mealPlan: mealplans[index],);
                                }
                              );
                          } else {
                            return Text("");
                          }
                        } else if(snapshot.connectionState == ConnectionState.waiting){
                          return Text('Loading...', style: TextStyle(fontSize: 18.0,color: Theme.of(context).primaryColor),);
                        } else {
                          return Text('');
                        }
                      },
                    ),
                  )
                ]
              )
            )
        ]
      )
    );
  }
}

class MealPlanCheckbox extends StatefulWidget {
  @override
  _MealPlanCheckboxState createState() => _MealPlanCheckboxState();

  UserProfile currentUser;
  MealPlan mealPlan;

  MealPlanCheckbox({this.currentUser, this.mealPlan});
}

class _MealPlanCheckboxState extends State<MealPlanCheckbox> {
  @override
  Widget build(BuildContext context) {
    bool checked = widget.currentUser.userMealPlans.contains(widget.mealPlan);
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 50),
      child: GestureDetector(
        child: Row(
          children: [
            Checkbox(
              value: checked, 
              fillColor: MaterialStateProperty.all<Color>(Theme.of(context).primaryColor),
              onChanged: (val) {
                setState(() {
                  if(val == true) {
                    widget.currentUser.userMealPlans.add(widget.mealPlan);
                  } else {
                    widget.currentUser.userMealPlans.remove(widget.mealPlan);
                  }
                  UserProfileService().updateUserData(widget.currentUser);
                });
              },
            ),
            Text(widget.mealPlan.mealplanName, style: TextStyle(color: Theme.of(context).primaryColor, fontSize: 25),)
          ],
        ),
        onTap: () {
          bool val = !checked;
          setState(() {
            if(val == true) {
              widget.currentUser.userMealPlans.add(widget.mealPlan);
            } else {
              widget.currentUser.userMealPlans.remove(widget.mealPlan);
            }
            UserProfileService().updateUserData(widget.currentUser);
          });
        },
      ),
    );
  }
}