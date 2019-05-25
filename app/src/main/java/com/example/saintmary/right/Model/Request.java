package com.example.saintmary.right.Model;

import java.util.List;

public class Request {
    private String phone;
    private String FirstName;
    private String LastName;
    private String address;
    private String total;
    private String status;
    private String FoodName;
    private List<Order> foods;//list of food order

    public Request() {
    }



    public Request(String phone, String firstName, String lastName, String address, String total, List<Order> foods) {
        this.phone = phone;
        FirstName =firstName;
        LastName=lastName;
        this.address = address;
        this.total = total;
        this.foods = foods;
        this.status="0";//default is 0,0:placed,1:shipping,2:shipped
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String toatl) {
        this.total = toatl;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }

//    public String getStatus() {
//        return status;
//    }

    public void setStatus(String status) {
        this.status =status;
    }

    public String getStatus() {
        return status;
    }
    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }
}
