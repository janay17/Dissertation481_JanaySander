package com.sander.fantasticfood.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

public class Allergy implements Parcelable {
    private int id;
    private String allergyName;
    private String allergyDescription;

    public Allergy(int id, String allergyName, String allergyDescription) {
        this.id = id;
        this.allergyName = allergyName;
        this.allergyDescription = allergyDescription;
    }

    protected Allergy(Parcel in) {
        id = in.readInt();
        allergyName = in.readString();
        allergyDescription = in.readString();
    }

    public static final Creator<Allergy> CREATOR = new Creator<Allergy>() {
        @Override
        public Allergy createFromParcel(Parcel in) {
            return new Allergy(in);
        }

        @Override
        public Allergy[] newArray(int size) {
            return new Allergy[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAllergyName() {
        return allergyName;
    }

    public void setAllergyName(String allergyName) {
        this.allergyName = allergyName;
    }

    public String getAllergyDescription() {
        return allergyDescription;
    }

    public void setAllergyDescription(String allergyDescription) {
        this.allergyDescription = allergyDescription;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Allergy other = (Allergy) obj;
        if (this.getId() == other.getId()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(allergyName);
        dest.writeString(allergyDescription);
    }

    @Override
    public String toString() {
        return getAllergyName();
    }
}
