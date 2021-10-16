import 'dart:convert';

import 'package:fantastic_food/domain/allergy.dart';
import 'package:fantastic_food/domain/user_profile.dart';
import 'package:fantastic_food/services/allergy_service.dart';
import 'package:fantastic_food/services/user_profile_service.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class MyAllergies extends StatefulWidget {
  @override
  _MyAllergiesState createState() => _MyAllergiesState();
}

class _MyAllergiesState extends State<MyAllergies> {

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
                    padding: const EdgeInsets.only(left: 20, right: 20, bottom: 10),
                    child: Text("Select what you are allergic to:", 
                    style: TextStyle(color: Theme.of(context).primaryColor, fontSize: 25),
                    textAlign: TextAlign.center,),
                  ),
                  Container(
                    height: 420,
                    child: FutureBuilder(
                      future: AllergyService().getAllAllergies(),
                      builder: (context, snapshot){
                        if(snapshot.connectionState == ConnectionState.done){
                          if(snapshot.hasError){
                            return Text('A connection problem occurred', style: TextStyle(fontSize: 18.0,color: Theme.of(context).primaryColor),);
                          }
                          else if(snapshot.hasData) {
                            List<Allergy> allergies = snapshot.data;
                            if(allergies.length == 0){
                              return Text('No results found', style: TextStyle(fontSize: 18.0,color: Theme.of(context).primaryColor),);
                            } else
                              return ListView.builder(
                                itemCount: allergies.length,
                                physics: NeverScrollableScrollPhysics(),
                                itemBuilder: (context, index) {
                                  return AllergyCheckbox(currentUser: currentUser, allergy: allergies[index],);
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

class AllergyCheckbox extends StatefulWidget {
  @override
  _AllergyCheckboxState createState() => _AllergyCheckboxState();

  UserProfile currentUser;
  Allergy allergy;

  AllergyCheckbox({this.currentUser, this.allergy});
}

class _AllergyCheckboxState extends State<AllergyCheckbox> {

  @override
  Widget build(BuildContext context) {

    bool checked = widget.currentUser.userAllergies.contains(widget.allergy);

    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 50),
      child: GestureDetector(
        child: Row(
          children: [
            Checkbox(
              value: widget.currentUser.userAllergies.contains(widget.allergy), 
              fillColor: MaterialStateProperty.all<Color>(Theme.of(context).primaryColor),
              onChanged: (val) {
                setState(() {
                  if(val == true) {
                    widget.currentUser.userAllergies.add(widget.allergy);
                  } else {
                    widget.currentUser.userAllergies.remove(widget.allergy);
                  }
                  UserProfileService().updateUserData(widget.currentUser);
                });
              },
            ),
            Text(widget.allergy.allergyName, style: TextStyle(color: Theme.of(context).primaryColor, fontSize: 25),)
          ],
        ),
        onTap: () {
          bool val = !checked;
          setState(() {
            if(val == true) {
              widget.currentUser.userAllergies.add(widget.allergy);
            } else {
              widget.currentUser.userAllergies.remove(widget.allergy);
            }
            UserProfileService().updateUserData(widget.currentUser);
          });
        },
      ),
    );
  }
}