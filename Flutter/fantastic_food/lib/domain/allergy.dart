class Allergy {
  int id;
  String allergyName;
  String allergyDescription;

  Allergy(this.id, this.allergyName, this.allergyDescription);

  factory Allergy.fromJson(dynamic json) {
    return Allergy(json['id'] as int, json['allergyName'] as String,
        json['allergyDescription'] as String);
  }

  Map<String, dynamic> toJson() {
    return {
      "id": this.id,
      "allergyName": this.allergyName,
      "allergyDescription": this.allergyDescription
    };
  }

  @override
  bool operator ==(Object other) {
    return other is Allergy && other.id == id;
  }

  int get hashcode => id.hashCode ^ allergyName.hashCode;
}
