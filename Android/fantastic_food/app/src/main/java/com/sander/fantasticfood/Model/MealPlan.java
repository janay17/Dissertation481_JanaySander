package com.sander.fantasticfood.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

public class MealPlan implements Parcelable {
    private int id;
    private String mealplanName;
    private String mealplanDescription;

    public MealPlan(int id, String mealplanName, String mealplanDescription) {
        this.id = id;
        this.mealplanName = mealplanName;
        this.mealplanDescription = mealplanDescription;
    }

    protected MealPlan(Parcel in) {
        id = in.readInt();
        mealplanName = in.readString();
        mealplanDescription = in.readString();
    }

    public static final Creator<MealPlan> CREATOR = new Creator<MealPlan>() {
        @Override
        public MealPlan createFromParcel(Parcel in) {
            return new MealPlan(in);
        }

        @Override
        public MealPlan[] newArray(int size) {
            return new MealPlan[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMealplanName() {
        return mealplanName;
    }

    public void setMealplanName(String mealplanName) {
        this.mealplanName = mealplanName;
    }

    public String getMealplanDescription() {
        return mealplanDescription;
    }

    public void setMealplanDescription(String mealplanDescription) {
        this.mealplanDescription = mealplanDescription;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        MealPlan other = (MealPlan) obj;
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
        dest.writeString(mealplanName);
        dest.writeString(mealplanDescription);
    }

    @Override
    public String toString() {
        return getMealplanName();
    }
}
