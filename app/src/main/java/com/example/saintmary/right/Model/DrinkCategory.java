package com.example.saintmary.right.Model;
//this Model package is used to bind the firebase database with the recyclerview
public class DrinkCategory {
    private String drinkName;
    private String drnkImage;

    public DrinkCategory(){

    }

    public DrinkCategory(String drinkname, String drinkimage) {
        drinkName =drinkname;
        drnkImage=drinkimage;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
       this.drinkName=drinkName;
    }

    public String getDrinkImage() {
        return drnkImage;
    }

    public void setDrnkImage(String image) {
       this.drnkImage=drnkImage;
    }
}
