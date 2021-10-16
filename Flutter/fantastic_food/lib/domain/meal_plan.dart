class MealPlan {
  int id;
  String mealplanName;
  String mealplanDescription;

  MealPlan(this.id, this.mealplanName, this.mealplanDescription);

  factory MealPlan.fromJson(dynamic json) {
    return MealPlan(json['id'] as int, json['mealplanName'] as String,
        json['mealplanDescription'] as String);
  }

  Map<String, dynamic> toJson() {
    return {
      "id": this.id,
      "mealplanName": this.mealplanName,
      "mealplanDescription": this.mealplanDescription
    };
  }

  @override
  bool operator ==(Object other) {
    return other is MealPlan && other.id == id;
  }

  int get hashcode => id.hashCode ^ mealplanName.hashCode;
}
